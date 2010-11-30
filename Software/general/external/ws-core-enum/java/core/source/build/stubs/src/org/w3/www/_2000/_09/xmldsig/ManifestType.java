/**
 * ManifestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.w3.www._2000._09.xmldsig;

public class ManifestType  implements java.io.Serializable {
    private org.w3.www._2000._09.xmldsig.ReferenceType[] reference;
    private org.apache.axis.types.Id id;  // attribute

    public ManifestType() {
    }

    public ManifestType(
           org.apache.axis.types.Id id,
           org.w3.www._2000._09.xmldsig.ReferenceType[] reference) {
           this.reference = reference;
           this.id = id;
    }


    /**
     * Gets the reference value for this ManifestType.
     * 
     * @return reference
     */
    public org.w3.www._2000._09.xmldsig.ReferenceType[] getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this ManifestType.
     * 
     * @param reference
     */
    public void setReference(org.w3.www._2000._09.xmldsig.ReferenceType[] reference) {
        this.reference = reference;
    }

    public org.w3.www._2000._09.xmldsig.ReferenceType getReference(int i) {
        return this.reference[i];
    }

    public void setReference(int i, org.w3.www._2000._09.xmldsig.ReferenceType _value) {
        this.reference[i] = _value;
    }


    /**
     * Gets the id value for this ManifestType.
     * 
     * @return id
     */
    public org.apache.axis.types.Id getId() {
        return id;
    }


    /**
     * Sets the id value for this ManifestType.
     * 
     * @param id
     */
    public void setId(org.apache.axis.types.Id id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ManifestType)) return false;
        ManifestType other = (ManifestType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              java.util.Arrays.equals(this.reference, other.getReference()))) &&
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
        if (getReference() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReference());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReference(), i);
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
        new org.apache.axis.description.TypeDesc(ManifestType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "ManifestType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("id");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Id"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ID"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "ReferenceType"));
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
