/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.impl.security.authorization;

import org.globus.wsrf.security.SecurityManager;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;

import org.globus.wsrf.impl.security.util.AuthUtil;

import org.globus.util.I18n;

import com.sun.xacml.ParsingException;
import com.sun.xacml.UnknownIdentifierException;
import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.attr.AttributeFactory;
import com.sun.xacml.attr.AttributeValue;
import com.sun.xacml.ctx.Attribute;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.ctx.Subject;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.SelectorModule;

import org.apache.axis.AxisFault;
import org.apache.axis.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.handler.MessageContext;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.security.Principal;

import org.globus.wsrf.security.authorization.PDPConstants;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;

/**
 * This class evaluates and stores XACML based policies on a service or 
 * resource instance level. The id specified when initilizing the chain is 
 * used to key the policy in the database. XACML Attributes that have been 
 * collected by PIPs or PDPs preceeding this PDP in the chain may be used as 
 * environment attributes in XACML policy rule conditions. For more
 * information about the API see the {@link ServicePDP} documentation.
 * @see ServicePDP
 * @see ServiceInterceptor 
 * @see ServiceAuthorizationChain 
 * @see XMLDB
 */ 
public class XACMLPDP implements org.globus.wsrf.security.authorization.PDP {
    
    private static I18n i18n = I18n.getI18n(PDPConstants.RESOURCE,
					    XACMLPDP.class.getClassLoader());
    private static Log logger = LogFactory.getLog(XACMLPDP.class.getName());
    
    private PDP pdp = null;
    private ServicePolicyFinderModule servicePolicyModule = null;
    private XMLDB db = null;
    private String serviceId = null;
    private String serviceOwner;
    private ArrayList allowedOperations;
    private boolean initialized;
    private org.globus.wsrf.security.authorization.PDPConfig config;
    private String name;

    public static final String POLICY_FILE_NAME = "policyFile";

    public static final String[] POLICY_NAMES = 
	new String[] { "urn:oasis:names:tc:xacml:1.0:policy" };

    public XACMLPDP() {
        this.servicePolicyModule = new ServicePolicyFinderModule();
        PolicyFinder policyFinder = new PolicyFinder();
        Set policyModules = new HashSet();
        policyModules.add(servicePolicyModule);
        policyFinder.setModules(policyModules);

        CurrentEnvModule envAttributeModule = new CurrentEnvModule();
        SelectorModule selectorAttributeModule = new SelectorModule();

        AttributeFinder attributeFinder = new AttributeFinder();
        List attributeModules = new ArrayList();
        attributeModules.add(envAttributeModule);
        attributeModules.add(selectorAttributeModule);
        attributeFinder.setModules(attributeModules);

        this.pdp = new PDP(new PDPConfig(attributeFinder, policyFinder, null));
    }

    public String[] getPolicyNames() {
	return POLICY_NAMES;
    }

    private void initOwner() throws InitializeException {
        SecurityManager manager = SecurityManager.getManager(); 
	String creator = manager.getCaller();
	if (creator != null) {
	    this.serviceOwner = creator;
	} else {
	    // No caller credentials found. Use container credentials instead.
	    javax.security.auth.Subject systemSubject = null;
	    try {
	        systemSubject = manager.getSystemSubject();
		    
	    } catch (Exception e) {
		throw new InitializeException(i18n.getMessage("systemSubject"),
                                              e);
	    }
	    try {
	        Principal principal = (Principal) systemSubject.getPrincipals()
                    .iterator().next();
	        creator = principal.getName(); 
	    } catch (Exception e) {
		throw new InitializeException(i18n.getMessage("containerCred"),
                                              e);
	    }
	}
	if (logger.isDebugEnabled()) {
	    logger.debug("Setting service admin to:\n  " + creator);
	}
	this.serviceOwner = creator;
    }

    /**
     * Initializes a default policy for the service, where the service creator
     * has full access to all service operations. Others are unauthorized.
     */
    private void initPolicy() throws InitializeException {

	String policy = null;
        // try to get file name from config
        String policyFile = (String)this.config.getProperty(this.name, 
                                                            POLICY_FILE_NAME);
        if (policyFile != null) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(policyFile));
                String str = null;
                while ((str = reader.readLine()) != null) {
                    if (policy == null) {
                        policy = str;
                    } else {
                        policy = policy + str;
                    }
                }
            } catch (IOException exp) {
                logger.error(exp);
                throw new InitializeException(i18n.getMessage("policyFile",
                                                              new Object[]
                                                              { policyFile }),
                                              exp);
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        } else {

            // Set default policy
            initOwner();
            String policyOwner = this.serviceOwner.replaceAll(" ","__s__");
            policy = 
                XACMLPDPConstants.DEFAULT_POLICY
                .replaceAll(XACMLPDPConstants.SERVICE_OWNER, policyOwner);
        }
        
	InputStream policyStream = new ByteArrayInputStream(policy.getBytes());
	Document doc = null;
	Element defaultPolicy = null;
	try {
	    // Create new factory -> not thread safe according to API
	    doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(policyStream);
	    defaultPolicy = doc.getDocumentElement();
	} catch (Exception e) {
	    throw new InitializeException(i18n.getMessage("defaultParse"), e);
	}
	
	if (logger.isDebugEnabled()) {
	    logger.debug("Default policy:\n" + policy);
	}
	
	this.servicePolicyModule.setPolicy(defaultPolicy);
	try {
	    this.db.store(defaultPolicy);
	} catch (Exception e) {
	    throw new InitializeException("Default policy could not be stored",
                                          e);
	}
    }

    /**
     * Loads policy document from database.
     */
    private boolean loadPolicy() throws InitializeException {
	logger.debug("Trying to load service policy from DB...");
	Element policy = null;
	try {
	    policy = this.db.load();
	} catch (Exception e) {
	    throw new InitializeException("Failed to load service " 
                                          + this.serviceId, e);
	}
	if (policy == null) {
	    return false;
	}
	if (logger.isDebugEnabled()) {
	    logger.debug("Loaded policy:\n" +
		     XMLUtils.ElementToString(policy));
	}
	this.servicePolicyModule.setPolicy(policy);
	logger.debug("Service policy successfully loaded from DB.");
	return true;
    }
    
    /**
     * Evaluates the given request and returns the Response that the PDP
     * will hand back to the PEP.
     *
     * @param peerSubject The caller subject.     
     * @param operation The operation.
     *
     * @return The result of the evaluation
     *
     * @throws IOException if there is a problem accessing the file
     * @throws ParsingException if the Request is invalid
     */
    public ResponseCtx evaluate(javax.security.auth.Subject peerSubject,
				String operation)
        throws IOException, ParsingException, URISyntaxException, Exception {
	HashSet subjects = new HashSet(); 
	HashSet resources = new HashSet();
	HashSet actions = new HashSet();
	HashSet environment = new HashSet();

	// Create PDP request Subject attributes
	String subjectStr = AuthUtil.getIdentity(peerSubject);	
	Attribute subjectAttribute = createAttribute
	    ("urn:oasis:names:tc:xacml:1.0:subject:subject-id",
	     "http://www.w3.org/2001/XMLSchema#string",	    
	     subjectStr);
	HashSet attributes = new HashSet();
	attributes.add(subjectAttribute);
	Subject subject = new Subject(attributes);
	subjects.add(subject);

	// Create PDP request Resource attributes
	Attribute resourceAttribute = createAttribute
	    ("urn:oasis:names:tc:xacml:1.0:resource:resource-id",
	     "http://www.w3.org/2001/XMLSchema#string",	    
	     "Service");       
	resources.add(resourceAttribute);	
	
	// Create PDP request Action attributes
	Attribute actionAttribute = createAttribute
	    ("urn:oasis:names:tc:xacml:1.0:action:action-id",
	     "http://www.w3.org/2001/XMLSchema#string",	    
	     operation);	
	actions.add(actionAttribute);

	/* Create PDP request Environment attributes. Environment attributes
	   are passed through the public credentials of the peer subject object
	 */
	environment.addAll(peerSubject.getPublicCredentials(Attribute.class));
	
        RequestCtx request = new RequestCtx(subjects, resources, actions, 
                                            environment);
        // evaluate the request
        return pdp.evaluate(request);
    }
    
    public boolean isPermitted(javax.security.auth.Subject peerSubject,
	                       MessageContext context,
			       QName operation) throws AuthorizationException {
	
        if (this.allowedOperations.contains(operation.getLocalPart())) {
	    return true;
	};
	Principal principal = (Principal) peerSubject.getPrincipals()
            .iterator().next();
	if (this.serviceOwner.equals(principal.getName())) {
	    return true;
	}
        // Evaluate the request
        ResponseCtx response = null;
	try {
            response = evaluate(peerSubject, operation.toString());
	} catch (Exception e) {
	    throw new AuthorizationException(i18n.getMessage("evaluation"), e);
	}

        // Get the Result set.
	Set results = response.getResults();
	if (results.size() == 1) {
	    Iterator iterator = results.iterator(); 
	    Result result = (Result)iterator.next();
	    if (result.getDecision() == Result.DECISION_PERMIT) {
		return true;
	    } else if (result.getDecision() == Result.DECISION_DENY) {
		logger.warn("Decision is deny. Status message: " 
                            + result.getStatus().getMessage());
	    } else if (result.getDecision() == Result.DECISION_INDETERMINATE) {
		logger.warn("Decision is indeterminate. Status message: " 
                            + result.getStatus().getMessage());
	    } else if (result.getDecision() == 
                       Result.DECISION_NOT_APPLICABLE) {
		logger.warn("Decision is N/A. Status message: " 
                            + result.getStatus().getMessage());
	    }
	}
	logger.warn("service owner: " + this.serviceOwner);
	return false;
    }

    public void 
        initialize(org.globus.wsrf.security.authorization.PDPConfig config, 
                           String name, String id) 
	    throws InitializeException {
	this.config = config;
	this.name = name;
	initAllowedOperations();
	this.serviceId = id;
	logger.debug("Service id is: " + this.serviceId);
	/* Initialize (create if necessary) database policy collection */
	try {
	    this.db = new XMLDB(XACMLPDPConstants.DB_COLLECTION, 
                                this.serviceId);
	    this.initialized = true;
	} catch (Exception e) {
	    throw new InitializeException(i18n.getMessage("dbCreate"), e);
	}

	/* Try to load policy from DB */
	if (!loadPolicy()) {
	    logger.debug("Policy for service " + serviceId +
			 " could not be loaded.\n" +
			 "Initializing new policy...");
	    /* We failed to load policy, so we assume that the policy does
	       not exist. This is the case if the (parent) service is created
	       for the first time and not (re)loaded (on server restart). Thus,
	       create a new policy. */
	    initPolicy();
	}
	if (this.serviceOwner == null) {
	    String policyOwner = 
		((Element)this.servicePolicyModule.getPolicy())
                .getAttribute(XACMLPDPConstants.OWNER_ATTRIBUTE);
	    this.serviceOwner = policyOwner.replaceAll("__s__"," ");
	}
    }
    
    public Node setPolicy(Node policy) throws InvalidPolicyException {
	try {
	    this.db.store((Element)policy);
	} catch (Exception e) {
	    throw new InvalidPolicyException(i18n.getMessage("dbStore"), e);
	}
	this.servicePolicyModule.setPolicy(policy);
	return null;
    }

    public Node getPolicy(Node policy) throws InvalidPolicyException {
	return this.servicePolicyModule.getPolicy();
    }

    /**
     * Called on destruction of parent Grid service. Removes policy from DB.
     */
    public void close() throws CloseException {
	if (!initialized) {
	    return;
	}
	String dbKey = this.serviceId;
	
	try {
	    this.db.remove();
	    logger.debug("Service policy successfully removed from DB.");
	} catch (Exception e) {
	    throw new CloseException(i18n.getMessage("dbRemove"), e);
	}
    }
    
    private void initAllowedOperations() {
	if (this.allowedOperations == null) {
	    this.allowedOperations = new ArrayList();
            String operations =	(String) this.config
                .getProperty(this.name, 
                             XACMLPDPConstants.ALLOWED_OPERATIONS);
	    if (operations == null) {
	        return;
	    }
	    StringTokenizer tokenizer = new StringTokenizer(operations);
	    while (tokenizer.hasMoreTokens()) {
	        String op = tokenizer.nextToken();
		this.allowedOperations.add(op);
	    }
	}
    }

    /**
     * Convenience method for creating a XACML Attribute. The value Object must
     * provide a valid implementation of the toString method.
     *
     * @param id - the id URI of the attribute
     * @param type - the type URI of the attribute value
     * @param value - the actual value of the attribute
     * @return The Attribute object.
     */    
    public static Attribute createAttribute(String id, String type, 
                                            Object value)
	throws URISyntaxException, UnknownIdentifierException, 
               ParsingException  {
	
	URI idURI = new URI(id);
	URI typeURI = new URI(type);
        AttributeValue attributeValue = 
	    AttributeFactory.createAttribute(typeURI, value.toString());
        return new Attribute(idURI, null, null, attributeValue);	
    }
}
