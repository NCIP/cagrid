/**
 * SecurityTestPortTypeSOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.security.bindings;

public class SecurityTestPortTypeSOAPBindingStub extends org.apache.axis.client.Stub implements org.globus.wsrf.tests.security.SecurityTestPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[23];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("noAuth");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "noAuthRequest"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">noAuthRequest"), org.globus.wsrf.tests.security.NoAuthRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">noAuthResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.NoAuthResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "noAuthResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiTransportOnly");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiTransportOnly"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportOnly"), org.globus.wsrf.tests.security.GsiTransportOnly.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportOnlyResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiTransportOnlyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiTransportOnlyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiTransportIntegrity");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiTransportIntegrity"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportIntegrity"), org.globus.wsrf.tests.security.GsiTransportIntegrity.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportIntegrityResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiTransportIntegrityResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiTransportIntegrityResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiTransportPrivacy");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiTransportPrivacy"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportPrivacy"), org.globus.wsrf.tests.security.GsiTransportPrivacy.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportPrivacyResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiTransportPrivacyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiTransportPrivacyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSecConvOnly");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvOnly"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvOnly"), org.globus.wsrf.tests.security.GsiSecConvOnly.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvOnlyResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecConvOnlyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvOnlyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSecConvIntegrity");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvIntegrity"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvIntegrity"), org.globus.wsrf.tests.security.GsiSecConvIntegrity.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvIntegrityResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecConvIntegrityResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvIntegrityResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSecConvPrivacy");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvPrivacy"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvPrivacy"), org.globus.wsrf.tests.security.GsiSecConvPrivacy.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvPrivacyResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecConvPrivacyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvPrivacyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSecConvDeleg");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvDeleg"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvDeleg"), org.globus.wsrf.tests.security.GsiSecConvDeleg.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvDelegResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecConvDelegResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecConvDelegResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSecMsgOnly");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecMsgOnly"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgOnly"), org.globus.wsrf.tests.security.GsiSecMsgOnly.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgOnlyResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecMsgOnlyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecMsgOnlyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSecMsgPrivacy");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecMsgPrivacy"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgPrivacy"), org.globus.wsrf.tests.security.GsiSecMsgPrivacy.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgPrivacyResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecMsgPrivacyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecMsgPrivacyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSecMsgIntegrity");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecMsgIntegrity"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgIntegrity"), org.globus.wsrf.tests.security.GsiSecMsgIntegrity.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgIntegrityResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecMsgIntegrityResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecMsgIntegrityResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("gsiSec");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSec"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSec"), org.globus.wsrf.tests.security.GsiSec.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.GsiSecResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "gsiSecResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createResource");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "createResource"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">createResourceResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.CreateResourceResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "createResourceResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("alterSecurityDesc");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "alterSecurityDescRequest"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">alterSecurityDescResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.AlterSecurityDescResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "alterSecurityDescResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setAuthz");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setAuthz"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setAuthzResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.SetAuthzResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setAuthzResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setAnonymousAuthz");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setAnonymousAuthz"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setAnonymousAuthz"), org.globus.wsrf.tests.security.SetAnonymousAuthz.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setAnonymousAuthzResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.SetAnonymousAuthzResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setAnonymousAuthzResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setValue");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setValue"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setValueResponse"));
        oper.setReturnClass(org.globus.wsrf.tests.security.SetValueResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setValueResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getValue");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "getValue"), new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">getValue"), org.globus.wsrf.tests.security.GetValue.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        oper.setReturnClass(int.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "getValueResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setServiceAuthz");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setServiceAuthz"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setServiceAuthzOutput"));
        oper.setReturnClass(org.globus.wsrf.tests.security.SetServiceAuthzOutput.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "setServiceAuthzOutput"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSecurityProperty");
        oper.addParameter(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "getSecurityProperty"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "getSecurityPropertyOutput"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetResourceProperty");
        oper.addParameter(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "GetResourceProperty"), new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "QName"), javax.xml.namespace.QName.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetResourcePropertyResponse"));
        oper.setReturnClass(org.oasis.wsrf.properties.GetResourcePropertyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "GetResourcePropertyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidResourcePropertyQNameFault"),
                      "org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidResourcePropertyQNameFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourceUnknownFault"),
                      "org.oasis.wsrf.properties.ResourceUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourceUnknownFaultType"), 
                      true
                     ));
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Subscribe");
        oper.addParameter(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "Subscribe"), new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">Subscribe"), org.oasis.wsn.Subscribe.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">SubscribeResponse"));
        oper.setReturnClass(org.oasis.wsn.SubscribeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "SubscribeResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicNotSupportedFault"),
                      "org.oasis.wsn.TopicNotSupportedFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicNotSupportedFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "InvalidTopicExpressionFault"),
                      "org.oasis.wsn.InvalidTopicExpressionFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "InvalidTopicExpressionFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "SubscribeCreationFailedFault"),
                      "org.oasis.wsn.SubscribeCreationFailedFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "SubscribeCreationFailedFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFault"),
                      "org.oasis.wsn.ResourceUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicPathDialectUnknownFault"),
                      "org.oasis.wsn.TopicPathDialectUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicPathDialectUnknownFaultType"), 
                      true
                     ));
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetCurrentMessage");
        oper.addParameter(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "GetCurrentMessage"), new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">GetCurrentMessage"), org.oasis.wsn.GetCurrentMessage.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">GetCurrentMessageResponse"));
        oper.setReturnClass(org.oasis.wsn.GetCurrentMessageResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "GetCurrentMessageResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicNotSupportedFault"),
                      "org.oasis.wsn.TopicNotSupportedFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicNotSupportedFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "InvalidTopicExpressionFault"),
                      "org.oasis.wsn.InvalidTopicExpressionFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "InvalidTopicExpressionFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "NoCurrentMessageOnTopicFault"),
                      "org.oasis.wsn.NoCurrentMessageOnTopicFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "NoCurrentMessageOnTopicFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFault"),
                      "org.oasis.wsn.ResourceUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFaultType"), 
                      true
                     ));
        _operations[22] = oper;

    }

    public SecurityTestPortTypeSOAPBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SecurityTestPortTypeSOAPBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SecurityTestPortTypeSOAPBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ReferencePropertiesType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.ReferencePropertiesType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "FaultSubcodeValues");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.FaultSubcodeValues.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicPathDialectUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.TopicPathDialectUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "QueryExpressionType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryExpressionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetResourcePropertyResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.GetResourcePropertyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportOnly");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiTransportOnly.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InsertType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InsertType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">createResourceResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.CreateResourceResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "UpdateType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.UpdateType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicNotSupportedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.TopicNotSupportedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidQueryExpressionFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InvalidQueryExpressionFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">ResumeSubscriptionResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResumeSubscriptionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">GetCurrentMessage");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.GetCurrentMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourcePropertyValueChangeNotificationType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "NoCurrentMessageOnTopicFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.NoCurrentMessageOnTopicFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgOnly");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecMsgOnly.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvPrivacyResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvPrivacyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportIntegrityResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiTransportIntegrityResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">PauseSubscription");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.PauseSubscription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">QueryResourceProperties");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryResourceProperties_Element.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "Relationship");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.Relationship.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">noAuthResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.NoAuthResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.EndpointReferenceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvDelegResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvDelegResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">Subscribe");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.Subscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">getValue");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GetValue.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">alterSecurityDescResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.AlterSecurityDescResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgOnlyResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecMsgOnlyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">NotificationProducerRP");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.NotificationProducerRP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgPrivacyResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecMsgPrivacyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "QueryEvaluationErrorFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryEvaluationErrorFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgPrivacy");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecMsgPrivacy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "AttributedURI");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.AttributedURI.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvDeleg");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvDeleg.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">ResourcePropertyValueChangeNotificationType>NewValue");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeNewValue.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">ResourcePropertyValueChangeNotificationType>OldValue");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeOldValue.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgIntegrity");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecMsgIntegrity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "UnableToSetTerminationTimeFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.UnableToSetTerminationTimeFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setAnonymousAuthz");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.SetAnonymousAuthz.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "InvalidTopicExpressionFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.InvalidTopicExpressionFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "PauseFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.PauseFailedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">PauseSubscriptionResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.PauseSubscriptionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourceUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourceUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setAnonymousAuthzResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.SetAnonymousAuthzResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">ScheduledResourceTerminationRP");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.ScheduledResourceTerminationRP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidResourcePropertyQNameFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidSetResourcePropertiesRequestContentFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InvalidSetResourcePropertiesRequestContentFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ServiceNameType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.ServiceNameType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">SetResourcePropertiesResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.SetResourcePropertiesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvIntegrityResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvIntegrityResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportPrivacy");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiTransportPrivacy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicExpressionType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.TopicExpressionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecMsgIntegrityResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecMsgIntegrityResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", ">BaseFaultType>ErrorCode");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.faults.BaseFaultTypeErrorCode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvPrivacy");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvPrivacy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportOnlyResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiTransportOnlyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">DestroyResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.DestroyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "BaseFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.faults.BaseFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSec");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetMultipleResourceProperties");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.GetMultipleResourceProperties_Element.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvOnlyResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvOnlyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "UnableToModifyResourcePropertyFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.UnableToModifyResourcePropertyFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">Notify");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.Notify.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">ResumeSubscription");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResumeSubscription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportIntegrity");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiTransportIntegrity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "AttributedQName");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.AttributedQName.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", ">BaseFaultType>Description");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.faults.BaseFaultTypeDescription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvOnly");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvOnly.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResumeFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResumeFailedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">SubscriptionManagerRP");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.SubscriptionManagerRP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "NotificationMessageHolderType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.NotificationMessageHolderType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "SubscribeCreationFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.SubscribeCreationFailedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ReplyAfterType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.ReplyAfterType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setServiceAuthzOutput");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.SetServiceAuthzOutput.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.ResourceUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceNotDestroyedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiTransportPrivacyResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiTransportPrivacyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">SubscribeResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.SubscribeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "UnknownQueryExpressionDialectFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "SetResourcePropertyRequestFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.SetResourcePropertyRequestFailedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">TerminationNotification");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.TerminationNotification.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetMultipleResourcePropertiesResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">QueryResourcePropertiesResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryResourcePropertiesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">noAuthRequest");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.NoAuthRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setValueResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.SetValueResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTimeChangeRejectedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.TerminationTimeChangeRejectedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "RelationshipTypeValues");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.RelationshipTypeValues.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">setAuthzResponse");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.SetAuthzResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">GetCurrentMessageResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.GetCurrentMessageResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">SetTerminationTimeResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.SetTerminationTimeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">SetResourceProperties");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.SetResourceProperties_Element.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">SetTerminationTime");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.SetTerminationTime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">TestRP");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.TestRP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">gsiSecConvIntegrity");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.tests.security.GsiSecConvIntegrity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "DeleteType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.DeleteType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">Destroy");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.Destroy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResourceUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call =
                    (org.apache.axis.client.Call) super.service.createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public org.globus.wsrf.tests.security.NoAuthResponse noAuth(org.globus.wsrf.tests.security.NoAuthRequest parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/noAuthRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "noAuth"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.NoAuthResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.NoAuthResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.NoAuthResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiTransportOnlyResponse gsiTransportOnly(org.globus.wsrf.tests.security.GsiTransportOnly parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiTransportOnlyRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiTransportOnly"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiTransportOnlyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiTransportOnlyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiTransportOnlyResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiTransportIntegrityResponse gsiTransportIntegrity(org.globus.wsrf.tests.security.GsiTransportIntegrity parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiTransportIntegrityRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiTransportIntegrity"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiTransportIntegrityResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiTransportIntegrityResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiTransportIntegrityResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiTransportPrivacyResponse gsiTransportPrivacy(org.globus.wsrf.tests.security.GsiTransportPrivacy parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiTransportPrivacyRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiTransportPrivacy"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiTransportPrivacyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiTransportPrivacyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiTransportPrivacyResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecConvOnlyResponse gsiSecConvOnly(org.globus.wsrf.tests.security.GsiSecConvOnly parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecConvOnlyRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSecConvOnly"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecConvOnlyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecConvOnlyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecConvOnlyResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecConvIntegrityResponse gsiSecConvIntegrity(org.globus.wsrf.tests.security.GsiSecConvIntegrity parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecConvIntegrityRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSecConvIntegrity"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecConvIntegrityResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecConvIntegrityResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecConvIntegrityResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecConvPrivacyResponse gsiSecConvPrivacy(org.globus.wsrf.tests.security.GsiSecConvPrivacy parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecConvPrivacyRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSecConvPrivacy"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecConvPrivacyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecConvPrivacyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecConvPrivacyResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecConvDelegResponse gsiSecConvDeleg(org.globus.wsrf.tests.security.GsiSecConvDeleg parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecConvDelegRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSecConvDeleg"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecConvDelegResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecConvDelegResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecConvDelegResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecMsgOnlyResponse gsiSecMsgOnly(org.globus.wsrf.tests.security.GsiSecMsgOnly parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecMsgOnlyRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSecMsgOnly"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecMsgOnlyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecMsgOnlyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecMsgOnlyResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecMsgPrivacyResponse gsiSecMsgPrivacy(org.globus.wsrf.tests.security.GsiSecMsgPrivacy parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecMsgPrivacyRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSecMsgPrivacy"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecMsgPrivacyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecMsgPrivacyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecMsgPrivacyResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecMsgIntegrityResponse gsiSecMsgIntegrity(org.globus.wsrf.tests.security.GsiSecMsgIntegrity parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecMsgIntegrityRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSecMsgIntegrity"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecMsgIntegrityResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecMsgIntegrityResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecMsgIntegrityResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.GsiSecResponse gsiSec(org.globus.wsrf.tests.security.GsiSec parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/gsiSecRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "gsiSec"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.GsiSecResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.GsiSecResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.GsiSecResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.CreateResourceResponse createResource(boolean parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/createResourceRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "createResource"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Boolean(parameters)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.CreateResourceResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.CreateResourceResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.CreateResourceResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.AlterSecurityDescResponse alterSecurityDesc(java.lang.String parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/alterSecurityDescRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "alterSecurityDesc"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.AlterSecurityDescResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.AlterSecurityDescResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.AlterSecurityDescResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.SetAuthzResponse setAuthz(java.lang.String parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/setAuthzRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "setAuthz"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.SetAuthzResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.SetAuthzResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.SetAuthzResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.SetAnonymousAuthzResponse setAnonymousAuthz(org.globus.wsrf.tests.security.SetAnonymousAuthz parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/setAnonymousAuthzRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "setAnonymousAuthz"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.SetAnonymousAuthzResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.SetAnonymousAuthzResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.SetAnonymousAuthzResponse.class);
            }
        }
    }

    public org.globus.wsrf.tests.security.SetValueResponse setValue(int parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/setValueRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "setValue"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Integer(parameters)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.SetValueResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.SetValueResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.SetValueResponse.class);
            }
        }
    }

    public int getValue(org.globus.wsrf.tests.security.GetValue parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/getValueRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getValue"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Integer) _resp).intValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Integer) org.apache.axis.utils.JavaUtils.convert(_resp, int.class)).intValue();
            }
        }
    }

    public org.globus.wsrf.tests.security.SetServiceAuthzOutput setServiceAuthz(java.lang.String parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/setServiceAuthzRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "setServiceAuthz"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.globus.wsrf.tests.security.SetServiceAuthzOutput) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.globus.wsrf.tests.security.SetServiceAuthzOutput) org.apache.axis.utils.JavaUtils.convert(_resp, org.globus.wsrf.tests.security.SetServiceAuthzOutput.class);
            }
        }
    }

    public java.lang.String getSecurityProperty(java.lang.String parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://wsrf.globus.org/tests/security/SecurityTestPortType/getSecurityPropertyRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "getSecurityProperty"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
    }

    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties/GetResourceProperty");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "GetResourceProperty"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getResourcePropertyRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.oasis.wsrf.properties.GetResourcePropertyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.oasis.wsrf.properties.GetResourcePropertyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.oasis.wsrf.properties.GetResourcePropertyResponse.class);
            }
        }
    }

    public org.oasis.wsn.SubscribeResponse subscribe(org.oasis.wsn.Subscribe subscribeRequest) throws java.rmi.RemoteException, org.oasis.wsn.TopicNotSupportedFaultType, org.oasis.wsn.InvalidTopicExpressionFaultType, org.oasis.wsn.SubscribeCreationFailedFaultType, org.oasis.wsn.ResourceUnknownFaultType, org.oasis.wsn.TopicPathDialectUnknownFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification/Subscribe");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "Subscribe"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {subscribeRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.oasis.wsn.SubscribeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.oasis.wsn.SubscribeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.oasis.wsn.SubscribeResponse.class);
            }
        }
    }

    public org.oasis.wsn.GetCurrentMessageResponse getCurrentMessage(org.oasis.wsn.GetCurrentMessage getCurrentMessageRequest) throws java.rmi.RemoteException, org.oasis.wsn.TopicNotSupportedFaultType, org.oasis.wsn.InvalidTopicExpressionFaultType, org.oasis.wsn.NoCurrentMessageOnTopicFaultType, org.oasis.wsn.ResourceUnknownFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification/GetCurrentMessage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "GetCurrentMessage"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getCurrentMessageRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.oasis.wsn.GetCurrentMessageResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.oasis.wsn.GetCurrentMessageResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.oasis.wsn.GetCurrentMessageResponse.class);
            }
        }
    }

}
