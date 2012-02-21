/**
 * NoAuthRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.security;

public class NoAuthRequest  implements java.io.Serializable {
    private java.lang.String authzService;
    private java.lang.String authzServiceIdentity;

    public NoAuthRequest() {
    }

    public NoAuthRequest(
           java.lang.String authzService,
           java.lang.String authzServiceIdentity) {
           this.authzService = authzService;
           this.authzServiceIdentity = authzServiceIdentity;
    }


    /**
     * Gets the authzService value for this NoAuthRequest.
     * 
     * @return authzService
     */
    public java.lang.String getAuthzService() {
        return authzService;
    }


    /**
     * Sets the authzService value for this NoAuthRequest.
     * 
     * @param authzService
     */
    public void setAuthzService(java.lang.String authzService) {
        this.authzService = authzService;
    }


    /**
     * Gets the authzServiceIdentity value for this NoAuthRequest.
     * 
     * @return authzServiceIdentity
     */
    public java.lang.String getAuthzServiceIdentity() {
        return authzServiceIdentity;
    }


    /**
     * Sets the authzServiceIdentity value for this NoAuthRequest.
     * 
     * @param authzServiceIdentity
     */
    public void setAuthzServiceIdentity(java.lang.String authzServiceIdentity) {
        this.authzServiceIdentity = authzServiceIdentity;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NoAuthRequest)) return false;
        NoAuthRequest other = (NoAuthRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authzService==null && other.getAuthzService()==null) || 
             (this.authzService!=null &&
              this.authzService.equals(other.getAuthzService()))) &&
            ((this.authzServiceIdentity==null && other.getAuthzServiceIdentity()==null) || 
             (this.authzServiceIdentity!=null &&
              this.authzServiceIdentity.equals(other.getAuthzServiceIdentity())));
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
        if (getAuthzService() != null) {
            _hashCode += getAuthzService().hashCode();
        }
        if (getAuthzServiceIdentity() != null) {
            _hashCode += getAuthzServiceIdentity().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NoAuthRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", ">noAuthRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authzService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "authzService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authzServiceIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/security", "authzServiceIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
