package gov.nih.nci.cagrid.introduce;

import gov.nih.nci.cagrid.common.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.ws.jaxme.js.JavaMethod;
import org.apache.ws.jaxme.js.Parameter;
import org.jdom.Element;


/**
 * SyncMethodsOnDeployment TODO:DOCUMENT ME
 * 
 * @author <A HREF="MAILTO:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @author <A HREF="MAILTO:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A HREF="MAILTO:langella@bmi.osu.edu">Stephen Langella </A>
 * @created Jun 8, 2005
 * @version $Id: mobiusEclipseCodeTemplates.xml,v 1.2 2005/04/19 14:58:02 oster
 *          Exp $
 */
public class SyncMethods {

	private String serviceClient;
	private String serviceInterface;
	private String serviceImpl;
	private String serviceProviderImpl;
	private Properties deploymentProperties;
	private String packageName;
	private File baseDir;


	public SyncMethods(File baseDir, Properties deploymentProperties) {
		this.baseDir = baseDir;
		this.deploymentProperties = deploymentProperties;
		this.packageName = (String) this.deploymentProperties.get("introduce.skeleton.package") + ".stubs";
		serviceClient = baseDir.getAbsolutePath() + File.separator + "src" + File.separator
			+ this.deploymentProperties.get("introduce.skeleton.package.dir") + File.separator + "client"
			+ File.separator + this.deploymentProperties.get("introduce.skeleton.service.name") + "Client.java";
		serviceInterface = baseDir.getAbsolutePath() + File.separator + "src" + File.separator
			+ this.deploymentProperties.get("introduce.skeleton.package.dir") + File.separator + "common"
			+ File.separator + this.deploymentProperties.get("introduce.skeleton.service.name") + "I.java";
		serviceImpl = baseDir.getAbsolutePath() + File.separator + "src" + File.separator
			+ this.deploymentProperties.get("introduce.skeleton.package.dir") + File.separator + "service"
			+ File.separator + this.deploymentProperties.get("introduce.skeleton.service.name") + "Impl.java";
		serviceProviderImpl = baseDir.getAbsolutePath() + File.separator + "src" + File.separator
			+ this.deploymentProperties.get("introduce.skeleton.package.dir") + File.separator + "service"
			+ File.separator + "globus" + File.separator
			+ this.deploymentProperties.get("introduce.skeleton.service.name") + "ProviderImpl.java";
	}


	private String createExceptions(Element method) {
		String exceptions = "";
		// process the faults for this method...
		Element exceptionsEl = method.getChild("exceptions", method.getNamespace());
		exceptions += "RemoteException";
		if (exceptionsEl != null) {
			List children = exceptionsEl.getChildren();
			if (children.size() > 0) {
				exceptions += ", ";
			}
			for (int i = 0; i < children.size(); i++) {
				Element fault = (Element) children.get(i);
				// hack for now, should look at the namespace in the
				// element.....
				exceptions += this.packageName + "." + capatilzeFirstLetter(fault.getAttributeValue("name"));
				if (i < children.size() - 1) {
					exceptions += ", ";
				}
			}
		}
		if (exceptions.length() > 0) {
			exceptions = "throws " + exceptions + " ";
		}
		return exceptions;
	}


	private String createUnBoxedSignatureStringFromMethod(Element method) {
		String methodString = "";
		Element returnTypeEl = method.getChild("output", method.getNamespace());
		String methodName = method.getAttributeValue("name");
		String returnType = returnTypeEl.getAttributeValue("className");
		methodString += "\tpublic " + returnType + " " + methodName + "(";
		List inputs = method.getChild("inputs", method.getNamespace()).getChildren();
		for (int j = 0; j < inputs.size(); j++) {
			String classType = ((Element) inputs.get(j)).getAttributeValue("className");
			String paramName = ((Element) inputs.get(j)).getAttributeValue("name");
			methodString += classType + " " + paramName;
			if (j < inputs.size() - 1) {
				methodString += ",";
			}
		}
		methodString += ")";

		return methodString;
	}


	private String createUnBoxedSignatureStringFromMethod(JavaMethod method) {
		String methodString = "";
		String methodName = method.getName();
		String returnType = "";
		if (method.getType().getPackageName().length() > 0) {
			returnType += method.getType().getPackageName() + ".";
		}
		returnType += method.getType().getClassName();
		if (method.getType().isArray()) {
			returnType += "[]";
		}
		methodString += "\tpublic " + returnType + " " + methodName + "(";
		Parameter[] inputs = method.getParams();
		for (int j = 0; j < inputs.length; j++) {
			String classType = inputs[j].getType().getPackageName() + "." + inputs[j].getType().getClassName();
			if (inputs[j].getType().isArray()) {
				classType += "[]";
			}
			String paramName = inputs[j].getName();
			methodString += classType + " " + paramName;
			if (j < inputs.length - 1) {
				methodString += ",";
			}
		}
		methodString += ")";
		return methodString;
	}


	private String getBoxedOutputTypeName(String input) {
		String returnType = capatilzeFirstLetter(input) + "Response";
		return returnType;
	}


	private String capatilzeFirstLetter(String input) {
		String returnType = input.toUpperCase().toCharArray()[0] + input.substring(1, input.length());
		return returnType;
	}


	private String createBoxedSignatureStringFromMethod(Element method) {
		String methodString = "";
		Element returnTypeEl = method.getChild("output", method.getNamespace());
		String methodName = method.getAttributeValue("name");
		String returnType = returnTypeEl.getAttributeValue("className");

		returnType = this.packageName + "." + getBoxedOutputTypeName(methodName);

		methodString += "\tpublic " + returnType + " " + methodName + "(";
		List inputs = method.getChild("inputs", method.getNamespace()).getChildren();

		// boxed
		methodString += this.packageName + "." + capatilzeFirstLetter(methodName) + " params";

		methodString += ")";
		return methodString;
	}


	private String createBoxedSignatureStringFromMethod(JavaMethod method) {
		String methodString = "";
		String methodName = method.getName();
		String returnType = "";

		// need to box the output type
		returnType = this.packageName + "." + getBoxedOutputTypeName(method.getName());

		methodString += "\tpublic " + returnType + " " + methodName + "(";
		Parameter[] inputs = method.getParams();
		// always boxed for now
		// if (inputs.length > 1 || inputs.length == 0) {

		// boxed
		methodString += this.packageName + "." + capatilzeFirstLetter(methodName) + " params";

		methodString += ")";
		return methodString;
	}


	private boolean isPrimitive(String type) {
		if (type.equals("int") || type.equals("double") || type.equals("float") || type.equals("boolean")
			|| type.equals("short") || type.equals("byte") || type.equals("char") || type.equals("long")) {
			return true;
		}
		return false;
	}


	private String createPrimitiveReturn(String type) {
		if (type.equals("int") || type.equals("double") || type.equals("float") || type.equals("short")
			|| type.equals("byte") || type.equals("char") || type.equals("long")) {
			return "0";
		} else if (type.equals("boolean")) {
			return "false";
		} else {
			return "RETURN VALUE";
		}
	}


	public void addMethods(List additions) {
		for (int i = 0; i < additions.size(); i++) {
			// add it to the interface
			Element method = (Element) additions.get(i);

			StringBuffer fileContent = null;
			try {
				fileContent = Utilities.fileToStringBuffer(new File(this.serviceInterface));
			} catch (Exception e) {
				e.printStackTrace();
			}

			// insert the new client method
			int endOfClass = fileContent.lastIndexOf("}");
			String clientMethod = createUnBoxedSignatureStringFromMethod(method) + " " + createExceptions(method);
			clientMethod += ";\n";

			fileContent.insert(endOfClass - 1, clientMethod);
			try {
				FileWriter fw = new FileWriter(new File(this.serviceInterface));
				fw.write(fileContent.toString());
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// populate the impl method
			addImpl(method);
			// populate the provider impl method
			addProviderImpl(method);
			// populate the client method
			addClientImpl(method);
		}
	}


	private void addClientImpl(Element method) {
		StringBuffer fileContent = null;
		String methodName = method.getAttributeValue("name");
		try {
			fileContent = Utilities.fileToStringBuffer(new File(this.serviceClient));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// insert the new client method
		int endOfClass = fileContent.lastIndexOf("}");
		String clientMethod = createUnBoxedSignatureStringFromMethod(method) + " " + createExceptions(method);
		clientMethod += "{\n\t\t";
		// clientMethod += "try{\n";
		clientMethod += "\t\t\t";

		String secureValue = "SECURITY_PROPERTY_NONE";
		if (method.getAttribute("secure") != null) {
			secureValue = "SECURITY_PROPERTY_" + method.getAttributeValue("secure");
		}
		// get the port
		// TODO: handle security here
		clientMethod += this.deploymentProperties.get("introduce.skeleton.service.name")
			+ "PortType port = this.getPortType();\n";

		clientMethod += "";

		// put in the call to the client
		String var = "port";

		String lineStart = "\t\t\t";
		List inputs = method.getChild("inputs", method.getNamespace()).getChildren();

		String methodString = lineStart;
		Element returnTypeEl = method.getChild("output", method.getNamespace());
		String returnType = returnTypeEl.getAttributeValue("className");

		// always a boxed call now becuase using complex types in the wsdl
		// create handle for the boxed wrapper
		methodString += this.packageName + "." + capatilzeFirstLetter(methodName) + " params = new " + this.packageName
			+ "." + capatilzeFirstLetter(methodName) + "();\n";
		// set the values fo the boxed wrapper
		for (int j = 0; j < inputs.size(); j++) {
			String paramName = ((Element) inputs.get(j)).getAttributeValue("name");
			methodString += lineStart;
			methodString += "params.set" + capatilzeFirstLetter(paramName) + "(" + paramName + ");\n";
		}
		// make the call
		methodString += lineStart;

		// always boxed returns now because of complex types in wsdl
		String returnTypeBoxed = getBoxedOutputTypeName(methodName);
		methodString += this.packageName + "." + returnTypeBoxed + " boxedResult = " + var + "." + methodName
			+ "(params);\n";
		methodString += lineStart;
		if (!returnType.equals("void")) {
			methodString += "return boxedResult.getValue();\n";
		}

		clientMethod += methodString;

		// clientMethod += "\t\t} catch(Exception e)
		// {\n\t\t\te.printStackTrace();\n\t\t}\n";
		// Element methodReturn = method.getChild("output",
		// method.getNamespace());
		// if (!methodReturn.getAttributeValue("className").equals("void")) {
		// if (!isPrimitive(returnType)) {
		// clientMethod += "\t\treturn null;";
		// } else if (isPrimitive(returnType)) {
		// clientMethod += "\t\treturn "
		// + createPrimitiveReturn(methodReturn
		// .getAttributeValue("className")) + ";\n";
		// }
		// }

		clientMethod += "\n\t}\n\n";

		fileContent.insert(endOfClass - 1, clientMethod);
		try {
			FileWriter fw = new FileWriter(new File(this.serviceClient));
			fw.write(fileContent.toString());
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}


	private void addImpl(Element method) {
		StringBuffer fileContent = null;
		try {
			fileContent = Utilities.fileToStringBuffer(new File(this.serviceImpl));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// insert the new client method
		int endOfClass = fileContent.lastIndexOf("}");
		String clientMethod = createUnBoxedSignatureStringFromMethod(method) + " " + createExceptions(method);

		clientMethod += "{\n";
		clientMethod += "\t\t//TODO: Implement this autogenerated method\n";
		Element methodReturn = method.getChild("output", method.getNamespace());
		if (!methodReturn.getAttributeValue("className").equals("void")
			&& !isPrimitive(methodReturn.getAttributeValue("className"))) {
			clientMethod += "\t\treturn null;\n";
		} else if (isPrimitive(methodReturn.getAttributeValue("className"))) {
			clientMethod += "\t\treturn " + createPrimitiveReturn(methodReturn.getAttributeValue("className")) + ";\n";
		}
		clientMethod += "\t}\n";

		fileContent.insert(endOfClass - 1, clientMethod);
		try {
			FileWriter fw = new FileWriter(new File(this.serviceImpl));
			fw.write(fileContent.toString());
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	private void addProviderImpl(Element method) {
		StringBuffer fileContent = null;
		try {
			fileContent = Utilities.fileToStringBuffer(new File(this.serviceProviderImpl));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// insert the new client method
		int endOfClass = fileContent.lastIndexOf("}");
		// slh -- in migration to globus 4 we need to check here for autoboxing
		// and get appropriate
		String clientMethod = createBoxedSignatureStringFromMethod(method) + " " + createExceptions(method);

		// clientMethod += " throws RemoteException";
		clientMethod += "{\n";

		// create the unboxed call to the implementation
		String var = "impl";
		String lineStart = "\t\t";
		List inputs = method.getChild("inputs", method.getNamespace()).getChildren();
		String methodName = method.getAttributeValue("name");
		String methodString = "";
		Element returnTypeEl = method.getChild("output", method.getNamespace());
		String returnType = returnTypeEl.getAttributeValue("className");

		// unbox the params
		String params = "";
		// always unbox now
		if (inputs.size() >= 1) {
			// inputs were boxed and need to be unboxed
			for (int j = 0; j < inputs.size(); j++) {
				String paramName = ((Element) inputs.get(j)).getAttributeValue("name");
				params += "params.get" + capatilzeFirstLetter(paramName) + "()";
				if (j < inputs.size() - 1) {
					params += ",";
				}
			}
		} else {
			// inputs are not boxed and can just be passed through
			for (int j = 0; j < inputs.size(); j++) {
				String paramName = ((Element) inputs.get(j)).getAttributeValue("name");
				params += paramName;
				if (j < inputs.size() - 1) {
					params += ",";
				}
			}
		}

		// return the boxed type
		String returnTypeBoxed = getBoxedOutputTypeName(methodName);
		if (returnType.equals("void")) {
			// just call it is void
			methodString += lineStart;
			methodString += var + "." + methodName + "(" + params + ");\n";
			methodString += lineStart;
			methodString += "return new " + this.packageName + "." + returnTypeBoxed + "();\n";
		} else {
			// need to unbox on the way out
			methodString += lineStart;
			methodString += this.packageName + "." + returnTypeBoxed + " boxedResult = new " + this.packageName + "."
				+ returnTypeBoxed + "();\n";
			methodString += lineStart;
			methodString += "boxedResult.setValue(" + var + "." + methodName + "(" + params + "));\n";
			methodString += lineStart;
			methodString += "return boxedResult;\n";
		}

		clientMethod += methodString;
		clientMethod += "\t}\n";
		fileContent.insert(endOfClass - 1, clientMethod);

		try {
			FileWriter fw = new FileWriter(new File(this.serviceProviderImpl));
			fw.write(fileContent.toString());
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}


	public void removeMethods(List removals) {
		for (int i = 0; i < removals.size(); i++) {
			JavaMethod method = (JavaMethod) removals.get(i);

			StringBuffer fileContent = null;
			try {
				fileContent = Utilities.fileToStringBuffer(new File(this.serviceInterface));
			} catch (Exception e) {
				e.printStackTrace();
			}

			// remove the method
			String clientMethod = createUnBoxedSignatureStringFromMethod(method);
			clientMethod += " throws RemoteException ;\n";
			int startOfMethod = fileContent.indexOf(clientMethod);
			int endOfMethod = startOfMethod + clientMethod.length();

			if (startOfMethod == -1 || endOfMethod == -1) {
				System.err.println("WARNING: Unable to locate method in I : " + method.getName());
				return;
			}

			fileContent.delete(startOfMethod, endOfMethod);

			try {
				FileWriter fw = new FileWriter(new File(this.serviceInterface));
				fw.write(fileContent.toString());
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// remove the impl method
			removeImpl(method);
			// remove the provider impl method
			removeProviderImpl(method);
			// remove the client method
			removeClientImpl(method);
		}
	}


	private void removeClientImpl(JavaMethod method) {
		StringBuffer fileContent = null;
		try {
			fileContent = Utilities.fileToStringBuffer(new File(this.serviceClient));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// remove the method
		String clientMethod = createUnBoxedSignatureStringFromMethod(method);
		int startOfMethod = fileContent.indexOf(clientMethod);
		int endOfMethod = parenMatch(fileContent, startOfMethod + clientMethod.length());

		if (startOfMethod == -1 || endOfMethod == -1) {
			System.err.println("WARNING: Unable to locate method in clientImpl : " + method.getName());
			return;
		}

		fileContent.delete(startOfMethod, endOfMethod);

		try {
			FileWriter fw = new FileWriter(new File(this.serviceClient));
			fw.write(fileContent.toString());
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	private void removeProviderImpl(JavaMethod method) {
		StringBuffer fileContent = null;
		try {
			fileContent = Utilities.fileToStringBuffer(new File(this.serviceProviderImpl));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// remove the method
		String clientMethod = createBoxedSignatureStringFromMethod(method);
		int startOfMethod = fileContent.indexOf(clientMethod);
		int endOfMethod = parenMatch(fileContent, startOfMethod + clientMethod.length());

		if (startOfMethod == -1 || endOfMethod == -1) {
			System.err.println("WARNING: Unable to locate method in providerImpl : " + method.getName());
			return;
		}

		fileContent.delete(startOfMethod, endOfMethod);

		try {
			FileWriter fw = new FileWriter(new File(this.serviceProviderImpl));
			fw.write(fileContent.toString());
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}


	private void removeImpl(JavaMethod method) {
		StringBuffer fileContent = null;
		try {
			fileContent = Utilities.fileToStringBuffer(new File(this.serviceImpl));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// remove the method
		String clientMethod = createUnBoxedSignatureStringFromMethod(method);
		int startOfMethod = fileContent.indexOf(clientMethod);
		int endOfMethod = parenMatch(fileContent, startOfMethod + clientMethod.length());

		if (startOfMethod == -1 || endOfMethod == -1) {
			System.err.println("WARNING: Unable to locate method in Impl : " + method.getName());
			return;
		}

		fileContent.delete(startOfMethod, endOfMethod);

		try {
			FileWriter fw = new FileWriter(new File(this.serviceImpl));
			fw.write(fileContent.toString());
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}


	private int parenMatch(StringBuffer sb, int startingIndex) {
		int parenCount = 0;
		int index = startingIndex;
		boolean found = false;
		boolean canFind = false;
		while (!found) {
			char ch = sb.charAt(index);
			if (ch == '{') {
				canFind = true;
				parenCount++;
			} else if (ch == '}') {
				parenCount--;
				if (canFind == true) {
					if (parenCount == 0) {
						found = true;
					}
				}
			}
			index++;
		}
		return index;
	}
}
