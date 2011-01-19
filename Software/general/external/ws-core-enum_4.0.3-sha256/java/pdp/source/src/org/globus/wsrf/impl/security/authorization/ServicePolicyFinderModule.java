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

import org.globus.util.I18n;

import com.sun.xacml.AbstractPolicy;
import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.ParsingException;
import com.sun.xacml.Policy;
import com.sun.xacml.PolicyReference;
import com.sun.xacml.PolicySet;
import com.sun.xacml.ctx.Status;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.PolicyFinderModule;
import com.sun.xacml.finder.PolicyFinderResult;

import org.w3c.dom.Node;

import java.net.URI;
import java.util.ArrayList;

import org.globus.wsrf.security.authorization.PDPConstants;

/**
 * class used internally by the XACMLPDP to evaluate XACML policies
 */
public class ServicePolicyFinderModule extends PolicyFinderModule {

    private static I18n i18n = 
        I18n.getI18n(PDPConstants.RESOURCE,
                     ServicePolicyFinderModule.class.getClassLoader());

    private PolicyFinder finder;
    private Node root;

    public void setPolicy(Node policy) {
	this.root = policy;
    }
    
    public Node getPolicy() {
	return this.root;
    }

    public ServicePolicyFinderModule() {
        // any initialization you need to do, for example a table that
        // caches previously loaded policies
    }

    /**
     * Always return true, since indeed this class supports references.
     *
     * @return true
     */
    public boolean isIdReferenceSupported() {
        return true;
    }
    
    public boolean isRequestSupported() {
        return true;
    }

    /**
     * Called when the <code>PolicyFinder</code> initializes. This lets your
     * code keep track of the finder, which is especially useful if you have
     * to create policy sets.
     *
     * @param finder the <code>PolicyFinder</code> that's using this module
     */
    public void init(PolicyFinder finder) {
        this.finder = finder;
    }
    
    public PolicyFinderResult findPolicy(EvaluationCtx context) {
	return findPolicy(null,0);
    }

    /**
     * A partially implemented version of the method that is invoked to find
     * a policy reference. In this case, we first check that the URI is
     * one we know how to handle, then we fetch the policy, and if needed,
     * we parse the XML and create a new AbstractPolicy handling errors
     * correctly. Obviously the steps involved in fetching the policy are
     * left out here.
     *
     * @param idReference an identifier specifying some policy
     * @param type type of reference (policy or policySet) as identified by
     *             the fields in <code>PolicyReference</code>
     *
     * @return the result of looking for the referenced policy
     */    
    public PolicyFinderResult findPolicy(URI idReference, int type) {
        AbstractPolicy policy;

        try {
            if (type == PolicyReference.POLICY_REFERENCE) {
                policy = Policy.getInstance(this.root);
            } else {
                policy = PolicySet.getInstance(this.root, finder);
            }
        } catch (Exception pe) {
            ArrayList code = new ArrayList();
	    String message = i18n.getMessage("parsePolicy", new Object[] 
                { idReference, pe.getMessage() });

            code.add(Status.STATUS_SYNTAX_ERROR);

            return new PolicyFinderResult(new Status(code, message));
        }

        return new PolicyFinderResult(policy);
    }
}
