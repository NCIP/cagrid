/**
 * PolicyAttachmentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;

public class PolicyAttachmentType  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.globus.ws.policy.AppliesTo appliesTo;
    private org.apache.axis.message.MessageElement [] _any;
    private org.globus.ws.policy.PolicyExpression policy;
    private org.globus.ws.policy.PolicyReferenceType policyReference;
    private org.apache.axis.message.MessageElement [] _any2;

    public PolicyAttachmentType() {
    }

    public PolicyAttachmentType(
           org.apache.axis.message.MessageElement [] _any,
           org.apache.axis.message.MessageElement [] _any2,
           org.globus.ws.policy.AppliesTo appliesTo,
           org.globus.ws.policy.PolicyExpression policy,
           org.globus.ws.policy.PolicyReferenceType policyReference) {
           this.appliesTo = appliesTo;
           this._any = _any;
           this.policy = policy;
           this.policyReference = policyReference;
           this._any2 = _any2;
    }


    /**
     * Gets the appliesTo value for this PolicyAttachmentType.
     * 
     * @return appliesTo
     */
    public org.globus.ws.policy.AppliesTo getAppliesTo() {
        return appliesTo;
    }


    /**
     * Sets the appliesTo value for this PolicyAttachmentType.
     * 
     * @param appliesTo
     */
    public void setAppliesTo(org.globus.ws.policy.AppliesTo appliesTo) {
        this.appliesTo = appliesTo;
    }


    /**
     * Gets the _any value for this PolicyAttachmentType.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this PolicyAttachmentType.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }


    /**
     * Gets the policy value for this PolicyAttachmentType.
     * 
     * @return policy
     */
    public org.globus.ws.policy.PolicyExpression getPolicy() {
        return policy;
    }


    /**
     * Sets the policy value for this PolicyAttachmentType.
     * 
     * @param policy
     */
    public void setPolicy(org.globus.ws.policy.PolicyExpression policy) {
        this.policy = policy;
    }


    /**
     * Gets the policyReference value for this PolicyAttachmentType.
     * 
     * @return policyReference
     */
    public org.globus.ws.policy.PolicyReferenceType getPolicyReference() {
        return policyReference;
    }


    /**
     * Sets the policyReference value for this PolicyAttachmentType.
     * 
     * @param policyReference
     */
    public void setPolicyReference(org.globus.ws.policy.PolicyReferenceType policyReference) {
        this.policyReference = policyReference;
    }


    /**
     * Gets the _any2 value for this PolicyAttachmentType.
     * 
     * @return _any2
     */
    public org.apache.axis.message.MessageElement [] get_any2() {
        return _any2;
    }


    /**
     * Sets the _any2 value for this PolicyAttachmentType.
     * 
     * @param _any2
     */
    public void set_any2(org.apache.axis.message.MessageElement [] _any2) {
        this._any2 = _any2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PolicyAttachmentType)) return false;
        PolicyAttachmentType other = (PolicyAttachmentType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.appliesTo==null && other.getAppliesTo()==null) || 
             (this.appliesTo!=null &&
              this.appliesTo.equals(other.getAppliesTo()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any()))) &&
            ((this.policy==null && other.getPolicy()==null) || 
             (this.policy!=null &&
              this.policy.equals(other.getPolicy()))) &&
            ((this.policyReference==null && other.getPolicyReference()==null) || 
             (this.policyReference!=null &&
              this.policyReference.equals(other.getPolicyReference()))) &&
            ((this._any2==null && other.get_any2()==null) || 
             (this._any2!=null &&
              java.util.Arrays.equals(this._any2, other.get_any2())));
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
        if (getAppliesTo() != null) {
            _hashCode += getAppliesTo().hashCode();
        }
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
        if (getPolicy() != null) {
            _hashCode += getPolicy().hashCode();
        }
        if (getPolicyReference() != null) {
            _hashCode += getPolicyReference().hashCode();
        }
        if (get_any2() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any2());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any2(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PolicyAttachmentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyAttachmentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appliesTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "AppliesTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "AppliesTo"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Policy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyExpression"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyReferenceType"));
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
