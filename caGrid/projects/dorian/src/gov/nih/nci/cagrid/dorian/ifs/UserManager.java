package gov.nih.nci.cagrid.gums.ifs;

import gov.nih.nci.cagrid.gums.bean.GUMSInternalFault;
import gov.nih.nci.cagrid.gums.ca.CertificateAuthority;
import gov.nih.nci.cagrid.gums.common.Database;
import gov.nih.nci.cagrid.gums.common.GUMSObject;
import gov.nih.nci.cagrid.gums.common.ca.CertUtil;
import gov.nih.nci.cagrid.gums.common.ca.KeyUtil;
import gov.nih.nci.cagrid.gums.ifs.bean.CredentialsFault;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUser;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUserFilter;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUserRole;
import gov.nih.nci.cagrid.gums.ifs.bean.IFSUserStatus;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.globus.wsrf.utils.FaultHelper;

/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class UserManager extends GUMSObject {

	private static final String USERS_TABLE = "IFS_USERS";

	private Database db;

	private boolean dbBuilt = false;

	private CredentialsManager credentialsManager;

	private IFSConfiguration conf;

	private CertificateAuthority ca;

	public UserManager(Database db, IFSConfiguration conf,
			CertificateAuthority ca) {
		this.db = db;
		this.credentialsManager = new CredentialsManager(db);
		this.conf = conf;
		this.ca = ca;
	}

	public CredentialsManager getCredentialsManager() {
		return credentialsManager;
	}

	public synchronized boolean determineIfUserExists(long idpId, String uid)
			throws GUMSInternalFault {
		buildDatabase();
		Connection c = null;
		boolean exists = false;
		try {
			c = db.getConnectionManager().getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("select count(*) from " + USERS_TABLE
					+ " WHERE IDP_ID=" + idpId + " AND UID='" + uid + "'");
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					exists = true;
				}
			}
			rs.close();
			s.close();

		} catch (Exception e) {
			GUMSInternalFault fault = new GUMSInternalFault();
			fault.setFaultString("Unexpected Database Error");
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (GUMSInternalFault) helper.getFault();
			throw fault;
		} finally {
			db.getConnectionManager().releaseConnection(c);
		}
		return exists;
	}

	private String getCredentialsManagerUID(long idpId, String uid) {
		return "[IdPId=" + idpId + ", UID=" + uid + "]";
	}

	public synchronized X509Certificate createUserCredentials(long idpId,
			String uid) throws GUMSInternalFault, CredentialsFault {
		try {

			String caSubject = ca.getCACertificate().getSubjectDN().getName();
			int caindex = caSubject.lastIndexOf(",");
			String caPreSub = caSubject.substring(0, caindex);
			String sub = caPreSub + ",OU=IdP [" + idpId + "],CN=" + uid;
			Calendar c = new GregorianCalendar();
			Date start = c.getTime();
			c.roll(Calendar.YEAR, conf.getCredentialsValidYears());
			c.roll(Calendar.MONTH, conf.getCredentialsValidMonths());
			c.roll(Calendar.DAY_OF_MONTH, conf.getCredentialsValidDays());
			Date end = c.getTime();
			if (end.after(ca.getCACertificate().getNotAfter())) {
				end = ca.getCACertificate().getNotAfter();
			}

			KeyPair pair = KeyUtil.generateRSAKeyPair1024();

			PKCS10CertificationRequest req = CertUtil
					.generateCertficateRequest(sub, pair);
			X509Certificate cert = ca.requestCertificate(req, start, end);
			this.credentialsManager.addCredentials(getCredentialsManagerUID(
					idpId, uid), null, cert, pair.getPrivate());
			return cert;
		} catch (Exception e) {
			logError(e.getMessage(), e);
			CredentialsFault fault = new CredentialsFault();
			fault.setFaultString("Error creating credentials.");
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (CredentialsFault) helper.getFault();
			throw fault;
		}
	}

	private StringBuffer appendWhereOrAnd(boolean firstAppended,
			StringBuffer sql) {
		if (firstAppended) {
			sql.append(" AND ");
		} else {
			sql.append(" WHERE");
		}
		return sql;
	}

	public IFSUser[] getUsers(IFSUserFilter filter) throws GUMSInternalFault {

		this.buildDatabase();
		Connection c = null;
		List users = new ArrayList();
		try {
			c = db.getConnectionManager().getConnection();
			Statement s = c.createStatement();

			StringBuffer sql = new StringBuffer();
			sql.append("select * from " + USERS_TABLE);
			if (filter != null) {
				boolean firstAppended = false;

				if (filter.getIdPId() > 0) {
					sql = appendWhereOrAnd(firstAppended, sql);
					firstAppended = true;
					sql.append(" IDP_ID =" + filter.getIdPId());
				}

				if (filter.getUID() != null) {
					sql = appendWhereOrAnd(firstAppended, sql);
					firstAppended = true;
					sql.append(" UID LIKE '%" + filter.getUID() + "%'");
				}

				if (filter.getGridId() != null) {
					sql = appendWhereOrAnd(firstAppended, sql);
					firstAppended = true;
					sql.append(" GID LIKE '%" + filter.getGridId() + "%'");
				}

				if (filter.getEmail() != null) {
					sql = appendWhereOrAnd(firstAppended, sql);
					firstAppended = true;
					sql.append(" EMAIL LIKE '%" + filter.getEmail() + "%'");
				}

				if (filter.getUserStatus() != null) {
					sql = appendWhereOrAnd(firstAppended, sql);
					firstAppended = true;
					sql.append(" STATUS='" + filter.getUserStatus() + "'");
				}

				if (filter.getUserRole() != null) {
					sql = appendWhereOrAnd(firstAppended, sql);
					firstAppended = true;
					sql.append(" ROLE='" + filter.getUserRole() + "'");
				}
			}

			ResultSet rs = s.executeQuery(sql.toString());
			while (rs.next()) {
				IFSUser user = new IFSUser();
				user.setIdPId(rs.getLong("IDP_ID"));
				user.setUID(rs.getString("UID"));
				user.setGridId(rs.getString("GID"));
				String email = rs.getString("EMAIL");
				if ((email != null) && (!email.equals("null"))) {
					user.setEmail(email);
				}
				user.setUserStatus(IFSUserStatus.fromValue(rs
						.getString("STATUS")));
				String role = rs.getString("ROLE");
				user.setUserRole(IFSUserRole.fromValue(role));
				X509Certificate cert = credentialsManager
						.getCertificate(getCredentialsManagerUID(user
								.getIdPId(), user.getUID()));
				user.setCertificate(CertUtil.writeCertificateToString(cert));
				users.add(user);
			}
			rs.close();
			s.close();

			IFSUser[] list = new IFSUser[users.size()];
			for (int i = 0; i < list.length; i++) {
				list[i] = (IFSUser) users.get(i);
			}
			return list;

		} catch (Exception e) {
			logError(e.getMessage(), e);
			GUMSInternalFault fault = new GUMSInternalFault();
			fault
					.setFaultString("Unexpected Error, could not obtain a list of users");
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (GUMSInternalFault) helper.getFault();
			throw fault;
		} finally {
			db.getConnectionManager().releaseConnection(c);
		}
	}

	public synchronized IFSUser addUser(IFSUser user) throws GUMSInternalFault,
			CredentialsFault {
		this.buildDatabase();
		if (!determineIfUserExists(user.getIdPId(), user.getUID())) {
			X509Certificate cert = createUserCredentials(user.getIdPId(), user
					.getUID());
			try {
				// Write method for creating and setting a users credentials
				user.setCertificate(CertUtil.writeCertificateToString(cert));
				user.setGridId(cert.getSubjectDN().toString());
				user.setUserRole(IFSUserRole.Non_Administrator);
				user.setUserStatus(IFSUserStatus.Pending);
				db.update("INSERT INTO " + USERS_TABLE + " SET IDP_ID='"
						+ user.getIdPId() + "',UID='" + user.getUID()
						+ "', GID='" + user.getGridId() + "',STATUS='"
						+ user.getUserStatus().toString() + "',ROLE='"
						+ user.getUserRole().toString() + "',EMAIL='"
						+ user.getEmail() + "'");
			} catch (Exception e) {
				try {
					this.removeUser(user.getIdPId(), user.getUID());
				} catch (Exception ex) {

				}

				try {
					this.credentialsManager
							.deleteCredentials(getCredentialsManagerUID(user
									.getIdPId(), user.getUID()));
				} catch (Exception ex) {

				}
				logError(e.getMessage(), e);
				GUMSInternalFault fault = new GUMSInternalFault();
				fault
						.setFaultString("Error adding the user "
								+ getCredentialsManagerUID(user.getIdPId(),
										user.getUID())
								+ " to the IFS, an unexpected database error occurred.");
				FaultHelper helper = new FaultHelper(fault);
				helper.addFaultCause(e);
				fault = (GUMSInternalFault) helper.getFault();
				throw fault;
			}

		} else {
			GUMSInternalFault fault = new GUMSInternalFault();
			fault.setFaultString("Error adding the user, "
					+ getCredentialsManagerUID(user.getIdPId(), user.getUID())
					+ ", the user already exists!!!");
			throw fault;

		}
		return user;
	}

	public synchronized void removeUser(long idpId, String uid)
			throws GUMSInternalFault {
		this.buildDatabase();
		db.update("delete from " + USERS_TABLE + " WHERE IDP_ID=" + idpId
				+ " AND UID='" + uid + "'");
	}

	private void buildDatabase() throws GUMSInternalFault {
		if (!dbBuilt) {
			if (!this.db.tableExists(USERS_TABLE)) {
				String users = "CREATE TABLE " + USERS_TABLE + " ("
						+ "IDP_ID INT NOT NULL," + "UID VARCHAR(255) NOT NULL,"
						+ "GID VARCHAR(255) NOT NULL,"
						+ "STATUS VARCHAR(50) NOT NULL,"
						+ "ROLE VARCHAR(50) NOT NULL, "
						+ "EMAIL VARCHAR(255) NOT NULL, "
						+ "INDEX document_index (UID));";
				db.update(users);
			}
			this.dbBuilt = true;
		}
	}

}