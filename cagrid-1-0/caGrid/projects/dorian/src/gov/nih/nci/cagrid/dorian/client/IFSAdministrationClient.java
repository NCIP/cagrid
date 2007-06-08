package gov.nih.nci.cagrid.dorian.client;

import gov.nih.nci.cagrid.common.FaultHelper;
import gov.nih.nci.cagrid.common.FaultUtil;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.dorian.common.DorianFault;
import gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateFilter;
import gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateRecord;
import gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateUpdate;
import gov.nih.nci.cagrid.dorian.ifs.bean.IFSUser;
import gov.nih.nci.cagrid.dorian.ifs.bean.IFSUserFilter;
import gov.nih.nci.cagrid.dorian.ifs.bean.IFSUserPolicy;
import gov.nih.nci.cagrid.dorian.ifs.bean.TrustedIdP;
import gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault;
import gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault;
import gov.nih.nci.cagrid.dorian.stubs.types.InvalidTrustedIdPFault;
import gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserFault;
import gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault;

import java.math.BigInteger;
import java.rmi.RemoteException;

import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;

/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class IFSAdministrationClient {
	private DorianClient client;

	public IFSAdministrationClient(String serviceURI)
			throws MalformedURIException, RemoteException {
		client = new DorianClient(serviceURI);
	}

	public IFSAdministrationClient(String serviceURI, GlobusCredential proxy)
			throws MalformedURIException, RemoteException {
		client = new DorianClient(serviceURI, proxy);
	}

	public TrustedIdP addTrustedIdP(TrustedIdP idp) throws DorianFault,
			PermissionDeniedFault, InvalidTrustedIdPFault, DorianInternalFault {
		try {
			return client.addTrustedIdP(idp);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (InvalidTrustedIdPFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}
	}

	public void removeTrustedIdP(TrustedIdP idp) throws DorianFault,
			PermissionDeniedFault, InvalidTrustedIdPFault, DorianInternalFault {
		try {
			client.removeTrustedIdP(idp);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (InvalidTrustedIdPFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public void updateTrustedIdP(TrustedIdP idp) throws DorianFault,
			PermissionDeniedFault, InvalidTrustedIdPFault, DorianInternalFault {
		try {
			client.updateTrustedIdP(idp);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (InvalidTrustedIdPFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public IFSUserPolicy[] getUserPolicies() throws DorianFault,
			PermissionDeniedFault, DorianInternalFault {

		try {
			return client.getIFSUserPolicies();
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}
	}

	public IFSUser renewUserCredentials(IFSUser usr) throws DorianFault,
			PermissionDeniedFault, InvalidUserFault, DorianInternalFault {

		try {
			return client.renewIFSUserCredentials(usr);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (InvalidUserFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}
	}

	public TrustedIdP[] getTrustedIdPs() throws DorianFault,
			PermissionDeniedFault, InvalidUserFault, DorianInternalFault {

		try {
			return client.getTrustedIdPs();
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (InvalidUserFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}
	}

	public IFSUser[] findUsers(IFSUserFilter filter) throws DorianFault,
			PermissionDeniedFault, DorianInternalFault {

		try {
			return client.findIFSUsers(filter);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}
	}

	public void removeUser(IFSUser usr) throws DorianFault,
			PermissionDeniedFault, InvalidUserFault, DorianInternalFault {

		try {
			client.removeIFSUser(usr);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (InvalidUserFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public void updateUser(IFSUser usr) throws DorianFault,
			PermissionDeniedFault, InvalidUserFault, DorianInternalFault {

		try {
			client.updateIFSUser(usr);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (InvalidUserFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public void addAdmin(java.lang.String gridIdentity) throws DorianFault,
			gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
			gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		try {
			client.addAdmin(gridIdentity);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public void removeAdmin(java.lang.String gridIdentity) throws DorianFault,
			gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
			gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		try {
			client.removeAdmin(gridIdentity);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public java.lang.String[] getAdmins() throws DorianFault,
			gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
			gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
		try {
			return client.getAdmins();
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}
	}

	public HostCertificateRecord[] findHostCertificates(
			HostCertificateFilter filter) throws DorianFault,
			DorianInternalFault, PermissionDeniedFault {
		try {
			return client.findHostCertificates(filter);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public HostCertificateRecord approveHostCertificate(long recordId)
			throws DorianFault, DorianInternalFault,
			InvalidHostCertificateFault, PermissionDeniedFault {
		try {
			return client.approveHostCertificate(BigInteger.valueOf(recordId));
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (InvalidHostCertificateFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public void updateHostCertificateRecord(HostCertificateUpdate update)
			throws DorianFault, DorianInternalFault,
			InvalidHostCertificateFault, PermissionDeniedFault {
		try {
			client.updateHostCertificateRecord(update);
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (InvalidHostCertificateFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}

	}

	public HostCertificateRecord renewHostCertificate(long recordId) throws DorianFault,
			DorianInternalFault, InvalidHostCertificateFault,
			PermissionDeniedFault {
		try {
			return client.renewHostCertificate(BigInteger.valueOf(recordId));
		} catch (DorianInternalFault gie) {
			throw gie;
		} catch (InvalidHostCertificateFault gie) {
			throw gie;
		} catch (PermissionDeniedFault f) {
			throw f;
		} catch (Exception e) {
			FaultUtil.printFault(e);
			DorianFault fault = new DorianFault();
			fault.setFaultString(Utils.getExceptionMessage(e));
			FaultHelper helper = new FaultHelper(fault);
			helper.addFaultCause(e);
			fault = (DorianFault) helper.getFault();
			throw fault;
		}
	}

}
