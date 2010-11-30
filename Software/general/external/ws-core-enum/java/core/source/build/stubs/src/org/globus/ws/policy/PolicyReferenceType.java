/**
 * PolicyReferenceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;

public class PolicyReferenceType  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.apache.axis.message.MessageElement [] _any;
    private org.apache.axis.types.URI URI;  // attribute
    private javax.xml.namespace.QName ref;  // attribute
    private byte[] digest;  // attribute
    private javax.xml.namespace.QName digestAlgorithm;  // attribute

    public PolicyReferenceType() {
    }

    public PolicyReferenceType(
           org.apache.axis.types.URI URI,
           org.apache.axis.message.MessageElement [] _any,
           byte[] digest,
           javax.xml.namespace.QName digestAlgorithm,
           javax.xml.namespace.QName ref) {
           this._any = _any;
           this.URI = URI;
           this.ref = ref;
           this.digest = digest;
           this.digestAlgorithm = digestAlgorithm;
    }


    /**
     * Gets the _any value for this PolicyReferenceType.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this PolicyReferenceType.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the URI value for this PolicyReferenceType.
     * 
     * @return URI
     */
    public org.apache.axis.types.URI getURI() {
        return URI;
    }


    /**
     * Sets the URI value for this PolicyReferenceType.
     * 
     * @param URI
     */
    public void setURI(org.apache.axis.types.URI URI) {
        this.URI = URI;
    }


    /**
     * Gets the ref value for this PolicyReferenceType.
     * 
     * @return ref
     */
    public javax.xml.namespace.QName getRef() {
        return ref;
    }


    /**
     * Sets the ref value for this PolicyReferenceType.
     * 
     * @param ref
     */
    public void setRef(javax.xml.namespace.QName ref) {
        this.ref = ref;
    }


    /**
     * Gets the digest value for this PolicyReferenceType.
     * 
     * @return digest
     */
    public byte[] getDigest() {
        return digest;
    }


    /**
     * Sets the digest value for this PolicyReferenceType.
     * 
     * @param digest
     */
    public void setDigest(byte[] digest) {
        this.digest = digest;
    }


    /**
     * Gets the digestAlgorithm value for this PolicyReferenceType.
     * 
     * @return digestAlgorithm
     */
    public javax.xml.namespace.QName getDigestAlgorithm() {
        return digestAlgorithm;
    }


    /**
     * Sets the digestAlgorithm value for this PolicyReferenceType.
     * 
     * @param digestAlgorithm
     */
    public void setDigestAlgorithm(javax.xml.namespace.QName digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PolicyReferenceType)) return false;
        PolicyReferenceType other = (PolicyReferenceType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            ((this.URI==null && other.getURI()==null) || 
             (this.URI!=null &&
              this.URI.equals(other.getURI()))) &&
            ((this.ref==null && other.getRef()==null) || 
             (this.ref!=null &&
              this.ref.equals(other.getRef()))) &&
            ((this.digest==null && other.getDigest()==null) || 
             (this.digest!=null &&
              java.util.Arrays.equals(this.digest, other.getDigest()))) &&
            ((this.digestAlgorithm==null && other.getDigestAlgorithm()==null) || 
             (this.digestAlgorithm!=null &&
              this.digestAlgorithm.equals(other.getDigestAlgorithm())));
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
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getURI() != null) {
            _hashCode += getURI().hashCode();
        }
        if (getRef() != null) {
            _hashCode += getRef().hashCode();
        }
        if (getDigest() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDigest());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDigest(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDigestAlgorithm() != null) {
            _hashCode += getDigestAlgorithm().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PolicyReferenceType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyReferenceType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("URI");
        attrField.setXmlName(new javax.xml.namespace.QName("", "URI"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("ref");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Ref"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "QName"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("digest");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Digest"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("digestAlgorithm");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DigestAlgorithm"));
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
