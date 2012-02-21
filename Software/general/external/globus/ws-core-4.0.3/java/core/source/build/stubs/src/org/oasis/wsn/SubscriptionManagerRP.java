/**
 * SubscriptionManagerRP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsn;

public class SubscriptionManagerRP  implements java.io.Serializable {
    private java.util.Calendar currentTime;
    private java.util.Calendar terminationTime;
    private org.apache.axis.message.addressing.EndpointReferenceType consumerReference;
    private org.oasis.wsn.TopicExpressionType topicExpression;
    private boolean useNotify;
    private org.oasis.wsrf.properties.QueryExpressionType precondition;
    private org.oasis.wsrf.properties.QueryExpressionType selector;
    private java.lang.Object subscriptionPolicy;
    private java.util.Calendar creationTime;

    public SubscriptionManagerRP() {
    }

    public SubscriptionManagerRP(
           org.apache.axis.message.addressing.EndpointReferenceType consumerReference,
           java.util.Calendar creationTime,
           java.util.Calendar currentTime,
           org.oasis.wsrf.properties.QueryExpressionType precondition,
           org.oasis.wsrf.properties.QueryExpressionType selector,
           java.lang.Object subscriptionPolicy,
           java.util.Calendar terminationTime,
           org.oasis.wsn.TopicExpressionType topicExpression,
           boolean useNotify) {
           this.currentTime = currentTime;
           this.terminationTime = terminationTime;
           this.consumerReference = consumerReference;
           this.topicExpression = topicExpression;
           this.useNotify = useNotify;
           this.precondition = precondition;
           this.selector = selector;
           this.subscriptionPolicy = subscriptionPolicy;
           this.creationTime = creationTime;
    }


    /**
     * Gets the currentTime value for this SubscriptionManagerRP.
     * 
     * @return currentTime
     */
    public java.util.Calendar getCurrentTime() {
        return currentTime;
    }


    /**
     * Sets the currentTime value for this SubscriptionManagerRP.
     * 
     * @param currentTime
     */
    public void setCurrentTime(java.util.Calendar currentTime) {
        this.currentTime = currentTime;
    }


    /**
     * Gets the terminationTime value for this SubscriptionManagerRP.
     * 
     * @return terminationTime
     */
    public java.util.Calendar getTerminationTime() {
        return terminationTime;
    }


    /**
     * Sets the terminationTime value for this SubscriptionManagerRP.
     * 
     * @param terminationTime
     */
    public void setTerminationTime(java.util.Calendar terminationTime) {
        this.terminationTime = terminationTime;
    }


    /**
     * Gets the consumerReference value for this SubscriptionManagerRP.
     * 
     * @return consumerReference
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getConsumerReference() {
        return consumerReference;
    }


    /**
     * Sets the consumerReference value for this SubscriptionManagerRP.
     * 
     * @param consumerReference
     */
    public void setConsumerReference(org.apache.axis.message.addressing.EndpointReferenceType consumerReference) {
        this.consumerReference = consumerReference;
    }


    /**
     * Gets the topicExpression value for this SubscriptionManagerRP.
     * 
     * @return topicExpression
     */
    public org.oasis.wsn.TopicExpressionType getTopicExpression() {
        return topicExpression;
    }


    /**
     * Sets the topicExpression value for this SubscriptionManagerRP.
     * 
     * @param topicExpression
     */
    public void setTopicExpression(org.oasis.wsn.TopicExpressionType topicExpression) {
        this.topicExpression = topicExpression;
    }


    /**
     * Gets the useNotify value for this SubscriptionManagerRP.
     * 
     * @return useNotify
     */
    public boolean isUseNotify() {
        return useNotify;
    }


    /**
     * Sets the useNotify value for this SubscriptionManagerRP.
     * 
     * @param useNotify
     */
    public void setUseNotify(boolean useNotify) {
        this.useNotify = useNotify;
    }


    /**
     * Gets the precondition value for this SubscriptionManagerRP.
     * 
     * @return precondition
     */
    public org.oasis.wsrf.properties.QueryExpressionType getPrecondition() {
        return precondition;
    }


    /**
     * Sets the precondition value for this SubscriptionManagerRP.
     * 
     * @param precondition
     */
    public void setPrecondition(org.oasis.wsrf.properties.QueryExpressionType precondition) {
        this.precondition = precondition;
    }


    /**
     * Gets the selector value for this SubscriptionManagerRP.
     * 
     * @return selector
     */
    public org.oasis.wsrf.properties.QueryExpressionType getSelector() {
        return selector;
    }


    /**
     * Sets the selector value for this SubscriptionManagerRP.
     * 
     * @param selector
     */
    public void setSelector(org.oasis.wsrf.properties.QueryExpressionType selector) {
        this.selector = selector;
    }


    /**
     * Gets the subscriptionPolicy value for this SubscriptionManagerRP.
     * 
     * @return subscriptionPolicy
     */
    public java.lang.Object getSubscriptionPolicy() {
        return subscriptionPolicy;
    }


    /**
     * Sets the subscriptionPolicy value for this SubscriptionManagerRP.
     * 
     * @param subscriptionPolicy
     */
    public void setSubscriptionPolicy(java.lang.Object subscriptionPolicy) {
        this.subscriptionPolicy = subscriptionPolicy;
    }


    /**
     * Gets the creationTime value for this SubscriptionManagerRP.
     * 
     * @return creationTime
     */
    public java.util.Calendar getCreationTime() {
        return creationTime;
    }


    /**
     * Sets the creationTime value for this SubscriptionManagerRP.
     * 
     * @param creationTime
     */
    public void setCreationTime(java.util.Calendar creationTime) {
        this.creationTime = creationTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubscriptionManagerRP)) return false;
        SubscriptionManagerRP other = (SubscriptionManagerRP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.currentTime==null && other.getCurrentTime()==null) || 
             (this.currentTime!=null &&
              this.currentTime.equals(other.getCurrentTime()))) &&
            ((this.terminationTime==null && other.getTerminationTime()==null) || 
             (this.terminationTime!=null &&
              this.terminationTime.equals(other.getTerminationTime()))) &&
            ((this.consumerReference==null && other.getConsumerReference()==null) || 
             (this.consumerReference!=null &&
              this.consumerReference.equals(other.getConsumerReference()))) &&
            ((this.topicExpression==null && other.getTopicExpression()==null) || 
             (this.topicExpression!=null &&
              this.topicExpression.equals(other.getTopicExpression()))) &&
            this.useNotify == other.isUseNotify() &&
            ((this.precondition==null && other.getPrecondition()==null) || 
             (this.precondition!=null &&
              this.precondition.equals(other.getPrecondition()))) &&
            ((this.selector==null && other.getSelector()==null) || 
             (this.selector!=null &&
              this.selector.equals(other.getSelector()))) &&
            ((this.subscriptionPolicy==null && other.getSubscriptionPolicy()==null) || 
             (this.subscriptionPolicy!=null &&
              this.subscriptionPolicy.equals(other.getSubscriptionPolicy()))) &&
            ((this.creationTime==null && other.getCreationTime()==null) || 
             (this.creationTime!=null &&
              this.creationTime.equals(other.getCreationTime())));
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
        if (getCurrentTime() != null) {
            _hashCode += getCurrentTime().hashCode();
        }
        if (getTerminationTime() != null) {
            _hashCode += getTerminationTime().hashCode();
        }
        if (getConsumerReference() != null) {
            _hashCode += getConsumerReference().hashCode();
        }
        if (getTopicExpression() != null) {
            _hashCode += getTopicExpression().hashCode();
        }
        _hashCode += (isUseNotify() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPrecondition() != null) {
            _hashCode += getPrecondition().hashCode();
        }
        if (getSelector() != null) {
            _hashCode += getSelector().hashCode();
        }
        if (getSubscriptionPolicy() != null) {
            _hashCode += getSubscriptionPolicy().hashCode();
        }
        if (getCreationTime() != null) {
            _hashCode += getCreationTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubscriptionManagerRP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">SubscriptionManagerRP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "CurrentTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("creationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "CreationTime"));
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
