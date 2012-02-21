/**
 * DerivedKeyTokenType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.sc;

public class DerivedKeyTokenType  implements java.io.Serializable {
    private org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0_xsd.SecurityTokenReferenceType securityTokenReference;
    private org.globus.ws.sc.PropertiesType properties;
    private org.apache.axis.types.UnsignedLong generation;
    private org.apache.axis.types.UnsignedLong offset;
    private org.apache.axis.types.UnsignedLong length;
    private java.lang.String label;
    private byte[] nonce;
    private org.apache.axis.types.Id id;  // attribute
    private org.apache.axis.types.URI algorithm;  // attribute

    public DerivedKeyTokenType() {
    }

    public DerivedKeyTokenType(
           org.apache.axis.types.URI algorithm,
           org.apache.axis.types.UnsignedLong generation,
           org.apache.axis.types.Id id,
           java.lang.String label,
           org.apache.axis.types.UnsignedLong length,
           byte[] nonce,
           org.apache.axis.types.UnsignedLong offset,
           org.globus.ws.sc.PropertiesType properties,
           org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0_xsd.SecurityTokenReferenceType securityTokenReference) {
           this.securityTokenReference = securityTokenReference;
           this.properties = properties;
           this.generation = generation;
           this.offset = offset;
           this.length = length;
           this.label = label;
           this.nonce = nonce;
           this.id = id;
           this.algorithm = algorithm;
    }


    /**
     * Gets the securityTokenReference value for this DerivedKeyTokenType.
     * 
     * @return securityTokenReference
     */
    public org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0_xsd.SecurityTokenReferenceType getSecurityTokenReference() {
        return securityTokenReference;
    }


    /**
     * Sets the securityTokenReference value for this DerivedKeyTokenType.
     * 
     * @param securityTokenReference
     */
    public void setSecurityTokenReference(org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0_xsd.SecurityTokenReferenceType securityTokenReference) {
        this.securityTokenReference = securityTokenReference;
    }


    /**
     * Gets the properties value for this DerivedKeyTokenType.
     * 
     * @return properties
     */
    public org.globus.ws.sc.PropertiesType getProperties() {
        return properties;
    }


    /**
     * Sets the properties value for this DerivedKeyTokenType.
     * 
     * @param properties
     */
    public void setProperties(org.globus.ws.sc.PropertiesType properties) {
        this.properties = properties;
    }


    /**
     * Gets the generation value for this DerivedKeyTokenType.
     * 
     * @return generation
     */
    public org.apache.axis.types.UnsignedLong getGeneration() {
        return generation;
    }


    /**
     * Sets the generation value for this DerivedKeyTokenType.
     * 
     * @param generation
     */
    public void setGeneration(org.apache.axis.types.UnsignedLong generation) {
        this.generation = generation;
    }


    /**
     * Gets the offset value for this DerivedKeyTokenType.
     * 
     * @return offset
     */
    public org.apache.axis.types.UnsignedLong getOffset() {
        return offset;
    }


    /**
     * Sets the offset value for this DerivedKeyTokenType.
     * 
     * @param offset
     */
    public void setOffset(org.apache.axis.types.UnsignedLong offset) {
        this.offset = offset;
    }


    /**
     * Gets the length value for this DerivedKeyTokenType.
     * 
     * @return length
     */
    public org.apache.axis.types.UnsignedLong getLength() {
        return length;
    }


    /**
     * Sets the length value for this DerivedKeyTokenType.
     * 
     * @param length
     */
    public void setLength(org.apache.axis.types.UnsignedLong length) {
        this.length = length;
    }


    /**
     * Gets the label value for this DerivedKeyTokenType.
     * 
     * @return label
     */
    public java.lang.String getLabel() {
        return label;
    }


    /**
     * Sets the label value for this DerivedKeyTokenType.
     * 
     * @param label
     */
    public void setLabel(java.lang.String label) {
        this.label = label;
    }


    /**
     * Gets the nonce value for this DerivedKeyTokenType.
     * 
     * @return nonce
     */
    public byte[] getNonce() {
        return nonce;
    }


    /**
     * Sets the nonce value for this DerivedKeyTokenType.
     * 
     * @param nonce
     */
    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }


    /**
     * Gets the id value for this DerivedKeyTokenType.
     * 
     * @return id
     */
    public org.apache.axis.types.Id getId() {
        return id;
    }


    /**
     * Sets the id value for this DerivedKeyTokenType.
     * 
     * @param id
     */
    public void setId(org.apache.axis.types.Id id) {
        this.id = id;
    }


    /**
     * Gets the algorithm value for this DerivedKeyTokenType.
     * 
     * @return algorithm
     */
    public org.apache.axis.types.URI getAlgorithm() {
        return algorithm;
    }


    /**
     * Sets the algorithm value for this DerivedKeyTokenType.
     * 
     * @param algorithm
     */
    public void setAlgorithm(org.apache.axis.types.URI algorithm) {
        this.algorithm = algorithm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DerivedKeyTokenType)) return false;
        DerivedKeyTokenType other = (DerivedKeyTokenType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.securityTokenReference==null && other.getSecurityTokenReference()==null) || 
             (this.securityTokenReference!=null &&
              this.securityTokenReference.equals(other.getSecurityTokenReference()))) &&
            ((this.properties==null && other.getProperties()==null) || 
             (this.properties!=null &&
              this.properties.equals(other.getProperties()))) &&
            ((this.generation==null && other.getGeneration()==null) || 
             (this.generation!=null &&
              this.generation.equals(other.getGeneration()))) &&
            ((this.offset==null && other.getOffset()==null) || 
             (this.offset!=null &&
              this.offset.equals(other.getOffset()))) &&
            ((this.length==null && other.getLength()==null) || 
             (this.length!=null &&
              this.length.equals(other.getLength()))) &&
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel()))) &&
            ((this.nonce==null && other.getNonce()==null) || 
             (this.nonce!=null &&
              java.util.Arrays.equals(this.nonce, other.getNonce()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.algorithm==null && other.getAlgorithm()==null) || 
             (this.algorithm!=null &&
              this.algorithm.equals(other.getAlgorithm())));
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
        if (getSecurityTokenReference() != null) {
            _hashCode += getSecurityTokenReference().hashCode();
        }
        if (getProperties() != null) {
            _hashCode += getProperties().hashCode();
        }
        if (getGeneration() != null) {
            _hashCode += getGeneration().hashCode();
        }
        if (getOffset() != null) {
            _hashCode += getOffset().hashCode();
        }
        if (getLength() != null) {
            _hashCode += getLength().hashCode();
        }
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        if (getNonce() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNonce());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNonce(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getAlgorithm() != null) {
            _hashCode += getAlgorithm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DerivedKeyTokenType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "DerivedKeyTokenType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("id");
        attrField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ID"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("algorithm");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Algorithm"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityTokenReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SecurityTokenReferenceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properties");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "Properties"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "PropertiesType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("generation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "Generation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedLong"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offset");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "Offset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedLong"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("length");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "Length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedLong"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "Label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nonce");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/04/sc", "Nonce"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
