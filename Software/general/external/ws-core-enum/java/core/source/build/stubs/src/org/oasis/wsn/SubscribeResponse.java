/**
 * SubscribeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsn;

public class SubscribeResponse  implements java.io.Serializable {
    private org.apache.axis.message.addressing.EndpointReferenceType subscriptionReference;

    public SubscribeResponse() {
    }

    public SubscribeResponse(
           org.apache.axis.message.addressing.EndpointReferenceType subscriptionReference) {
           this.subscriptionReference = subscriptionReference;
    }


    /**
     * Gets the subscriptionReference value for this SubscribeResponse.
     * 
     * @return subscriptionReference
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getSubscriptionReference() {
        return subscriptionReference;
    }


    /**
     * Sets the subscriptionReference value for this SubscribeResponse.
     * 
     * @param subscriptionReference
     */
    public void setSubscriptionReference(org.apache.axis.message.addressing.EndpointReferenceType subscriptionReference) {
        this.subscriptionReference = subscriptionReference;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubscribeResponse)) return false;
        SubscribeResponse other = (SubscribeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.subscriptionReference==null && other.getSubscriptionReference()==null) || 
             (this.subscriptionReference!=null &&
              this.subscriptionReference.equals(other.getSubscriptionReference())));
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
        if (getSubscriptionReference() != null) {
            _hashCode += getSubscriptionReference().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubscribeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", ">SubscribeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subscriptionReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "SubscriptionReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
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
