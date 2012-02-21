/**
 * RenewResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2004._09.enumeration;

public class RenewResponse  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.xmlsoap.schemas.ws._2004._09.enumeration.ExpirationType expires;
    private org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext;
    private org.apache.axis.message.MessageElement [] _any;

    public RenewResponse() {
    }

    public RenewResponse(
           org.apache.axis.message.MessageElement [] _any,
           org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext,
           org.xmlsoap.schemas.ws._2004._09.enumeration.ExpirationType expires) {
           this.expires = expires;
           this.enumerationContext = enumerationContext;
           this._any = _any;
    }


    /**
     * Gets the expires value for this RenewResponse.
     * 
     * @return expires
     */
    public org.xmlsoap.schemas.ws._2004._09.enumeration.ExpirationType getExpires() {
        return expires;
    }


    /**
     * Sets the expires value for this RenewResponse.
     * 
     * @param expires
     */
    public void setExpires(org.xmlsoap.schemas.ws._2004._09.enumeration.ExpirationType expires) {
        this.expires = expires;
    }


    /**
     * Gets the enumerationContext value for this RenewResponse.
     * 
     * @return enumerationContext
     */
    public org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType getEnumerationContext() {
        return enumerationContext;
    }


    /**
     * Sets the enumerationContext value for this RenewResponse.
     * 
     * @param enumerationContext
     */
    public void setEnumerationContext(org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext) {
        this.enumerationContext = enumerationContext;
    }


    /**
     * Gets the _any value for this RenewResponse.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this RenewResponse.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RenewResponse)) return false;
        RenewResponse other = (RenewResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.expires==null && other.getExpires()==null) || 
             (this.expires!=null &&
              this.expires.equals(other.getExpires()))) &&
            ((this.enumerationContext==null && other.getEnumerationContext()==null) || 
             (this.enumerationContext!=null &&
              this.enumerationContext.equals(other.getEnumerationContext()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any())));
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
        if (getExpires() != null) {
            _hashCode += getExpires().hashCode();
        }
        if (getEnumerationContext() != null) {
            _hashCode += getEnumerationContext().hashCode();
        }
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
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
        new org.apache.axis.description.TypeDesc(RenewResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", ">RenewResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expires");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "Expires"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "ExpirationType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enumerationContext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "EnumerationContext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "EnumerationContextType"));
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
