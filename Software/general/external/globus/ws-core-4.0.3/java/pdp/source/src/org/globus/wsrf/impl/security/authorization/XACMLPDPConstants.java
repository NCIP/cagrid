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

public class XACMLPDPConstants {
    public static final String SERVICE_OWNER = "__ServiceOwner__";
    public static final String OWNER_ATTRIBUTE = "PolicyId";
    public static final String DB_COLLECTION = "servicePolicy";

    public static final String DEFAULT_POLICY =
	"<Policy PolicyId=\"" + SERVICE_OWNER + "\"" +
	"   RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">" +
	"    <Target>" +
	"      <Subjects>" +
	"          <AnySubject/>" + 
	"      </Subjects>" +
	"      <Resources>"+ 
	"        <AnyResource/>" +
	"      </Resources>" +
	"      <Actions>" + 
	"        <AnyAction/>" +
	"      </Actions>" + 
	"    </Target>" +
        "    <Rule RuleId=\"OthersRule\" Effect=\"Deny\"/>" + 
	"</Policy>";

    public static final String ALLOWED_OPERATIONS = "allowedPDPOperations";
}
