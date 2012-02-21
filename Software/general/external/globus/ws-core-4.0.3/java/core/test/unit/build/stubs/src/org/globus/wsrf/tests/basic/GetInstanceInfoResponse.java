/**
 * GetInstanceInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.basic;

public class GetInstanceInfoResponse  implements java.io.Serializable {
    private int instances;
    private int initCalls;
    private int destroyCalls;
    private boolean providerInit;

    public GetInstanceInfoResponse() {
    }

    public GetInstanceInfoResponse(
           int destroyCalls,
           int initCalls,
           int instances,
           boolean providerInit) {
           this.instances = instances;
           this.initCalls = initCalls;
           this.destroyCalls = destroyCalls;
           this.providerInit = providerInit;
    }


    /**
     * Gets the instances value for this GetInstanceInfoResponse.
     * 
     * @return instances
     */
    public int getInstances() {
        return instances;
    }


    /**
     * Sets the instances value for this GetInstanceInfoResponse.
     * 
     * @param instances
     */
    public void setInstances(int instances) {
        this.instances = instances;
    }


    /**
     * Gets the initCalls value for this GetInstanceInfoResponse.
     * 
     * @return initCalls
     */
    public int getInitCalls() {
        return initCalls;
    }


    /**
     * Sets the initCalls value for this GetInstanceInfoResponse.
     * 
     * @param initCalls
     */
    public void setInitCalls(int initCalls) {
        this.initCalls = initCalls;
    }


    /**
     * Gets the destroyCalls value for this GetInstanceInfoResponse.
     * 
     * @return destroyCalls
     */
    public int getDestroyCalls() {
        return destroyCalls;
    }


    /**
     * Sets the destroyCalls value for this GetInstanceInfoResponse.
     * 
     * @param destroyCalls
     */
    public void setDestroyCalls(int destroyCalls) {
        this.destroyCalls = destroyCalls;
    }


    /**
     * Gets the providerInit value for this GetInstanceInfoResponse.
     * 
     * @return providerInit
     */
    public boolean isProviderInit() {
        return providerInit;
    }


    /**
     * Sets the providerInit value for this GetInstanceInfoResponse.
     * 
     * @param providerInit
     */
    public void setProviderInit(boolean providerInit) {
        this.providerInit = providerInit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetInstanceInfoResponse)) return false;
        GetInstanceInfoResponse other = (GetInstanceInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.instances == other.getInstances() &&
            this.initCalls == other.getInitCalls() &&
            this.destroyCalls == other.getDestroyCalls() &&
            this.providerInit == other.isProviderInit();
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
        _hashCode += getInstances();
        _hashCode += getInitCalls();
        _hashCode += getDestroyCalls();
        _hashCode += (isProviderInit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetInstanceInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/basic", ">getInstanceInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instances");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/basic", "instances"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("initCalls");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/basic", "initCalls"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destroyCalls");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/basic", "destroyCalls"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providerInit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/tests/basic", "providerInit"));
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
