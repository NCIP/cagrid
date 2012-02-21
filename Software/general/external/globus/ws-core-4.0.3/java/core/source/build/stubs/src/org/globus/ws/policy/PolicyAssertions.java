/**
 * PolicyAssertions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;

public class PolicyAssertions  implements java.io.Serializable {
    private org.globus.ws.policy.TextEncodingAssertion textEncoding;
    private org.globus.ws.policy.LanguageAssertion language;
    private org.globus.ws.policy.SpecVersionAssertion specVersion;
    private org.globus.ws.policy.MessagePredicateAssertion messagePredicate;

    public PolicyAssertions() {
    }

    public PolicyAssertions(
           org.globus.ws.policy.LanguageAssertion language,
           org.globus.ws.policy.MessagePredicateAssertion messagePredicate,
           org.globus.ws.policy.SpecVersionAssertion specVersion,
           org.globus.ws.policy.TextEncodingAssertion textEncoding) {
           this.textEncoding = textEncoding;
           this.language = language;
           this.specVersion = specVersion;
           this.messagePredicate = messagePredicate;
    }


    /**
     * Gets the textEncoding value for this PolicyAssertions.
     * 
     * @return textEncoding
     */
    public org.globus.ws.policy.TextEncodingAssertion getTextEncoding() {
        return textEncoding;
    }


    /**
     * Sets the textEncoding value for this PolicyAssertions.
     * 
     * @param textEncoding
     */
    public void setTextEncoding(org.globus.ws.policy.TextEncodingAssertion textEncoding) {
        this.textEncoding = textEncoding;
    }


    /**
     * Gets the language value for this PolicyAssertions.
     * 
     * @return language
     */
    public org.globus.ws.policy.LanguageAssertion getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this PolicyAssertions.
     * 
     * @param language
     */
    public void setLanguage(org.globus.ws.policy.LanguageAssertion language) {
        this.language = language;
    }


    /**
     * Gets the specVersion value for this PolicyAssertions.
     * 
     * @return specVersion
     */
    public org.globus.ws.policy.SpecVersionAssertion getSpecVersion() {
        return specVersion;
    }


    /**
     * Sets the specVersion value for this PolicyAssertions.
     * 
     * @param specVersion
     */
    public void setSpecVersion(org.globus.ws.policy.SpecVersionAssertion specVersion) {
        this.specVersion = specVersion;
    }


    /**
     * Gets the messagePredicate value for this PolicyAssertions.
     * 
     * @return messagePredicate
     */
    public org.globus.ws.policy.MessagePredicateAssertion getMessagePredicate() {
        return messagePredicate;
    }


    /**
     * Sets the messagePredicate value for this PolicyAssertions.
     * 
     * @param messagePredicate
     */
    public void setMessagePredicate(org.globus.ws.policy.MessagePredicateAssertion messagePredicate) {
        this.messagePredicate = messagePredicate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PolicyAssertions)) return false;
        PolicyAssertions other = (PolicyAssertions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.textEncoding==null && other.getTextEncoding()==null) || 
             (this.textEncoding!=null &&
              this.textEncoding.equals(other.getTextEncoding()))) &&
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              this.language.equals(other.getLanguage()))) &&
            ((this.specVersion==null && other.getSpecVersion()==null) || 
             (this.specVersion!=null &&
              this.specVersion.equals(other.getSpecVersion()))) &&
            ((this.messagePredicate==null && other.getMessagePredicate()==null) || 
             (this.messagePredicate!=null &&
              this.messagePredicate.equals(other.getMessagePredicate())));
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
        if (getTextEncoding() != null) {
            _hashCode += getTextEncoding().hashCode();
        }
        if (getLanguage() != null) {
            _hashCode += getLanguage().hashCode();
        }
        if (getSpecVersion() != null) {
            _hashCode += getSpecVersion().hashCode();
        }
        if (getMessagePredicate() != null) {
            _hashCode += getMessagePredicate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PolicyAssertions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyAssertions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("textEncoding");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "TextEncoding"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "TextEncodingAssertion"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Language"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "LanguageAssertion"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "SpecVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "SpecVersionAssertion"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messagePredicate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "MessagePredicate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "MessagePredicateAssertion"));
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
