package org.cagrid.gaards.cds.service;

import gov.nih.nci.cagrid.common.FaultHelper;
import gov.nih.nci.cagrid.gridca.common.KeyUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.gaards.cds.common.CertificateChain;
import org.cagrid.gaards.cds.common.DelegationIdentifier;
import org.cagrid.gaards.cds.common.DelegationPolicy;
import org.cagrid.gaards.cds.common.DelegationRecord;
import org.cagrid.gaards.cds.common.DelegationSigningRequest;
import org.cagrid.gaards.cds.common.DelegationSigningResponse;
import org.cagrid.gaards.cds.common.DelegationStatus;
import org.cagrid.gaards.cds.common.PublicKey;
import org.cagrid.gaards.cds.common.Utils;
import org.cagrid.gaards.cds.service.policy.PolicyHandler;
import org.cagrid.gaards.cds.stubs.types.CDSInternalFault;
import org.cagrid.gaards.cds.stubs.types.DelegationFault;
import org.cagrid.gaards.cds.stubs.types.InvalidPolicyFault;
import org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault;
import org.cagrid.tools.database.Database;
import org.globus.gsi.GlobusCredential;

public class DelegatedCredentialManager {

	private final static String TABLE = "delegated_credentials";
	private final static String DELEGATION_ID = "DELEGATION_ID";
	private final static String GRID_IDENTITY = "GRID_IDENTITY";
	private final static String STATUS = "STATUS";
	private final static String POLICY_TYPE = "POLICY_TYPE";
	private final static String EXPIRATION = "EXPIRATION";
	private final static String DATE_INITIATED = "DATE_INITIATED";
	private final static String DATE_APPROVED = "DATE_APPROVED";

	private Database db;
	private boolean dbBuilt = false;
	private List<PolicyHandler> handlers;
	private Log log;
	private KeyManager keyManager;
	private ProxyPolicy proxyPolicy;

	public DelegatedCredentialManager(Database db, PropertyManager properties,
			KeyManager keyManager, List<PolicyHandler> policyHandlers,
			ProxyPolicy proxyPolicy) throws CDSInternalFault {
		this.db = db;
		this.log = LogFactory.getLog(this.getClass().getName());
		this.handlers = policyHandlers;
		this.proxyPolicy = proxyPolicy;
		String currentKeyManager = properties.getKeyManager();
		if ((currentKeyManager != null)
				&& (!currentKeyManager.equals(keyManager.getClass().getName()))) {
			CDSInternalFault f = new CDSInternalFault();
			f.setFaultString(Errors.KEY_MANAGER_CHANGED);
			throw f;
		}
		this.keyManager = keyManager;
		if (currentKeyManager == null) {
			properties.setKeyManager(this.keyManager.getClass().getName());
		}
	}

	public PolicyHandler findHandler(String policyClassName)
			throws InvalidPolicyFault {
		PolicyHandler handler = null;
		boolean handlerFound = false;
		for (int i = 0; i < handlers.size(); i++) {
			if (handlers.get(i).isSupported(policyClassName)) {
				if (!handlerFound) {
					handler = handlers.get(i);
					handlerFound = true;
				} else {
					InvalidPolicyFault f = new InvalidPolicyFault();
					f.setFaultString(Errors.MULTIPLE_HANDLERS_FOUND_FOR_POLICY
							+ policyClassName
							+ ", cannot decide which handler to employ.");
					throw f;
				}
			}
		}
		if (!handlerFound) {
			InvalidPolicyFault f = new InvalidPolicyFault();
			f.setFaultString(Errors.DELEGATION_POLICY_NOT_SUPPORTED);
			throw f;
		}
		return handler;
	}

	public synchronized DelegationSigningRequest initiateDelegation(
			String callerGridIdentity, DelegationPolicy policy, int keyLength)
			throws CDSInternalFault, DelegationFault, InvalidPolicyFault {
		this.buildDatabase();
		PolicyHandler handler = this.findHandler(policy.getClass().getName());
		if (!this.proxyPolicy.isKeySizeSupported(keyLength)) {
			DelegationFault f = new DelegationFault();
			f.setFaultString(Errors.INVALID_KEY_LENGTH_SPECIFIED);
			throw f;
		}

		Connection c = null;
		long delegationId = -1;
		try {
			c = this.db.getConnection();
			PreparedStatement s = c.prepareStatement("INSERT INTO " + TABLE
					+ " SET " + GRID_IDENTITY + "= ?, " + POLICY_TYPE + "= ?, "
					+ STATUS + "= ?," + DATE_INITIATED + "=?," + DATE_APPROVED
					+ "=?," + EXPIRATION + "=?");
			s.setString(1, callerGridIdentity);
			s.setString(2, policy.getClass().getName());
			s.setString(3, DelegationStatus.Pending.getValue());
			s.setLong(4, new Date().getTime());
			s.setLong(5, 0);
			s.setLong(6, 0);
			s.execute();
			s.close();
			delegationId = db.getLastAutoId(c);
			DelegationIdentifier id = new DelegationIdentifier();
			id.setDelegationId(delegationId);
			// Create and Store Key Pair.
			KeyPair keys = this.keyManager.createAndStoreKeyPair(String
					.valueOf(delegationId), keyLength);
			handler.storePolicy(id, policy);
			DelegationSigningRequest req = new DelegationSigningRequest();
			req.setDelegationIdentifier(id);
			PublicKey publicKey = new PublicKey();
			publicKey.setKeyAsString(KeyUtil.writePublicKey(keys.getPublic()));
			req.setPublicKey(publicKey);
			return req;
		} catch (CDSInternalFault e) {
			try {
				this.delete(delegationId);
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
			throw e;
		} catch (InvalidPolicyFault e) {
			try {
				this.delete(delegationId);
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			try {
				this.delete(delegationId);
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
			throw Errors.getDatabaseFault(e);
		} finally {
			db.releaseConnection(c);
		}
	}

	public boolean delegationExists(DelegationIdentifier id)
			throws CDSInternalFault {
		try {
			if (id == null) {
				return false;
			} else {
				return db.exists(TABLE, DELEGATION_ID, id.getDelegationId());
			}
		} catch (Exception e) {
			throw Errors.getDatabaseFault(e);
		}
	}

	public DelegationRecord getDelegationRecord(DelegationIdentifier id)
			throws CDSInternalFault, DelegationFault {

		if (delegationExists(id)) {
			DelegationRecord r = new DelegationRecord();
			r.setDelegationIdentifier(id);
			Connection c = null;
			try {
				c = this.db.getConnection();
				PreparedStatement s = c.prepareStatement("select * from "
						+ TABLE + " WHERE " + DELEGATION_ID + "= ?");
				s.setLong(1, id.getDelegationId());
				ResultSet rs = s.executeQuery();
				if (rs.next()) {
					r.setDateApproved(rs.getLong(DATE_APPROVED));
					r.setDateInitiated(rs.getLong(DATE_INITIATED));
					r.setDelegationStatus(DelegationStatus.fromValue(rs
							.getString(STATUS)));
					r.setExpiration(rs.getLong(EXPIRATION));
					r.setGridIdentity(rs.getString(GRID_IDENTITY));
				}
				rs.close();
				s.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw Errors.getDatabaseFault(e);
			} finally {
				this.db.releaseConnection(c);
			}

			try {
				X509Certificate[] certs = this.keyManager
						.getCertificates(String.valueOf(id.getDelegationId()));
				r.setCertificateChain(org.cagrid.gaards.cds.common.Utils
						.toCertificateChain(certs));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				CDSInternalFault f = new CDSInternalFault();
				f
						.setFaultString(Errors.UNEXPECTED_ERROR_LOADING_CERTIFICATE_CHAIN);
				FaultHelper helper = new FaultHelper(f);
				helper.addFaultCause(e);
				f = (CDSInternalFault) helper.getFault();
				throw f;
			}
			try {
				PolicyHandler handler = this.findHandler(getPolicyType(id
						.getDelegationId()));
				r.setDelegationPolicy(handler.getPolicy(id));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				CDSInternalFault f = new CDSInternalFault();
				f
						.setFaultString(Errors.UNEXPECTED_ERROR_LOADING_DELEGATION_POLICY);
				FaultHelper helper = new FaultHelper(f);
				helper.addFaultCause(e);
				f = (CDSInternalFault) helper.getFault();
				throw f;
			}

			return r;
		} else {
			DelegationFault f = new DelegationFault();
			f.setFaultString(Errors.DELEGATION_RECORD_DOES_NOT_EXIST);
			throw f;
		}
	}

	public synchronized void approveDelegation(String callerGridIdentity,
			DelegationSigningResponse res) throws CDSInternalFault,
			DelegationFault, PermissionDeniedFault {
		DelegationIdentifier id = res.getDelegationIdentifier();
		if (this.delegationExists(id)) {
			DelegationRecord r = getDelegationRecord(id);
			if (!r.getGridIdentity().equals(callerGridIdentity)) {
				DelegationFault f = new DelegationFault();
				f
						.setFaultString(Errors.INITIATOR_DOES_NOT_MATCH_APPROVER);
				throw f;
			}
			CertificateChain chain = res.getCertificateChain();
			if (chain == null) {
				DelegationFault f = new DelegationFault();
				f
						.setFaultString(Errors.CERTIFICATE_CHAIN_NOT_SPECIFIED);
				throw f;
			}
			X509Certificate[] certs = null;
			try {
				certs = Utils.toCertificateArray(chain);

			} catch (Exception e) {
				CDSInternalFault f = new CDSInternalFault();
				f
						.setFaultString(Errors.UNEXPECTED_ERROR_LOADING_CERTIFICATE_CHAIN);
			}

			if (certs.length < 2) {
				DelegationFault f = new DelegationFault();
				f
						.setFaultString(Errors.INSUFFICIENT_CERTIFICATE_CHAIN_SPECIFIED);
				throw f;
			}
			
			//TODO: Check public key
			
			PrivateKey pkey = this.keyManager.getPrivateKey(String.valueOf(id
					.getDelegationId()));
			GlobusCredential cred = new GlobusCredential(pkey, certs);
			if (!cred.getIdentity().equals(r.getGridIdentity())) {
				DelegationFault f = new DelegationFault();
				f
						.setFaultString(Errors.IDENTITY_DOES_NOT_MATCH_INITIATOR);
				throw f;
			}
			
			
			//TODO: Check delegation path length

		} else {
			DelegationFault f = new DelegationFault();
			f.setFaultString(Errors.DELEGATION_RECORD_DOES_NOT_EXIST);
			throw f;
		}

	}

	private String getPolicyType(long delegationId) throws CDSInternalFault {
		Connection c = null;
		String policyType = null;
		try {
			c = this.db.getConnection();
			PreparedStatement s = c.prepareStatement("select " + POLICY_TYPE
					+ " from " + TABLE + " WHERE " + DELEGATION_ID + "= ?");
			s.setLong(1, delegationId);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				policyType = rs.getString(POLICY_TYPE);
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw Errors.getDatabaseFault(e);
		} finally {
			this.db.releaseConnection(c);
		}
		return policyType;
	}

	public synchronized void delete(DelegationIdentifier id)
			throws CDSInternalFault {
		this.delete(id.getDelegationId());
	}

	public synchronized void delete(long delegationId) throws CDSInternalFault {
		buildDatabase();
		Connection c = null;
		try {
			String policyType = getPolicyType(delegationId);
			PolicyHandler handler = findHandler(policyType);
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("DELETE FROM " + TABLE
					+ "  WHERE " + DELEGATION_ID + "= ?");
			s.setLong(1, delegationId);
			s.execute();
			s.close();
			this.keyManager.delete(String.valueOf(delegationId));
			DelegationIdentifier id = new DelegationIdentifier();
			id.setDelegationId(delegationId);
			handler.removePolicy(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw Errors.getDatabaseFault(e);
		} finally {
			db.releaseConnection(c);
		}
	}

	public void clearDatabase() throws CDSInternalFault {
		buildDatabase();
		try {
			for (int i = 0; i < handlers.size(); i++) {
				this.handlers.get(i).removeAllStoredPolicies();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		try {
			this.keyManager.deleteAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		try {
			db.update("DELETE FROM " + TABLE);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw Errors.getDatabaseFault(e);
		}
		dbBuilt = false;

	}

	private void buildDatabase() throws CDSInternalFault {
		if (!dbBuilt) {
			try {
				if (!this.db.tableExists(TABLE)) {
					String trust = "CREATE TABLE " + TABLE + " ("
							+ DELEGATION_ID
							+ " BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
							+ GRID_IDENTITY + " VARCHAR(255) NOT NULL,"
							+ POLICY_TYPE + " VARCHAR(255) NOT NULL," + STATUS
							+ " VARCHAR(50) NOT NULL," + DATE_INITIATED
							+ " BIGINT," + DATE_APPROVED + " BIGINT,"
							+ EXPIRATION + " BIGINT, INDEX document_index ("
							+ DELEGATION_ID + "));";
					db.update(trust);
				}

				dbBuilt = true;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw Errors.getDatabaseFault(e);
			}
		}
	}

}
