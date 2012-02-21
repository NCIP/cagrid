/**
 * NotificationMessageHolderType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsn;

public class NotificationMessageHolderType  implements java.io.Serializable {
    private org.oasis.wsn.TopicExpressionType topic;
    private org.apache.axis.message.addressing.EndpointReferenceType producerReference;
    private java.lang.Object message;

    public NotificationMessageHolderType() {
    }

    public NotificationMessageHolderType(
           java.lang.Object message,
           org.apache.axis.message.addressing.EndpointReferenceType producerReference,
           org.oasis.wsn.TopicExpressionType topic) {
           this.topic = topic;
           this.producerReference = producerReference;
           this.message = message;
    }


    /**
     * Gets the topic value for this NotificationMessageHolderType.
     * 
     * @return topic
     */
    public org.oasis.wsn.TopicExpressionType getTopic() {
        return topic;
    }


    /**
     * Sets the topic value for this NotificationMessageHolderType.
     * 
     * @param topic
     */
    public void setTopic(org.oasis.wsn.TopicExpressionType topic) {
        this.topic = topic;
    }


    /**
     * Gets the producerReference value for this NotificationMessageHolderType.
     * 
     * @return producerReference
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getProducerReference() {
        return producerReference;
    }


    /**
     * Sets the producerReference value for this NotificationMessageHolderType.
     * 
     * @param producerReference
     */
    public void setProducerReference(org.apache.axis.message.addressing.EndpointReferenceType producerReference) {
        this.producerReference = producerReference;
    }


    /**
     * Gets the message value for this NotificationMessageHolderType.
     * 
     * @return message
     */
    public java.lang.Object getMessage() {
        return message;
    }


    /**
     * Sets the message value for this NotificationMessageHolderType.
     * 
     * @param message
     */
    public void setMessage(java.lang.Object message) {
        this.message = message;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotificationMessageHolderType)) return false;
        NotificationMessageHolderType other = (NotificationMessageHolderType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.topic==null && other.getTopic()==null) || 
             (this.topic!=null &&
              this.topic.equals(other.getTopic()))) &&
            ((this.producerReference==null && other.getProducerReference()==null) || 
             (this.producerReference!=null &&
              this.producerReference.equals(other.getProducerReference()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage())));
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
        if (getTopic() != null) {
            _hashCode += getTopic().hashCode();
        }
        if (getProducerReference() != null) {
            _hashCode += getProducerReference().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NotificationMessageHolderType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "NotificationMessageHolderType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "Topic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicExpressionType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producerReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "ProducerReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
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
