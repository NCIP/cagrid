/**
 * ServiceGroupEntryRP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.servicegroup;

public class ServiceGroupEntryRP  implements java.io.Serializable {
    private org.apache.axis.message.addressing.EndpointReferenceType serviceGroupEPR;
    private org.apache.axis.message.addressing.EndpointReferenceType memberEPR;
    private java.lang.Object content;

    public ServiceGroupEntryRP() {
    }

    public ServiceGroupEntryRP(
           java.lang.Object content,
           org.apache.axis.message.addressing.EndpointReferenceType memberEPR,
           org.apache.axis.message.addressing.EndpointReferenceType serviceGroupEPR) {
           this.serviceGroupEPR = serviceGroupEPR;
           this.memberEPR = memberEPR;
           this.content = content;
    }


    /**
     * Gets the serviceGroupEPR value for this ServiceGroupEntryRP.
     * 
     * @return serviceGroupEPR
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getServiceGroupEPR() {
        return serviceGroupEPR;
    }


    /**
     * Sets the serviceGroupEPR value for this ServiceGroupEntryRP.
     * 
     * @param serviceGroupEPR
     */
    public void setServiceGroupEPR(org.apache.axis.message.addressing.EndpointReferenceType serviceGroupEPR) {
        this.serviceGroupEPR = serviceGroupEPR;
    }


    /**
     * Gets the memberEPR value for this ServiceGroupEntryRP.
     * 
     * @return memberEPR
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getMemberEPR() {
        return memberEPR;
    }


    /**
     * Sets the memberEPR value for this ServiceGroupEntryRP.
     * 
     * @param memberEPR
     */
    public void setMemberEPR(org.apache.axis.message.addressing.EndpointReferenceType memberEPR) {
        this.memberEPR = memberEPR;
    }


    /**
     * Gets the content value for this ServiceGroupEntryRP.
     * 
     * @return content
     */
    public java.lang.Object getContent() {
        return content;
    }


    /**
     * Sets the content value for this ServiceGroupEntryRP.
     * 
     * @param content
     */
    public void setContent(java.lang.Object content) {
        this.content = content;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceGroupEntryRP)) return false;
        ServiceGroupEntryRP other = (ServiceGroupEntryRP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceGroupEPR==null && other.getServiceGroupEPR()==null) || 
             (this.serviceGroupEPR!=null &&
              this.serviceGroupEPR.equals(other.getServiceGroupEPR()))) &&
            ((this.memberEPR==null && other.getMemberEPR()==null) || 
             (this.memberEPR!=null &&
              this.memberEPR.equals(other.getMemberEPR()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent())));
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
        if (getServiceGroupEPR() != null) {
            _hashCode += getServiceGroupEPR().hashCode();
        }
        if (getMemberEPR() != null) {
            _hashCode += getMemberEPR().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceGroupEntryRP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", ">ServiceGroupEntryRP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceGroupEPR");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "ServiceGroupEPR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memberEPR");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "MemberEPR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "Content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
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
