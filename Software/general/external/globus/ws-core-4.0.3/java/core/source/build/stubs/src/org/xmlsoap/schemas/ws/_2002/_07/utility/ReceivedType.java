/**
 * ReceivedType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2002._07.utility;


/**
 * This type extends AnnotatedDateTime to add a Delay attribute.
 */
public class ReceivedType  implements java.io.Serializable, org.apache.axis.encoding.SimpleType {
    private org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime _value;
    private java.lang.Integer delay;  // attribute
    private org.apache.axis.types.URI actor;  // attribute

    public ReceivedType() {
    }

    // Simple Types must have a String constructor
    public ReceivedType(org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime _value) {
        this._value = _value;
    }
    public ReceivedType(java.lang.String _value) {
        this._value = new org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime(_value);
    }

    // Simple Types must have a toString for serializing the value
    public java.lang.String toString() {
        return _value == null ? null : _value.toString();
    }


    /**
     * Gets the _value value for this ReceivedType.
     * 
     * @return _value
     */
    public org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime get_value() {
        return _value;
    }


    /**
     * Sets the _value value for this ReceivedType.
     * 
     * @param _value
     */
    public void set_value(org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime _value) {
        this._value = _value;
    }


    /**
     * Gets the delay value for this ReceivedType.
     * 
     * @return delay
     */
    public java.lang.Integer getDelay() {
        return delay;
    }


    /**
     * Sets the delay value for this ReceivedType.
     * 
     * @param delay
     */
    public void setDelay(java.lang.Integer delay) {
        this.delay = delay;
    }


    /**
     * Gets the actor value for this ReceivedType.
     * 
     * @return actor
     */
    public org.apache.axis.types.URI getActor() {
        return actor;
    }


    /**
     * Sets the actor value for this ReceivedType.
     * 
     * @param actor
     */
    public void setActor(org.apache.axis.types.URI actor) {
        this.actor = actor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReceivedType)) return false;
        ReceivedType other = (ReceivedType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._value==null && other.get_value()==null) || 
             (this._value!=null &&
              this._value.equals(other.get_value()))) &&
            ((this.delay==null && other.getDelay()==null) || 
             (this.delay!=null &&
              this.delay.equals(other.getDelay()))) &&
            ((this.actor==null && other.getActor()==null) || 
             (this.actor!=null &&
              this.actor.equals(other.getActor())));
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
        if (get_value() != null) {
            _hashCode += get_value().hashCode();
        }
        if (getDelay() != null) {
            _hashCode += getDelay().hashCode();
        }
        if (getActor() != null) {
            _hashCode += getActor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReceivedType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "ReceivedType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("delay");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Delay"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("actor");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Actor"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "_value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "AttributedDateTime"));
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
