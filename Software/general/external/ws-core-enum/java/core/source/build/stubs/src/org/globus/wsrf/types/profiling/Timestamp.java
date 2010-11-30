/**
 * Timestamp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.types.profiling;

public class Timestamp  implements java.io.Serializable {
    private long startTime;
    private long availableMemory;
    private java.lang.String threadID;
    private java.lang.String messageContextHash;
    private java.lang.String serviceURL;
    private java.lang.String operation;
    private int originType;
    private int messageType;
    private long endTime;

    public Timestamp() {
    }

    public Timestamp(
           long availableMemory,
           long endTime,
           java.lang.String messageContextHash,
           int messageType,
           java.lang.String operation,
           int originType,
           java.lang.String serviceURL,
           long startTime,
           java.lang.String threadID) {
           this.startTime = startTime;
           this.availableMemory = availableMemory;
           this.threadID = threadID;
           this.messageContextHash = messageContextHash;
           this.serviceURL = serviceURL;
           this.operation = operation;
           this.originType = originType;
           this.messageType = messageType;
           this.endTime = endTime;
    }


    /**
     * Gets the startTime value for this Timestamp.
     * 
     * @return startTime
     */
    public long getStartTime() {
        return startTime;
    }


    /**
     * Sets the startTime value for this Timestamp.
     * 
     * @param startTime
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    /**
     * Gets the availableMemory value for this Timestamp.
     * 
     * @return availableMemory
     */
    public long getAvailableMemory() {
        return availableMemory;
    }


    /**
     * Sets the availableMemory value for this Timestamp.
     * 
     * @param availableMemory
     */
    public void setAvailableMemory(long availableMemory) {
        this.availableMemory = availableMemory;
    }


    /**
     * Gets the threadID value for this Timestamp.
     * 
     * @return threadID
     */
    public java.lang.String getThreadID() {
        return threadID;
    }


    /**
     * Sets the threadID value for this Timestamp.
     * 
     * @param threadID
     */
    public void setThreadID(java.lang.String threadID) {
        this.threadID = threadID;
    }


    /**
     * Gets the messageContextHash value for this Timestamp.
     * 
     * @return messageContextHash
     */
    public java.lang.String getMessageContextHash() {
        return messageContextHash;
    }


    /**
     * Sets the messageContextHash value for this Timestamp.
     * 
     * @param messageContextHash
     */
    public void setMessageContextHash(java.lang.String messageContextHash) {
        this.messageContextHash = messageContextHash;
    }


    /**
     * Gets the serviceURL value for this Timestamp.
     * 
     * @return serviceURL
     */
    public java.lang.String getServiceURL() {
        return serviceURL;
    }


    /**
     * Sets the serviceURL value for this Timestamp.
     * 
     * @param serviceURL
     */
    public void setServiceURL(java.lang.String serviceURL) {
        this.serviceURL = serviceURL;
    }


    /**
     * Gets the operation value for this Timestamp.
     * 
     * @return operation
     */
    public java.lang.String getOperation() {
        return operation;
    }


    /**
     * Sets the operation value for this Timestamp.
     * 
     * @param operation
     */
    public void setOperation(java.lang.String operation) {
        this.operation = operation;
    }


    /**
     * Gets the originType value for this Timestamp.
     * 
     * @return originType
     */
    public int getOriginType() {
        return originType;
    }


    /**
     * Sets the originType value for this Timestamp.
     * 
     * @param originType
     */
    public void setOriginType(int originType) {
        this.originType = originType;
    }


    /**
     * Gets the messageType value for this Timestamp.
     * 
     * @return messageType
     */
    public int getMessageType() {
        return messageType;
    }


    /**
     * Sets the messageType value for this Timestamp.
     * 
     * @param messageType
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }


    /**
     * Gets the endTime value for this Timestamp.
     * 
     * @return endTime
     */
    public long getEndTime() {
        return endTime;
    }


    /**
     * Sets the endTime value for this Timestamp.
     * 
     * @param endTime
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Timestamp)) return false;
        Timestamp other = (Timestamp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.startTime == other.getStartTime() &&
            this.availableMemory == other.getAvailableMemory() &&
            ((this.threadID==null && other.getThreadID()==null) || 
             (this.threadID!=null &&
              this.threadID.equals(other.getThreadID()))) &&
            ((this.messageContextHash==null && other.getMessageContextHash()==null) || 
             (this.messageContextHash!=null &&
              this.messageContextHash.equals(other.getMessageContextHash()))) &&
            ((this.serviceURL==null && other.getServiceURL()==null) || 
             (this.serviceURL!=null &&
              this.serviceURL.equals(other.getServiceURL()))) &&
            ((this.operation==null && other.getOperation()==null) || 
             (this.operation!=null &&
              this.operation.equals(other.getOperation()))) &&
            this.originType == other.getOriginType() &&
            this.messageType == other.getMessageType() &&
            this.endTime == other.getEndTime();
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
        _hashCode += new Long(getStartTime()).hashCode();
        _hashCode += new Long(getAvailableMemory()).hashCode();
        if (getThreadID() != null) {
            _hashCode += getThreadID().hashCode();
        }
        if (getMessageContextHash() != null) {
            _hashCode += getMessageContextHash().hashCode();
        }
        if (getServiceURL() != null) {
            _hashCode += getServiceURL().hashCode();
        }
        if (getOperation() != null) {
            _hashCode += getOperation().hashCode();
        }
        _hashCode += getOriginType();
        _hashCode += getMessageType();
        _hashCode += new Long(getEndTime()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Timestamp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "Timestamp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "startTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableMemory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "availableMemory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("threadID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "threadID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageContextHash");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "messageContextHash"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceURL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "serviceURL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "operation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "originType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "messageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://wsrf.globus.org/types/profiling", "endTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
