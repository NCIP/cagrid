/**
 * FaultCodes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.sc;

public class FaultCodes implements java.io.Serializable {
    private javax.xml.namespace.QName _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FaultCodes(javax.xml.namespace.QName value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final javax.xml.namespace.QName _value1 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2004/04/sc}BadContextToken");
    public static final javax.xml.namespace.QName _value2 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2004/04/sc}UnsupportedContextToken");
    public static final javax.xml.namespace.QName _value3 = javax.xml.namespace.QName.valueOf("{http://schemas.xmlsoap.org/ws/2004/04/sc}UnknownDerivationSource");
    public static final FaultCodes value1 = new FaultCodes(_value1);
    public static final FaultCodes value2 = new FaultCodes(_value2);
    public static final FaultCodes value3 = new FaultCodes(_value3);
    public javax.xml.namespace.QName getValue() { return _value_;}
    public static FaultCodes fromValue(javax.xml.namespace.QName value)
          throws java.lang.IllegalArgumentException {
        FaultCodes enumeration = (FaultCodes)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FaultCodes fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(FaultCodes.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "FaultCodes"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
