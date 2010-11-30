/**
 * ServiceGroupModificationNotificationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.servicegroup;

public class ServiceGroupModificationNotificationType  implements java.io.Serializable {
    private org.apache.axis.message.addressing.EndpointReferenceType serviceGroupEntryEPR;
    private org.apache.axis.message.addressing.EndpointReferenceType memberServiceEPR;
    private java.lang.Object content;

    public ServiceGroupModificationNotificationType() {
    }

    public ServiceGroupModificationNotificationType(
           java.lang.Object content,
           org.apache.axis.message.addressing.EndpointReferenceType memberServiceEPR,
           org.apache.axis.message.addressing.EndpointReferenceType serviceGroupEntryEPR) {
           this.serviceGroupEntryEPR = serviceGroupEntryEPR;
           this.memberServiceEPR = memberServiceEPR;
           this.content = content;
    }


    /**
     * Gets the serviceGroupEntryEPR value for this ServiceGroupModificationNotificationType.
     * 
     * @return serviceGroupEntryEPR
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getServiceGroupEntryEPR() {
        return serviceGroupEntryEPR;
    }


    /**
     * Sets the serviceGroupEntryEPR value for this ServiceGroupModificationNotificationType.
     * 
     * @param serviceGroupEntryEPR
     */
    public void setServiceGroupEntryEPR(org.apache.axis.message.addressing.EndpointReferenceType serviceGroupEntryEPR) {
        this.serviceGroupEntryEPR = serviceGroupEntryEPR;
    }


    /**
     * Gets the memberServiceEPR value for this ServiceGroupModificationNotificationType.
     * 
     * @return memberServiceEPR
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getMemberServiceEPR() {
        return memberServiceEPR;
    }


    /**
     * Sets the memberServiceEPR value for this ServiceGroupModificationNotificationType.
     * 
     * @param memberServiceEPR
     */
    public void setMemberServiceEPR(org.apache.axis.message.addressing.EndpointReferenceType memberServiceEPR) {
        this.memberServiceEPR = memberServiceEPR;
    }


    /**
     * Gets the content value for this ServiceGroupModificationNotificationType.
     * 
     * @return content
     */
    public java.lang.Object getContent() {
        return content;
    }


    /**
     * Sets the content value for this ServiceGroupModificationNotificationType.
     * 
     * @param content
     */
    public void setContent(java.lang.Object content) {
        this.content = content;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceGroupModificationNotificationType)) return false;
        ServiceGroupModificationNotificationType other = (ServiceGroupModificationNotificationType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceGroupEntryEPR==null && other.getServiceGroupEntryEPR()==null) || 
             (this.serviceGroupEntryEPR!=null &&
              this.serviceGroupEntryEPR.equals(other.getServiceGroupEntryEPR()))) &&
            ((this.memberServiceEPR==null && other.getMemberServiceEPR()==null) || 
             (this.memberServiceEPR!=null &&
              this.memberServiceEPR.equals(other.getMemberServiceEPR()))) &&
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
        if (getServiceGroupEntryEPR() != null) {
            _hashCode += getServiceGroupEntryEPR().hashCode();
        }
        if (getMemberServiceEPR() != null) {
            _hashCode += getMemberServiceEPR().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceGroupModificationNotificationType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "ServiceGroupModificationNotificationType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceGroupEntryEPR");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "ServiceGroupEntryEPR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memberServiceEPR");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "MemberServiceEPR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ServiceGroup-1.2-draft-01.xsd", "Content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
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
