/**
 * TPart.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Mar 03, 2006 (12:17:06 EST) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.wsdl;

public class TPart  extends org.xmlsoap.schemas.wsdl.TExtensibleAttributesDocumented  implements java.io.Serializable {
    private org.apache.axis.types.NCName name;  // attribute
    private javax.xml.namespace.QName element;  // attribute
    private javax.xml.namespace.QName type;  // attribute

    public TPart() {
    }

    public TPart(
           javax.xml.namespace.QName element,
           org.apache.axis.types.NCName name,
           javax.xml.namespace.QName type) {
           this.name = name;
           this.element = element;
           this.type = type;
    }


    /**
     * Gets the name value for this TPart.
     * 
     * @return name
     */
    public org.apache.axis.types.NCName getName() {
        return name;
    }


    /**
     * Sets the name value for this TPart.
     * 
     * @param name
     */
    public void setName(org.apache.axis.types.NCName name) {
        this.name = name;
    }


    /**
     * Gets the element value for this TPart.
     * 
     * @return element
     */
    public javax.xml.namespace.QName getElement() {
        return element;
    }


    /**
     * Sets the element value for this TPart.
     * 
     * @param element
     */
    public void setElement(javax.xml.namespace.QName element) {
        this.element = element;
    }


    /**
     * Gets the type value for this TPart.
     * 
     * @return type
     */
    public javax.xml.namespace.QName getType() {
        return type;
    }


    /**
     * Sets the type value for this TPart.
     * 
     * @param type
     */
    public void setType(javax.xml.namespace.QName type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TPart)) return false;
        TPart other = (TPart) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.element==null && other.getElement()==null) || 
             (this.element!=null &&
              this.element.equals(other.getElement()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getElement() != null) {
            _hashCode += getElement().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TPart.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/wsdl/", "tPart"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("name");
        attrField.setXmlName(new javax.xml.namespace.QName("", "name"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "NCName"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("element");
        attrField.setXmlName(new javax.xml.namespace.QName("", "element"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "QName"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("type");
        attrField.setXmlName(new javax.xml.namespace.QName("", "type"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "QName"));
        typeDesc.addFieldDesc(attrField);
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
