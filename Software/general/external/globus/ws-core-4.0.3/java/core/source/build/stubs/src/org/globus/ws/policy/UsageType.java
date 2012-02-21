/**
 * UsageType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;

public class UsageType implements java.io.Serializable {
    private javax.xml.namespace.QName _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected UsageType(javax.xml.namespace.QName value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final javax.xml.namespace.QName _value1 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/12/policy}Required");
    public static final javax.xml.namespace.QName _value2 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/12/policy}Optional");
    public static final javax.xml.namespace.QName _value3 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/12/policy}Rejected");
    public static final javax.xml.namespace.QName _value4 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/12/policy}Ignored");
    public static final javax.xml.namespace.QName _value5 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2002/12/policy}Observed");
    public static final UsageType value1 = new UsageType(_value1);
    public static final UsageType value2 = new UsageType(_value2);
    public static final UsageType value3 = new UsageType(_value3);
    public static final UsageType value4 = new UsageType(_value4);
    public static final UsageType value5 = new UsageType(_value5);
    public javax.xml.namespace.QName getValue() { return _value_;}
    public static UsageType fromValue(javax.xml.namespace.QName value)
          throws java.lang.IllegalArgumentException {
        UsageType enumeration = (UsageType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static UsageType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(UsageType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "UsageType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
