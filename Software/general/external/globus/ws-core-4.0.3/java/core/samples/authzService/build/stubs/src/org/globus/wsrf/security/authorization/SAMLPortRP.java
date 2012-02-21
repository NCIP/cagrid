/**
 * SAMLPortRP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.security.authorization;

public class SAMLPortRP  implements java.io.Serializable {
    private org.apache.axis.types.URI[] supportedPolicies;
    private boolean supportsIndeterminate;
    private boolean signatureCapable;

    public SAMLPortRP() {
    }

    public SAMLPortRP(
           boolean signatureCapable,
           org.apache.axis.types.URI[] supportedPolicies,
           boolean supportsIndeterminate) {
           this.supportedPolicies = supportedPolicies;
           this.supportsIndeterminate = supportsIndeterminate;
           this.signatureCapable = signatureCapable;
    }


    /**
     * Gets the supportedPolicies value for this SAMLPortRP.
     * 
     * @return supportedPolicies
     */
    public org.apache.axis.types.URI[] getSupportedPolicies() {
        return supportedPolicies;
    }


    /**
     * Sets the supportedPolicies value for this SAMLPortRP.
     * 
     * @param supportedPolicies
     */
    public void setSupportedPolicies(org.apache.axis.types.URI[] supportedPolicies) {
        this.supportedPolicies = supportedPolicies;
    }

    public org.apache.axis.types.URI getSupportedPolicies(int i) {
        return this.supportedPolicies[i];
    }

    public void setSupportedPolicies(int i, org.apache.axis.types.URI _value) {
        this.supportedPolicies[i] = _value;
    }


    /**
     * Gets the supportsIndeterminate value for this SAMLPortRP.
     * 
     * @return supportsIndeterminate
     */
    public boolean isSupportsIndeterminate() {
        return supportsIndeterminate;
    }


    /**
     * Sets the supportsIndeterminate value for this SAMLPortRP.
     * 
     * @param supportsIndeterminate
     */
    public void setSupportsIndeterminate(boolean supportsIndeterminate) {
        this.supportsIndeterminate = supportsIndeterminate;
    }


    /**
     * Gets the signatureCapable value for this SAMLPortRP.
     * 
     * @return signatureCapable
     */
    public boolean isSignatureCapable() {
        return signatureCapable;
    }


    /**
     * Sets the signatureCapable value for this SAMLPortRP.
     * 
     * @param signatureCapable
     */
    public void setSignatureCapable(boolean signatureCapable) {
        this.signatureCapable = signatureCapable;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SAMLPortRP)) return false;
        SAMLPortRP other = (SAMLPortRP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.supportedPolicies==null && other.getSupportedPolicies()==null) || 
             (this.supportedPolicies!=null &&
              java.util.Arrays.equals(this.supportedPolicies, other.getSupportedPolicies()))) &&
            this.supportsIndeterminate == other.isSupportsIndeterminate() &&
            this.signatureCapable == other.isSignatureCapable();
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
        if (getSupportedPolicies() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSupportedPolicies());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSupportedPolicies(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isSupportsIndeterminate() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isSignatureCapable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SAMLPortRP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.gridforum.org/namespaces/2004/03/ogsa-authz/saml", ">SAMLPortRP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supportedPolicies");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gridforum.org/namespaces/2004/03/ogsa-authz/saml", "supportedPolicies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supportsIndeterminate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gridforum.org/namespaces/2004/03/ogsa-authz/saml", "supportsIndeterminate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureCapable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.gridforum.org/namespaces/2004/03/ogsa-authz/saml", "signatureCapable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
