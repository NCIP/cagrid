/**
 * OpenUsageType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;


/**
 * Per GXA norms, we allow other values that are not pre-defined.
 */
public class OpenUsageType  implements java.io.Serializable, org.apache.axis.encoding.SimpleType {
    private java.lang.String _value;
    public OpenUsageType() {
    }

    // Simple Types must have a String constructor
    public OpenUsageType(java.lang.String _value) {
        this._value = _value;
    }
    public OpenUsageType(org.globus.ws.policy.UsageType _value) {
        setUsageTypeValue(_value);
    }

    public OpenUsageType(javax.xml.namespace.QName _value) {
        setQNameValue(_value);
    }

    // Simple Types must have a toString for serializing the value
    public java.lang.String toString() {
        return _value;
    }


    /**
     * Gets the usageTypeValue value for this OpenUsageType.
     * 
     * @return usageTypeValue
     */
    public org.globus.ws.policy.UsageType getUsageTypeValue() {
        return org.globus.ws.policy.UsageType.fromString(_value);
    }


    /**
     * Sets the _value value for this OpenUsageType.
     * 
     * @param _value
     */
    public void setUsageTypeValue(org.globus.ws.policy.UsageType _value) {
        this._value = _value == null ? null : _value.toString();
    }


    /**
     * Gets the QNameValue value for this OpenUsageType.
     * 
     * @return QNameValue
     */
    public javax.xml.namespace.QName getQNameValue() {
        return new javax.xml.namespace.QName(_value);
    }


    /**
     * Sets the _value value for this OpenUsageType.
     * 
     * @param _value
     */
    public void setQNameValue(javax.xml.namespace.QName _value) {
        this._value = _value == null ? null : _value.toString();
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OpenUsageType)) return false;
        OpenUsageType other = (OpenUsageType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&  this.toString().equals(obj.toString());
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
        if (this._value != null) {
            _hashCode += this._value.hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OpenUsageType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "OpenUsageType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usageTypeValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UsageTypeValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "UsageType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("QNameValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QNameValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "QName"));
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
          new  org.apache.axis.encoding.ser.SimpleSerializer(
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
          new  org.apache.axis.encoding.ser.SimpleDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
