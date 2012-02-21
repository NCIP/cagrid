/**
 * TContextFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2002._07.utility;

public class TContextFault implements java.io.Serializable {
    private javax.xml.namespace.QName _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TContextFault(javax.xml.namespace.QName value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final javax.xml.namespace.QName _value1 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/07/utility}ContextEstablished");
    public static final javax.xml.namespace.QName _value2 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/07/utility}ContextUnknown");
    public static final javax.xml.namespace.QName _value3 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/07/utility}ContextNotSupported");
    public static final javax.xml.namespace.QName _value4 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/07/utility}ContextRefused");
    public static final javax.xml.namespace.QName _value5 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/07/utility}ContextExpired");
    public static final TContextFault value1 = new TContextFault(_value1);
    public static final TContextFault value2 = new TContextFault(_value2);
    public static final TContextFault value3 = new TContextFault(_value3);
    public static final TContextFault value4 = new TContextFault(_value4);
    public static final TContextFault value5 = new TContextFault(_value5);
    public javax.xml.namespace.QName getValue() { return _value_;}
    public static TContextFault fromValue(javax.xml.namespace.QName value)
          throws java.lang.IllegalArgumentException {
        TContextFault enumeration = (TContextFault)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TContextFault fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        try {
            return fromValue(javax.xml.namespace.QName.valueOf(value));
        } catch (Exception e) {
            throw new java.lang.IllegalArgumentException();
        }
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_.toString();}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TContextFault.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "tContextFault"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
