/**
 * Subscribe.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsn;

public class Subscribe  implements java.io.Serializable {
    private org.apache.axis.message.addressing.EndpointReferenceType consumerReference;
    private org.oasis.wsn.TopicExpressionType topicExpression;
    private java.lang.Boolean useNotify;
    private org.oasis.wsrf.properties.QueryExpressionType precondition;
    private org.oasis.wsrf.properties.QueryExpressionType selector;
    private java.lang.Object subscriptionPolicy;
    private java.util.Calendar initialTerminationTime;

    public Subscribe() {
    }

    public Subscribe(
           org.apache.axis.message.addressing.EndpointReferenceType consumerReference,
           java.util.Calendar initialTerminationTime,
           org.oasis.wsrf.properties.QueryExpressionType precondition,
           org.oasis.wsrf.properties.QueryExpressionType selector,
           java.lang.Object subscriptionPolicy,
           org.oasis.wsn.TopicExpressionType topicExpression,
           java.lang.Boolean useNotify) {
           this.consumerReference = consumerReference;
           this.topicExpression = topicExpression;
           this.useNotify = useNotify;
           this.precondition = precondition;
           this.selector = selector;
           this.subscriptionPolicy = subscriptionPolicy;
           this.initialTerminationTime = initialTerminationTime;
    }


    /**
     * Gets the consumerReference value for this Subscribe.
     * 
     * @return consumerReference
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getConsumerReference() {
        return consumerReference;
    }


    /**
     * Sets the consumerReference value for this Subscribe.
     * 
     * @param consumerReference
     */
    public void setConsumerReference(org.apache.axis.message.addressing.EndpointReferenceType consumerReference) {
        this.consumerReference = consumerReference;
    }


    /**
     * Gets the topicExpression value for this Subscribe.
     * 
     * @return topicExpression
     */
    public org.oasis.wsn.TopicExpressionType getTopicExpression() {
        return topicExpression;
    }


    /**
     * Sets the topicExpression value for this Subscribe.
     * 
     * @param topicExpression
     */
    public void setTopicExpression(org.oasis.wsn.TopicExpressionType topicExpression) {
        this.topicExpression = topicExpression;
    }


    /**
     * Gets the useNotify value for this Subscribe.
     * 
     * @return useNotify
     */
    public java.lang.Boolean getUseNotify() {
        return useNotify;
    }


    /**
     * Sets the useNotify value for this Subscribe.
     * 
     * @param useNotify
     */
    public void setUseNotify(java.lang.Boolean useNotify) {
        this.useNotify = useNotify;
    }


    /**
     * Gets the precondition value for this Subscribe.
     * 
     * @return precondition
     */
    public org.oasis.wsrf.properties.QueryExpressionType getPrecondition() {
        return precondition;
    }


    /**
     * Sets the precondition value for this Subscribe.
     * 
     * @param precondition
     */
    public void setPrecondition(org.oasis.wsrf.properties.QueryExpressionType precondition) {
        this.precondition = precondition;
    }


    /**
     * Gets the selector value for this Subscribe.
     * 
     * @return selector
     */
    public org.oasis.wsrf.properties.QueryExpressionType getSelector() {
        return selector;
    }


    /**
     * Sets the selector value for this Subscribe.
     * 
     * @param selector
     */
    public void setSelector(org.oasis.wsrf.properties.QueryExpressionType selector) {
        this.selector = selector;
    }


    /**
     * Gets the subscriptionPolicy value for this Subscribe.
     * 
     * @return subscriptionPolicy
     */
    public java.lang.Object getSubscriptionPolicy() {
        return subscriptionPolicy;
    }


    /**
     * Sets the subscriptionPolicy value for this Subscribe.
     * 
     * @param subscriptionPolicy
     */
    public void setSubscriptionPolicy(java.lang.Object subscriptionPolicy) {
        this.subscriptionPolicy = subscriptionPolicy;
    }


    /**
     * Gets the initialTerminationTime value for this Subscribe.
     * 
     * @return initialTerminationTime
     */
    public java.util.Calendar getInitialTerminationTime() {
        return initialTerminationTime;
    }


    /**
     * Sets the initialTerminationTime value for this Subscribe.
     * 
     * @param initialTerminationTime
     */
    public void setInitialTerminationTime(java.util.Calendar initialTerminationTime) {
        this.initialTerminationTime = initialTerminationTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Subscribe)) return false;
        Subscribe other = (Subscribe) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.consumerReference==null && other.getConsumerReference()==null) || 
             (this.consumerReference!=null &&
              this.consumerReference.equals(other.getConsumerReference()))) &&
            ((this.topicExpression==null && other.getTopicExpression()==null) || 
             (this.topicExpression!=null &&
              this.topicExpression.equals(other.getTopicExpression()))) &&
            ((this.useNotify==null && other.getUseNotify()==null) || 
             (this.useNotify!=null &&
              this.useNotify.equals(other.getUseNotify()))) &&
            ((this.precondition==null && other.getPrecondition()==null) || 
             (this.precondition!=null &&
              this.precondition.equals(other.getPrecondition()))) &&
            ((this.selector==null && other.getSelector()==null) || 
             (this.selector!=null &&
              this.selector.equals(other.getSelector()))) &&
            ((this.subscriptionPolicy==null && other.getSubscriptionPolicy()==null) || 
             (this.subscriptionPolicy!=null &&
              this.subscriptionPolicy.equals(other.getSubscriptionPolicy()))) &&
            ((this.initialTerminationTime==null && other.getInitialTerminationTime()==null) || 
             (this.initialTerminationTime!=null &&
              this.initialTerminationTime.equals(other.getInitialTerminationTime())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getConsumerReference() != null) {
            _hashCode += getConsumerReference().hashCode();
        }
        if (getTopicExpression() != null) {
            _hashCode += getTopicExpression().hashCode();
        }
        if (getUseNotify() != null) {
            _hashCode += getUseNotify().hashCode();
        }
        if (getPrecondition() != null) {
            _hashCode += getPrecondition().hashCode();
        }
        if (getSelector() != null) {
            _hashCode += getSelector().hashCode();
        }
        if (getSubscriptionPolicy() != null) {
            _hashCode += getSubscriptionPolicy().hashCode();
        }
        if (getInitialTerminationTime() != null) {
            _hashCode += getInitialTerminationTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Subscribe.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">Subscribe"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumerReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ConsumerReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topicExpression");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicExpression"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicExpressionType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useNotify");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "UseNotify"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("precondition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "Precondition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "QueryExpressionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selector");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "Selector"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "QueryExpressionType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriptionPolicy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "SubscriptionPolicy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initialTerminationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "InitialTerminationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
