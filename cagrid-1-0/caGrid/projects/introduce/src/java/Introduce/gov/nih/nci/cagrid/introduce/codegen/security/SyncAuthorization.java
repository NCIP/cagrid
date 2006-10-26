package gov.nih.nci.cagrid.introduce.codegen.security;

import gov.nih.nci.cagrid.gridgrouper.bean.MembershipExpression;
import gov.nih.nci.cagrid.gridgrouper.client.GridGrouperClientUtils;
import gov.nih.nci.cagrid.introduce.beans.method.MethodType;
import gov.nih.nci.cagrid.introduce.beans.security.CSMAuthorization;
import gov.nih.nci.cagrid.introduce.beans.security.ProtectionMethod;
import gov.nih.nci.cagrid.introduce.beans.service.ServiceType;
import gov.nih.nci.cagrid.introduce.codegen.common.SyncTool;
import gov.nih.nci.cagrid.introduce.codegen.common.SynchronizationException;
import gov.nih.nci.cagrid.introduce.codegen.utils.TemplateUtils;
import gov.nih.nci.cagrid.introduce.common.CommonTools;
import gov.nih.nci.cagrid.introduce.info.ServiceInformation;
import gov.nih.nci.cagrid.introduce.info.SpecificServiceInformation;
import gov.nih.nci.cagrid.introduce.templates.service.globus.ServiceAuthorizationTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;


public class SyncAuthorization extends SyncTool {

	public SyncAuthorization(File baseDirectory, ServiceInformation info) {
		super(baseDirectory, info);
		// TODO Auto-generated constructor stub
	}


	public static String addAuthorizationToProviderImpl(ServiceType service, MethodType method, String lineStart)
		throws Exception {
		// First we filter by method name
		if (method.getMethodSecurity() != null) {
			if (method.getMethodSecurity().getMethodAuthorization() != null) {
				if (method.getMethodSecurity().getMethodAuthorization().getGridGrouperAuthorization() != null) {
					return generateGridGrouper(method, method.getMethodSecurity().getMethodAuthorization()
						.getGridGrouperAuthorization(), lineStart);
				} else if (method.getMethodSecurity().getMethodAuthorization().getCSMAuthorization() != null) {
					return generateCSM(service, method, method.getMethodSecurity().getMethodAuthorization()
						.getCSMAuthorization(), true, lineStart);
				}
			}
		}

		if (service.getServiceSecurity() != null) {
			if (service.getServiceSecurity().getServiceAuthorization() != null) {
				if (service.getServiceSecurity().getServiceAuthorization().getGridGrouperAuthorization() != null) {
					return generateGridGrouper(method,service.getServiceSecurity().getServiceAuthorization()
						.getGridGrouperAuthorization(), lineStart);
				} else if (service.getServiceSecurity().getServiceAuthorization().getCSMAuthorization() != null) {
					return generateCSM(service, method, service.getServiceSecurity().getServiceAuthorization()
						.getCSMAuthorization(), false, lineStart);
				}
			}
		}
		return "";
	}


	public static String generateCSM(ServiceType service, MethodType method, CSMAuthorization csm,
		boolean isMethodLevel, String lineStart) throws Exception {
		StringBuffer sb = new StringBuffer();

		if (csm != null) {
			sb.append(lineStart + "\n");
			sb.append(lineStart + "/******************* Start CSM Authorization *******************/\n");
			sb.append(lineStart + "\n");
			sb.append(lineStart + "boolean authorized = false;\n");
			sb.append(lineStart + "try{\n");
			sb.append(lineStart + "String csmConfig = " + service.getPackageName()
				+ ".service.ServiceConfiguration.getConfiguration().getCsmConfiguration();\n");
			sb.append(lineStart + "if((csmConfig == null)||(csmConfig.trim().length()<=0)){\n");
			sb.append(lineStart + "\t"
				+ "throw new java.rmi.RemoteException(\"No CSM Configuration file could be found\");\n");
			sb.append(lineStart + "}else{\n");
			sb.append(lineStart + "\t" + "System.setProperty(\"gov.nih.nci.security.configFile\", csmConfig);\n");
			sb.append(lineStart + "}\n");

			if (csm.getProtectionMethod().equals(ProtectionMethod.ServiceURI)) {
				sb
					.append(lineStart
						+ "\t"
						+ "org.apache.axis.message.addressing.EndpointReferenceType type = org.globus.wsrf.utils.AddressingUtils.createEndpointReference(null);\n");
				if (isMethodLevel) {
					sb.append(lineStart + "\t" + "String object = type.getAddress().toString()+\":" + method.getName()
						+ "\";\n");
				} else {
					sb.append(lineStart + "\t" + "String object = type.getAddress().toString();\n");
				}
			} else if (csm.getProtectionMethod().equals(ProtectionMethod.ServiceType)) {
				if (isMethodLevel) {
					sb.append(lineStart + "\t" + "String object = \"" + service.getName() + ":" + method.getName()
						+ "\";\n");
				} else {
					sb.append(lineStart + "\t" + "String object = \"" + service.getName() + "\";\n");
				}
			} else {
				sb.append(lineStart + "\t" + "String object = \"" + csm.getCustomProtectionMethod() + "\";\n");
			}

			String application = csm.getApplicationContext();

			sb.append(lineStart + "\t" + "String gridIdentity = getCallerIdentity();\n");

			sb.append(lineStart + "\t" + "String privilege = \"" + csm.getPrivilege() + "\";\n");

			sb
				.append(lineStart
					+ "\t"
					+ "gov.nih.nci.cagrid.authorization.GridAuthorizationManager mgr = new gov.nih.nci.cagrid.authorization.impl.CSMGridAuthorizationManager(\""
					+ application + "\");\n");

			sb.append(lineStart + "\t" + "authorized = mgr.isAuthorized(gridIdentity,object,privilege);\n");
			sb.append(lineStart + "}catch(Exception e){\n");
			sb.append(lineStart + "\t" + "e.printStackTrace();\n");
			sb
				.append(lineStart
					+ "\t"
					+ "throw new java.rmi.RemoteException(\"Error determining if caller is authorized to perform request\");\n");
			sb.append(lineStart + "}\n");
			sb.append(lineStart + "if(!authorized){\n");
			sb
				.append(lineStart + "\t"
					+ "throw new java.rmi.RemoteException(\"Not authorized to perform request\");\n");
			sb.append(lineStart + "}\n");

			sb.append(lineStart + "\n");
			sb.append(lineStart + "/******************** End CSM Authorization ********************/\n");
			sb.append(lineStart + "\n");

			// gov.nih.nci.cagrid.authorization.impl.GridAuthorizationManager
			// mgr = new
			// gov.nih.nci.cagrid.authorization.impl.CSMGridAuthorizationManager(csm.get);
			// mgr.isAuthorized(getCallerIdentity(), obj, privilege);
			// sb.append(lineStart + "\n");

		}
		return sb.toString();
	}


	public static String generateGridGrouperQuery(MembershipExpression exp) throws Exception {
		StringBuffer gg = new StringBuffer();

		if (exp != null) {
			String xml = GridGrouperClientUtils.expressionToXML(exp);
			xml = xml.replaceAll("\"", "\\\\\"");

			BufferedReader reader = new BufferedReader(new StringReader(xml));
			String line = reader.readLine();
			while (line != null) {
				gg.append("\"" + line + "\"");
				line = reader.readLine();
				if(line!=null){
					gg.append("\n\t\t + ");
				}
			}
			gg.append(";");
		}
		return gg.toString();
	}
	public static String generateGridGrouper(MethodType method, MembershipExpression exp, String lineStart) throws Exception {
		StringBuffer gg = new StringBuffer();

		if (exp != null) {
			gg.append(lineStart + "/**************** Start Grid Grouper Authorization *****************/\n");

			gg.append(lineStart + "String gridIdentity = getCallerIdentity();\n");
			gg.append(lineStart + "\n");
			gg.append(lineStart + "boolean isMember=false;\n");
			gg.append(lineStart + "if(gridIdentity!=null){\n");
			gg.append(lineStart + "\t" + "try{\n");
			gg
				.append(lineStart
					+ "\t\t"
					+ "isMember=gov.nih.nci.cagrid.gridgrouper.client.GridGrouperClientUtils.isMember(gridGrouperAuthorize" + TemplateUtils.upperCaseFirstCharacter(method.getName())+ ",gridIdentity);\n");
			gg.append(lineStart + "\t" + "}catch(Exception e){\n");
			gg.append(lineStart + "\t\t" + "e.printStackTrace();\n");
			gg
				.append(lineStart
					+ "\t\t"
					+ "throw new java.rmi.RemoteException(\"Error determining if caller is authorized to perform request\");\n");
			gg.append(lineStart + "\t" + "}\n");
			gg.append(lineStart + "}\n");
			gg.append(lineStart + "if(!isMember){\n");
			gg
				.append(lineStart + "\t"
					+ "throw new java.rmi.RemoteException(\"Not authorized to perform request\");\n");
			gg.append(lineStart + "}\n");
			gg.append(lineStart + "/************** End Grid Grouper Authorization ***************/\n");
		}
		return gg.toString();
	}


	public void sync() throws SynchronizationException {

		for (int serviceI = 0; serviceI < getServiceInformation().getServices().getService().length; serviceI++) {
			ServiceType service = getServiceInformation().getServices().getService(serviceI);
			ServiceAuthorizationTemplate authorizationT = new ServiceAuthorizationTemplate();
			String authorizationS = authorizationT.generate(new SpecificServiceInformation(getServiceInformation(),
				service));
			File authorizationF = new File(getBaseDirectory().getAbsolutePath() + File.separator + "src"
				+ File.separator + CommonTools.getPackageDir(service) + File.separator + "service" + File.separator
				+ "globus" + File.separator + service.getName() + "Authorization.java");

			try {
				FileWriter authorizationFW = new FileWriter(authorizationF);
				authorizationFW.write(authorizationS);
				authorizationFW.close();
			} catch (Exception e) {
				System.out.println("ERROR: Unable to write authorization class for service: " + service.getName());
			}

		}

	}
}
