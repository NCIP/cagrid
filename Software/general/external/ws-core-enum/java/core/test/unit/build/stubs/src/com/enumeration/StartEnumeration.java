/**
 * StartEnumeration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.enumeration;

public class StartEnumeration  implements java.io.Serializable {
    private int iteratorType;
    private boolean persistent;

    public StartEnumeration() {
    }

    public StartEnumeration(
           int iteratorType,
           boolean persistent) {
           this.iteratorType = iteratorType;
           this.persistent = persistent;
    }


    /**
     * Gets the iteratorType value for this StartEnumeration.
     * 
     * @return iteratorType
     */
    public int getIteratorType() {
        return iteratorType;
    }


    /**
     * Sets the iteratorType value for this StartEnumeration.
     * 
     * @param iteratorType
     */
    public void setIteratorType(int iteratorType) {
        this.iteratorType = iteratorType;
    }


    /**
     * Gets the persistent value for this StartEnumeration.
     * 
     * @return persistent
     */
    public boolean isPersistent() {
        return persistent;
    }


    /**
     * Sets the persistent value for this StartEnumeration.
     * 
     * @param persistent
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StartEnumeration)) return false;
        StartEnumeration other = (StartEnumeration) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.iteratorType == other.getIteratorType() &&
            this.persistent == other.isPersistent();
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
        _hashCode += getIteratorType();
        _hashCode += (isPersistent() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StartEnumeration.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://enumeration.com", ">startEnumeration"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("iteratorType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://enumeration.com", "iteratorType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("persistent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://enumeration.com", "persistent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
