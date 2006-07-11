/**
 * SolicitResponseOrNotificationOperation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Mar 03, 2006 (12:17:06 EST) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.wsdl;

public class SolicitResponseOrNotificationOperation  implements java.io.Serializable {
    private org.xmlsoap.schemas.wsdl.TParam output;
    private org.xmlsoap.schemas.wsdl.TParam input;
    private org.xmlsoap.schemas.wsdl.TFault[] fault;

    public SolicitResponseOrNotificationOperation() {
    }

    public SolicitResponseOrNotificationOperation(
           org.xmlsoap.schemas.wsdl.TFault[] fault,
           org.xmlsoap.schemas.wsdl.TParam input,
           org.xmlsoap.schemas.wsdl.TParam output) {
           this.output = output;
           this.input = input;
           this.fault = fault;
    }


    /**
     * Gets the output value for this SolicitResponseOrNotificationOperation.
     * 
     * @return output
     */
    public org.xmlsoap.schemas.wsdl.TParam getOutput() {
        return output;
    }


    /**
     * Sets the output value for this SolicitResponseOrNotificationOperation.
     * 
     * @param output
     */
    public void setOutput(org.xmlsoap.schemas.wsdl.TParam output) {
        this.output = output;
    }


    /**
     * Gets the input value for this SolicitResponseOrNotificationOperation.
     * 
     * @return input
     */
    public org.xmlsoap.schemas.wsdl.TParam getInput() {
        return input;
    }


    /**
     * Sets the input value for this SolicitResponseOrNotificationOperation.
     * 
     * @param input
     */
    public void setInput(org.xmlsoap.schemas.wsdl.TParam input) {
        this.input = input;
    }


    /**
     * Gets the fault value for this SolicitResponseOrNotificationOperation.
     * 
     * @return fault
     */
    public org.xmlsoap.schemas.wsdl.TFault[] getFault() {
        return fault;
    }


    /**
     * Sets the fault value for this SolicitResponseOrNotificationOperation.
     * 
     * @param fault
     */
    public void setFault(org.xmlsoap.schemas.wsdl.TFault[] fault) {
        this.fault = fault;
    }

    public org.xmlsoap.schemas.wsdl.TFault getFault(int i) {
        return this.fault[i];
    }

    public void setFault(int i, org.xmlsoap.schemas.wsdl.TFault _value) {
        this.fault[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SolicitResponseOrNotificationOperation)) return false;
        SolicitResponseOrNotificationOperation other = (SolicitResponseOrNotificationOperation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.output==null && other.getOutput()==null) || 
             (this.output!=null &&
              this.output.equals(other.getOutput()))) &&
            ((this.input==null && other.getInput()==null) || 
             (this.input!=null &&
              this.input.equals(other.getInput()))) &&
            ((this.fault==null && other.getFault()==null) || 
             (this.fault!=null &&
              java.util.Arrays.equals(this.fault, other.getFault())));
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
        if (getOutput() != null) {
            _hashCode += getOutput().hashCode();
        }
        if (getInput() != null) {
            _hashCode += getInput().hashCode();
        }
        if (getFault() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFault());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFault(), i);
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
        new org.apache.axis.description.TypeDesc(SolicitResponseOrNotificationOperation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "solicit-response-or-notification-operation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("output");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "output"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "tParam"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("input");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "input"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "tParam"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fault");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "fault"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "tFault"));
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
