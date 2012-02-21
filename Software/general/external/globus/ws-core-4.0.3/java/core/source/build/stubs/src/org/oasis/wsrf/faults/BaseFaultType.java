/**
 * BaseFaultType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.faults;

public class BaseFaultType  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private java.util.Calendar timestamp;
    private org.apache.axis.message.addressing.EndpointReferenceType originator;
    private org.oasis.wsrf.faults.BaseFaultTypeErrorCode errorCode;
    private org.oasis.wsrf.faults.BaseFaultTypeDescription[] description;
    private org.oasis.wsrf.faults.BaseFaultType[] faultCause;

    public BaseFaultType() {
    }

    public BaseFaultType(
           java.util.Calendar timestamp,
           org.apache.axis.message.addressing.EndpointReferenceType originator,
           org.oasis.wsrf.faults.BaseFaultTypeErrorCode errorCode,
           org.oasis.wsrf.faults.BaseFaultTypeDescription[] description,
           org.oasis.wsrf.faults.BaseFaultType[] faultCause) {
        this.timestamp = timestamp;
        this.originator = originator;
        this.errorCode = errorCode;
        this.description = description;
        this.faultCause = faultCause;
    }


    /**
     * Gets the timestamp value for this BaseFaultType.
     * 
     * @return timestamp
     */
    public java.util.Calendar getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp value for this BaseFaultType.
     * 
     * @param timestamp
     */
    public void setTimestamp(java.util.Calendar timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Gets the originator value for this BaseFaultType.
     * 
     * @return originator
     */
    public org.apache.axis.message.addressing.EndpointReferenceType getOriginator() {
        return originator;
    }


    /**
     * Sets the originator value for this BaseFaultType.
     * 
     * @param originator
     */
    public void setOriginator(org.apache.axis.message.addressing.EndpointReferenceType originator) {
        this.originator = originator;
    }


    /**
     * Gets the errorCode value for this BaseFaultType.
     * 
     * @return errorCode
     */
    public org.oasis.wsrf.faults.BaseFaultTypeErrorCode getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this BaseFaultType.
     * 
     * @param errorCode
     */
    public void setErrorCode(org.oasis.wsrf.faults.BaseFaultTypeErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the description value for this BaseFaultType.
     * 
     * @return description
     */
    public org.oasis.wsrf.faults.BaseFaultTypeDescription[] getDescription() {
        return description;
    }


    /**
     * Sets the description value for this BaseFaultType.
     * 
     * @param description
     */
    public void setDescription(org.oasis.wsrf.faults.BaseFaultTypeDescription[] description) {
        this.description = description;
    }

    public org.oasis.wsrf.faults.BaseFaultTypeDescription getDescription(int i) {
        return this.description[i];
    }

    public void setDescription(int i, org.oasis.wsrf.faults.BaseFaultTypeDescription _value) {
        this.description[i] = _value;
    }


    /**
     * Gets the faultCause value for this BaseFaultType.
     * 
     * @return faultCause
     */
    public org.oasis.wsrf.faults.BaseFaultType[] getFaultCause() {
        return faultCause;
    }


    /**
     * Sets the faultCause value for this BaseFaultType.
     * 
     * @param faultCause
     */
    public void setFaultCause(org.oasis.wsrf.faults.BaseFaultType[] faultCause) {
        this.faultCause = faultCause;
    }

    public org.oasis.wsrf.faults.BaseFaultType getFaultCause(int i) {
        return this.faultCause[i];
    }

    public void setFaultCause(int i, org.oasis.wsrf.faults.BaseFaultType _value) {
        this.faultCause[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseFaultType)) return false;
        BaseFaultType other = (BaseFaultType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.timestamp==null && other.getTimestamp()==null) || 
             (this.timestamp!=null &&
              this.timestamp.equals(other.getTimestamp()))) &&
            ((this.originator==null && other.getOriginator()==null) || 
             (this.originator!=null &&
              this.originator.equals(other.getOriginator()))) &&
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              java.util.Arrays.equals(this.description, other.getDescription()))) &&
            ((this.faultCause==null && other.getFaultCause()==null) || 
             (this.faultCause!=null &&
              java.util.Arrays.equals(this.faultCause, other.getFaultCause())));
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
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        if (getOriginator() != null) {
            _hashCode += getOriginator().hashCode();
        }
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getDescription() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDescription());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDescription(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFaultCause() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFaultCause());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFaultCause(), i);
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
        new org.apache.axis.description.TypeDesc(BaseFaultType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "BaseFaultType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "Timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "Originator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/03/addressing", "EndpointReferenceType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "ErrorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", ">BaseFaultType>ErrorCode"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", ">BaseFaultType>Description"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultCause");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "FaultCause"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-BaseFaults-1.2-draft-01.xsd", "BaseFaultType"));
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
