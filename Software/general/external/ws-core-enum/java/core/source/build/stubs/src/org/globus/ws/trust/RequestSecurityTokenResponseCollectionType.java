/**
 * RequestSecurityTokenResponseCollectionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.trust;

public class RequestSecurityTokenResponseCollectionType  implements java.io.Serializable {
    private org.globus.ws.trust.RequestSecurityTokenResponseType[] requestSecurityTokenResponse;

    public RequestSecurityTokenResponseCollectionType() {
    }

    public RequestSecurityTokenResponseCollectionType(
           org.globus.ws.trust.RequestSecurityTokenResponseType[] requestSecurityTokenResponse) {
           this.requestSecurityTokenResponse = requestSecurityTokenResponse;
    }


    /**
     * Gets the requestSecurityTokenResponse value for this RequestSecurityTokenResponseCollectionType.
     * 
     * @return requestSecurityTokenResponse
     */
    public org.globus.ws.trust.RequestSecurityTokenResponseType[] getRequestSecurityTokenResponse() {
        return requestSecurityTokenResponse;
    }


    /**
     * Sets the requestSecurityTokenResponse value for this RequestSecurityTokenResponseCollectionType.
     * 
     * @param requestSecurityTokenResponse
     */
    public void setRequestSecurityTokenResponse(org.globus.ws.trust.RequestSecurityTokenResponseType[] requestSecurityTokenResponse) {
        this.requestSecurityTokenResponse = requestSecurityTokenResponse;
    }

    public org.globus.ws.trust.RequestSecurityTokenResponseType getRequestSecurityTokenResponse(int i) {
        return this.requestSecurityTokenResponse[i];
    }

    public void setRequestSecurityTokenResponse(int i, org.globus.ws.trust.RequestSecurityTokenResponseType _value) {
        this.requestSecurityTokenResponse[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestSecurityTokenResponseCollectionType)) return false;
        RequestSecurityTokenResponseCollectionType other = (RequestSecurityTokenResponseCollectionType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.requestSecurityTokenResponse==null && other.getRequestSecurityTokenResponse()==null) || 
             (this.requestSecurityTokenResponse!=null &&
              java.util.Arrays.equals(this.requestSecurityTokenResponse, other.getRequestSecurityTokenResponse())));
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
        if (getRequestSecurityTokenResponse() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequestSecurityTokenResponse());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequestSecurityTokenResponse(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestSecurityTokenResponseCollectionType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/trust", "RequestSecurityTokenResponseCollectionType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestSecurityTokenResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/trust", "RequestSecurityTokenResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/trust", "RequestSecurityTokenResponseType"));
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
