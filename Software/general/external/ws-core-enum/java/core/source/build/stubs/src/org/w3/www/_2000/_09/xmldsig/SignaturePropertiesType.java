/**
 * SignaturePropertiesType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.w3.www._2000._09.xmldsig;

public class SignaturePropertiesType  implements java.io.Serializable {
    private org.w3.www._2000._09.xmldsig.SignaturePropertyType[] signatureProperty;
    private org.apache.axis.types.Id id;  // attribute

    public SignaturePropertiesType() {
    }

    public SignaturePropertiesType(
           org.apache.axis.types.Id id,
           org.w3.www._2000._09.xmldsig.SignaturePropertyType[] signatureProperty) {
           this.signatureProperty = signatureProperty;
           this.id = id;
    }


    /**
     * Gets the signatureProperty value for this SignaturePropertiesType.
     * 
     * @return signatureProperty
     */
    public org.w3.www._2000._09.xmldsig.SignaturePropertyType[] getSignatureProperty() {
        return signatureProperty;
    }


    /**
     * Sets the signatureProperty value for this SignaturePropertiesType.
     * 
     * @param signatureProperty
     */
    public void setSignatureProperty(org.w3.www._2000._09.xmldsig.SignaturePropertyType[] signatureProperty) {
        this.signatureProperty = signatureProperty;
    }

    public org.w3.www._2000._09.xmldsig.SignaturePropertyType getSignatureProperty(int i) {
        return this.signatureProperty[i];
    }

    public void setSignatureProperty(int i, org.w3.www._2000._09.xmldsig.SignaturePropertyType _value) {
        this.signatureProperty[i] = _value;
    }


    /**
     * Gets the id value for this SignaturePropertiesType.
     * 
     * @return id
     */
    public org.apache.axis.types.Id getId() {
        return id;
    }


    /**
     * Sets the id value for this SignaturePropertiesType.
     * 
     * @param id
     */
    public void setId(org.apache.axis.types.Id id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SignaturePropertiesType)) return false;
        SignaturePropertiesType other = (SignaturePropertiesType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.signatureProperty==null && other.getSignatureProperty()==null) || 
             (this.signatureProperty!=null &&
              java.util.Arrays.equals(this.signatureProperty, other.getSignatureProperty()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId())));
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
        if (getSignatureProperty() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSignatureProperty());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSignatureProperty(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SignaturePropertiesType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "SignaturePropertiesType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("id");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Id"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ID"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureProperty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "SignaturePropertyType"));
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
