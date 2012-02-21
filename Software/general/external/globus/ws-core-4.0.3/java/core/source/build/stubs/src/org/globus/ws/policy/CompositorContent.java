/**
 * CompositorContent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;

public class CompositorContent  implements java.io.Serializable {
    private org.globus.ws.policy.Compositor oneOrMore;
    private org.globus.ws.policy.Compositor all;
    private org.globus.ws.policy.Compositor exactlyOne;
    private org.globus.ws.policy.PolicyReferenceType policyReference;
    private org.globus.ws.policy.PolicyAssertions policyAssertions;

    public CompositorContent() {
    }

    public CompositorContent(
           org.globus.ws.policy.Compositor all,
           org.globus.ws.policy.Compositor exactlyOne,
           org.globus.ws.policy.Compositor oneOrMore,
           org.globus.ws.policy.PolicyAssertions policyAssertions,
           org.globus.ws.policy.PolicyReferenceType policyReference) {
           this.oneOrMore = oneOrMore;
           this.all = all;
           this.exactlyOne = exactlyOne;
           this.policyReference = policyReference;
           this.policyAssertions = policyAssertions;
    }


    /**
     * Gets the oneOrMore value for this CompositorContent.
     * 
     * @return oneOrMore
     */
    public org.globus.ws.policy.Compositor getOneOrMore() {
        return oneOrMore;
    }


    /**
     * Sets the oneOrMore value for this CompositorContent.
     * 
     * @param oneOrMore
     */
    public void setOneOrMore(org.globus.ws.policy.Compositor oneOrMore) {
        this.oneOrMore = oneOrMore;
    }


    /**
     * Gets the all value for this CompositorContent.
     * 
     * @return all
     */
    public org.globus.ws.policy.Compositor getAll() {
        return all;
    }


    /**
     * Sets the all value for this CompositorContent.
     * 
     * @param all
     */
    public void setAll(org.globus.ws.policy.Compositor all) {
        this.all = all;
    }


    /**
     * Gets the exactlyOne value for this CompositorContent.
     * 
     * @return exactlyOne
     */
    public org.globus.ws.policy.Compositor getExactlyOne() {
        return exactlyOne;
    }


    /**
     * Sets the exactlyOne value for this CompositorContent.
     * 
     * @param exactlyOne
     */
    public void setExactlyOne(org.globus.ws.policy.Compositor exactlyOne) {
        this.exactlyOne = exactlyOne;
    }


    /**
     * Gets the policyReference value for this CompositorContent.
     * 
     * @return policyReference
     */
    public org.globus.ws.policy.PolicyReferenceType getPolicyReference() {
        return policyReference;
    }


    /**
     * Sets the policyReference value for this CompositorContent.
     * 
     * @param policyReference
     */
    public void setPolicyReference(org.globus.ws.policy.PolicyReferenceType policyReference) {
        this.policyReference = policyReference;
    }


    /**
     * Gets the policyAssertions value for this CompositorContent.
     * 
     * @return policyAssertions
     */
    public org.globus.ws.policy.PolicyAssertions getPolicyAssertions() {
        return policyAssertions;
    }


    /**
     * Sets the policyAssertions value for this CompositorContent.
     * 
     * @param policyAssertions
     */
    public void setPolicyAssertions(org.globus.ws.policy.PolicyAssertions policyAssertions) {
        this.policyAssertions = policyAssertions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompositorContent)) return false;
        CompositorContent other = (CompositorContent) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oneOrMore==null && other.getOneOrMore()==null) || 
             (this.oneOrMore!=null &&
              this.oneOrMore.equals(other.getOneOrMore()))) &&
            ((this.all==null && other.getAll()==null) || 
             (this.all!=null &&
              this.all.equals(other.getAll()))) &&
            ((this.exactlyOne==null && other.getExactlyOne()==null) || 
             (this.exactlyOne!=null &&
              this.exactlyOne.equals(other.getExactlyOne()))) &&
            ((this.policyReference==null && other.getPolicyReference()==null) || 
             (this.policyReference!=null &&
              this.policyReference.equals(other.getPolicyReference()))) &&
            ((this.policyAssertions==null && other.getPolicyAssertions()==null) || 
             (this.policyAssertions!=null &&
              this.policyAssertions.equals(other.getPolicyAssertions())));
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
        if (getOneOrMore() != null) {
            _hashCode += getOneOrMore().hashCode();
        }
        if (getAll() != null) {
            _hashCode += getAll().hashCode();
        }
        if (getExactlyOne() != null) {
            _hashCode += getExactlyOne().hashCode();
        }
        if (getPolicyReference() != null) {
            _hashCode += getPolicyReference().hashCode();
        }
        if (getPolicyAssertions() != null) {
            _hashCode += getPolicyAssertions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompositorContent.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "CompositorContent"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oneOrMore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "OneOrMore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Compositor"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("all");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "All"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Compositor"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exactlyOne");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "ExactlyOne"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Compositor"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyReferenceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyAssertions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyAssertions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyAssertions"));
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
