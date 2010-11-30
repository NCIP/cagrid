/**
 * SubscriptionManagerSOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.notification.bindings;

public class SubscriptionManagerSOAPBindingStub extends org.apache.axis.client.Stub implements org.globus.wsrf.core.notification.SubscriptionManager {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[5];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
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
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Destroy");
        oper.addParameter(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "Destroy"), new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">Destroy"), org.oasis.wsrf.lifetime.Destroy.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">DestroyResponse"));
        oper.setReturnClass(org.oasis.wsrf.lifetime.DestroyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "DestroyResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceNotDestroyedFault"),
                      "org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceNotDestroyedFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceUnknownFault"),
                      "org.oasis.wsrf.lifetime.ResourceUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceUnknownFaultType"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SetTerminationTime");
        oper.addParameter(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "SetTerminationTime"), new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">SetTerminationTime"), org.oasis.wsrf.lifetime.SetTerminationTime.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">SetTerminationTimeResponse"));
        oper.setReturnClass(org.oasis.wsrf.lifetime.SetTerminationTimeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "SetTerminationTimeResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "UnableToSetTerminationTimeFault"),
                      "org.oasis.wsrf.lifetime.UnableToSetTerminationTimeFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "UnableToSetTerminationTimeFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceUnknownFault"),
                      "org.oasis.wsrf.lifetime.ResourceUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceUnknownFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTimeChangeRejectedFault"),
                      "org.oasis.wsrf.lifetime.TerminationTimeChangeRejectedFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTimeChangeRejectedFaultType"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PauseSubscription");
        oper.addParameter(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "PauseSubscription"), new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">PauseSubscription"), org.oasis.wsn.PauseSubscription.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">PauseSubscriptionResponse"));
        oper.setReturnClass(org.oasis.wsn.PauseSubscriptionResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "PauseSubscriptionResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "PauseFailedFault"),
                      "org.oasis.wsn.PauseFailedFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "PauseFailedFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFault"),
                      "org.oasis.wsn.ResourceUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFaultType"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ResumeSubscription");
        oper.addParameter(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResumeSubscription"), new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">ResumeSubscription"), org.oasis.wsn.ResumeSubscription.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">ResumeSubscriptionResponse"));
        oper.setReturnClass(org.oasis.wsn.ResumeSubscriptionResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResumeSubscriptionResponse"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResumeFailedFault"),
                      "org.oasis.wsn.ResumeFailedFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResumeFailedFaultType"), 
                      true
                     ));
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFault"),
                      "org.oasis.wsn.ResourceUnknownFaultType",
                      new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFaultType"), 
                      true
                     ));
        _operations[4] = oper;

    }

    public SubscriptionManagerSOAPBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SubscriptionManagerSOAPBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SubscriptionManagerSOAPBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "RelationshipTypeValues");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.RelationshipTypeValues.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification", ">SubscriptionManagerGTWSDLResourceProperties");
            cachedSerQNames.add(qName);
            cls = org.globus.wsrf.core.notification.SubscriptionManagerGTWSDLResourceProperties.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ReplyAfterType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.ReplyAfterType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", ">BaseFaultType>ErrorCode");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.faults.BaseFaultTypeErrorCode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">SubscriptionManagerRP");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.SubscriptionManagerRP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", ">BaseFaultType>Description");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.faults.BaseFaultTypeDescription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "AttributedURI");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.AttributedURI.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">Destroy");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.Destroy.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">ResumeSubscription");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResumeSubscription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">SetResourceProperties");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.SetResourceProperties_Element.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">ResourcePropertyValueChangeNotificationType>NewValue");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeNewValue.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicExpressionType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.TopicExpressionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetMultipleResourcePropertiesResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResumeFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResumeFailedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">NotificationProducerRP");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.NotificationProducerRP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">TerminationNotification");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.TerminationNotification.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">SubscribeResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.SubscribeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ServiceNameType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.ServiceNameType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">QueryResourceProperties");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryResourceProperties_Element.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTimeChangeRejectedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.TerminationTimeChangeRejectedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">GetCurrentMessageResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.GetCurrentMessageResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "QueryEvaluationErrorFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryEvaluationErrorFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">Subscribe");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.Subscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">PauseSubscription");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.PauseSubscription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">SetTerminationTime");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.SetTerminationTime.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.EndpointReferenceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidSetResourcePropertiesRequestContentFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InvalidSetResourcePropertiesRequestContentFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "Relationship");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.Relationship.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">ResourcePropertyValueChangeNotificationType>OldValue");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeOldValue.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "NotificationMessageHolderType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.NotificationMessageHolderType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "ReferencePropertiesType");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.ReferencePropertiesType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">QueryResourcePropertiesResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryResourcePropertiesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ResourceUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResourceUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">GetCurrentMessage");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.GetCurrentMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourceUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourceUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">Notify");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.Notify.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetResourcePropertyResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.GetResourcePropertyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "InvalidTopicExpressionFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.InvalidTopicExpressionFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">DestroyResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.DestroyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "UnableToModifyResourcePropertyFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.UnableToModifyResourcePropertyFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "FaultSubcodeValues");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.FaultSubcodeValues.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.ResourceUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "SubscribeCreationFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.SubscribeCreationFailedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "BaseFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.faults.BaseFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidResourcePropertyQNameFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicNotSupportedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.TopicNotSupportedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "UnknownQueryExpressionDialectFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "AttributedQName");
            cachedSerQNames.add(qName);
            cls = org.apache.axis.message.addressing.AttributedQName.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(simplesf);
            cachedDeserFactories.add(simpledf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourcePropertyValueChangeNotificationType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetMultipleResourceProperties");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.GetMultipleResourceProperties_Element.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">ScheduledResourceTerminationRP");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.ScheduledResourceTerminationRP.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InsertType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InsertType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "DeleteType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.DeleteType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">PauseSubscriptionResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.PauseSubscriptionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", ">SetTerminationTimeResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.SetTerminationTimeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "UpdateType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.UpdateType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "NoCurrentMessageOnTopicFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.NoCurrentMessageOnTopicFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "ResourceNotDestroyedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "InvalidQueryExpressionFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.InvalidQueryExpressionFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicPathDialectUnknownFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.TopicPathDialectUnknownFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">SetResourcePropertiesResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.SetResourcePropertiesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "QueryExpressionType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.QueryExpressionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "UnableToSetTerminationTimeFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.lifetime.UnableToSetTerminationTimeFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "SetResourcePropertyRequestFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsrf.properties.SetResourcePropertyRequestFailedFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">ResumeSubscriptionResponse");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.ResumeSubscriptionResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "PauseFailedFaultType");
            cachedSerQNames.add(qName);
            cls = org.oasis.wsn.PauseFailedFaultType.class;
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

    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
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

    public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy destroyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType, org.oasis.wsrf.lifetime.ResourceUnknownFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime/Destroy");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "Destroy"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {destroyRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.oasis.wsrf.lifetime.DestroyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.oasis.wsrf.lifetime.DestroyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.oasis.wsrf.lifetime.DestroyResponse.class);
            }
        }
    }

    public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime setTerminationTimeRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.UnableToSetTerminationTimeFaultType, org.oasis.wsrf.lifetime.ResourceUnknownFaultType, org.oasis.wsrf.lifetime.TerminationTimeChangeRejectedFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime/SetTerminationTime");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "SetTerminationTime"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {setTerminationTimeRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.oasis.wsrf.lifetime.SetTerminationTimeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.oasis.wsrf.lifetime.SetTerminationTimeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.oasis.wsrf.lifetime.SetTerminationTimeResponse.class);
            }
        }
    }

    public org.oasis.wsn.PauseSubscriptionResponse pauseSubscription(org.oasis.wsn.PauseSubscription pauseSubscriptionRequest) throws java.rmi.RemoteException, org.oasis.wsn.PauseFailedFaultType, org.oasis.wsn.ResourceUnknownFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification/PauseSubscription");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "PauseSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {pauseSubscriptionRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.oasis.wsn.PauseSubscriptionResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.oasis.wsn.PauseSubscriptionResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.oasis.wsn.PauseSubscriptionResponse.class);
            }
        }
    }

    public org.oasis.wsn.ResumeSubscriptionResponse resumeSubscription(org.oasis.wsn.ResumeSubscription resumeSubscriptionRequest) throws java.rmi.RemoteException, org.oasis.wsn.ResumeFailedFaultType, org.oasis.wsn.ResourceUnknownFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification/ResumeSubscription");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ResumeSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {resumeSubscriptionRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.oasis.wsn.ResumeSubscriptionResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.oasis.wsn.ResumeSubscriptionResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.oasis.wsn.ResumeSubscriptionResponse.class);
            }
        }
    }

}
