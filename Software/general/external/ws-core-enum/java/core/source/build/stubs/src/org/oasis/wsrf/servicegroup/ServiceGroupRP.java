/**
 * ServiceGroupRP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.servicegroup;

public class ServiceGroupRP  implements java.io.Serializable {
    private org.oasis.wsrf.servicegroup.MembershipContentRule[] membershipContentRule;
    private org.oasis.wsrf.servicegroup.EntryType[] entry;

    public ServiceGroupRP() {
    }

    public ServiceGroupRP(
           org.oasis.wsrf.servicegroup.EntryType[] entry,
           org.oasis.wsrf.servicegroup.MembershipContentRule[] membershipContentRule) {
           this.membershipContentRule = membershipContentRule;
           this.entry = entry;
    }


    /**
     * Gets the membershipContentRule value for this ServiceGroupRP.
     * 
     * @return membershipContentRule
     */
    public org.oasis.wsrf.servicegroup.MembershipContentRule[] getMembershipContentRule() {
        return membershipContentRule;
    }


    /**
     * Sets the membershipContentRule value for this ServiceGroupRP.
     * 
     * @param membershipContentRule
     */
    public void setMembershipContentRule(org.oasis.wsrf.servicegroup.MembershipContentRule[] membershipContentRule) {
        this.membershipContentRule = membershipContentRule;
    }

    public org.oasis.wsrf.servicegroup.MembershipContentRule getMembershipContentRule(int i) {
        return this.membershipContentRule[i];
    }

    public void setMembershipContentRule(int i, org.oasis.wsrf.servicegroup.MembershipContentRule _value) {
        this.membershipContentRule[i] = _value;
    }


    /**
     * Gets the entry value for this ServiceGroupRP.
     * 
     * @return entry
     */
    public org.oasis.wsrf.servicegroup.EntryType[] getEntry() {
        return entry;
    }


    /**
     * Sets the entry value for this ServiceGroupRP.
     * 
     * @param entry
     */
    public void setEntry(org.oasis.wsrf.servicegroup.EntryType[] entry) {
        this.entry = entry;
    }

    public org.oasis.wsrf.servicegroup.EntryType getEntry(int i) {
        return this.entry[i];
    }

    public void setEntry(int i, org.oasis.wsrf.servicegroup.EntryType _value) {
        this.entry[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceGroupRP)) return false;
        ServiceGroupRP other = (ServiceGroupRP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.membershipContentRule==null && other.getMembershipContentRule()==null) || 
             (this.membershipContentRule!=null &&
              java.util.Arrays.equals(this.membershipContentRule, other.getMembershipContentRule()))) &&
            ((this.entry==null && other.getEntry()==null) || 
             (this.entry!=null &&
              java.util.Arrays.equals(this.entry, other.getEntry())));
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
        if (getMembershipContentRule() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMembershipContentRule());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMembershipContentRule(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEntry() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEntry());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEntry(), i);
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
        new org.apache.axis.description.TypeDesc(ServiceGroupRP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", ">ServiceGroupRP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("membershipContentRule");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "MembershipContentRule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", ">MembershipContentRule"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "Entry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "EntryType"));
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
