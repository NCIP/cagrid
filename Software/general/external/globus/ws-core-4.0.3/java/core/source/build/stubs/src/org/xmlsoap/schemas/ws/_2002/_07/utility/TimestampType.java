/**
 * TimestampType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2002._07.utility;


/**
 * This complex type ties together the timestamp related elements
 * into a composite type.
 */
public class TimestampType  implements java.io.Serializable {
    private org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime created;
    private org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime expires;
    private org.xmlsoap.schemas.ws._2002._07.utility.ReceivedType received;
    private org.apache.axis.types.Id id;  // attribute

    public TimestampType() {
    }

    public TimestampType(
           org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime created,
           org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime expires,
           org.apache.axis.types.Id id,
           org.xmlsoap.schemas.ws._2002._07.utility.ReceivedType received) {
           this.created = created;
           this.expires = expires;
           this.received = received;
           this.id = id;
    }


    /**
     * Gets the created value for this TimestampType.
     * 
     * @return created
     */
    public org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime getCreated() {
        return created;
    }


    /**
     * Sets the created value for this TimestampType.
     * 
     * @param created
     */
    public void setCreated(org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime created) {
        this.created = created;
    }


    /**
     * Gets the expires value for this TimestampType.
     * 
     * @return expires
     */
    public org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime getExpires() {
        return expires;
    }


    /**
     * Sets the expires value for this TimestampType.
     * 
     * @param expires
     */
    public void setExpires(org.xmlsoap.schemas.ws._2002._07.utility.AttributedDateTime expires) {
        this.expires = expires;
    }


    /**
     * Gets the received value for this TimestampType.
     * 
     * @return received
     */
    public org.xmlsoap.schemas.ws._2002._07.utility.ReceivedType getReceived() {
        return received;
    }


    /**
     * Sets the received value for this TimestampType.
     * 
     * @param received
     */
    public void setReceived(org.xmlsoap.schemas.ws._2002._07.utility.ReceivedType received) {
        this.received = received;
    }


    /**
     * Gets the id value for this TimestampType.
     * 
     * @return id
     */
    public org.apache.axis.types.Id getId() {
        return id;
    }


    /**
     * Sets the id value for this TimestampType.
     * 
     * @param id
     */
    public void setId(org.apache.axis.types.Id id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TimestampType)) return false;
        TimestampType other = (TimestampType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            ((this.expires==null && other.getExpires()==null) || 
             (this.expires!=null &&
              this.expires.equals(other.getExpires()))) &&
            ((this.received==null && other.getReceived()==null) || 
             (this.received!=null &&
              this.received.equals(other.getReceived()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId())));
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
        if (getCreated() != null) {
            _hashCode += getCreated().hashCode();
        }
        if (getExpires() != null) {
            _hashCode += getExpires().hashCode();
        }
        if (getReceived() != null) {
            _hashCode += getReceived().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TimestampType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "TimestampType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("id");
        attrField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "Id"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ID"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "Created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "AttributedDateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expires");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "Expires"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "AttributedDateTime"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("received");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "Received"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "ReceivedType"));
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
