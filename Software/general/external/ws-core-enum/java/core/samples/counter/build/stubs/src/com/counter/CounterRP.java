/**
 * CounterRP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package com.counter;

public class CounterRP  implements java.io.Serializable {
    private int value;
    private org.oasis.wsn.TopicExpressionType[] topic;
    private org.apache.axis.types.URI[] topicExpressionDialects;
    private java.util.Calendar terminationTime;
    private java.util.Calendar currentTime;
    private boolean fixedTopicSet;

    public CounterRP() {
    }

    public CounterRP(
           java.util.Calendar currentTime,
           boolean fixedTopicSet,
           java.util.Calendar terminationTime,
           org.oasis.wsn.TopicExpressionType[] topic,
           org.apache.axis.types.URI[] topicExpressionDialects,
           int value) {
           this.value = value;
           this.topic = topic;
           this.topicExpressionDialects = topicExpressionDialects;
           this.terminationTime = terminationTime;
           this.currentTime = currentTime;
           this.fixedTopicSet = fixedTopicSet;
    }


    /**
     * Gets the value value for this CounterRP.
     * 
     * @return value
     */
    public int getValue() {
        return value;
    }


    /**
     * Sets the value value for this CounterRP.
     * 
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }


    /**
     * Gets the topic value for this CounterRP.
     * 
     * @return topic
     */
    public org.oasis.wsn.TopicExpressionType[] getTopic() {
        return topic;
    }


    /**
     * Sets the topic value for this CounterRP.
     * 
     * @param topic
     */
    public void setTopic(org.oasis.wsn.TopicExpressionType[] topic) {
        this.topic = topic;
    }

    public org.oasis.wsn.TopicExpressionType getTopic(int i) {
        return this.topic[i];
    }

    public void setTopic(int i, org.oasis.wsn.TopicExpressionType _value) {
        this.topic[i] = _value;
    }


    /**
     * Gets the topicExpressionDialects value for this CounterRP.
     * 
     * @return topicExpressionDialects
     */
    public org.apache.axis.types.URI[] getTopicExpressionDialects() {
        return topicExpressionDialects;
    }


    /**
     * Sets the topicExpressionDialects value for this CounterRP.
     * 
     * @param topicExpressionDialects
     */
    public void setTopicExpressionDialects(org.apache.axis.types.URI[] topicExpressionDialects) {
        this.topicExpressionDialects = topicExpressionDialects;
    }

    public org.apache.axis.types.URI getTopicExpressionDialects(int i) {
        return this.topicExpressionDialects[i];
    }

    public void setTopicExpressionDialects(int i, org.apache.axis.types.URI _value) {
        this.topicExpressionDialects[i] = _value;
    }


    /**
     * Gets the terminationTime value for this CounterRP.
     * 
     * @return terminationTime
     */
    public java.util.Calendar getTerminationTime() {
        return terminationTime;
    }


    /**
     * Sets the terminationTime value for this CounterRP.
     * 
     * @param terminationTime
     */
    public void setTerminationTime(java.util.Calendar terminationTime) {
        this.terminationTime = terminationTime;
    }


    /**
     * Gets the currentTime value for this CounterRP.
     * 
     * @return currentTime
     */
    public java.util.Calendar getCurrentTime() {
        return currentTime;
    }


    /**
     * Sets the currentTime value for this CounterRP.
     * 
     * @param currentTime
     */
    public void setCurrentTime(java.util.Calendar currentTime) {
        this.currentTime = currentTime;
    }


    /**
     * Gets the fixedTopicSet value for this CounterRP.
     * 
     * @return fixedTopicSet
     */
    public boolean isFixedTopicSet() {
        return fixedTopicSet;
    }


    /**
     * Sets the fixedTopicSet value for this CounterRP.
     * 
     * @param fixedTopicSet
     */
    public void setFixedTopicSet(boolean fixedTopicSet) {
        this.fixedTopicSet = fixedTopicSet;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CounterRP)) return false;
        CounterRP other = (CounterRP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.value == other.getValue() &&
            ((this.topic==null && other.getTopic()==null) || 
             (this.topic!=null &&
              java.util.Arrays.equals(this.topic, other.getTopic()))) &&
            ((this.topicExpressionDialects==null && other.getTopicExpressionDialects()==null) || 
             (this.topicExpressionDialects!=null &&
              java.util.Arrays.equals(this.topicExpressionDialects, other.getTopicExpressionDialects()))) &&
            ((this.terminationTime==null && other.getTerminationTime()==null) || 
             (this.terminationTime!=null &&
              this.terminationTime.equals(other.getTerminationTime()))) &&
            ((this.currentTime==null && other.getCurrentTime()==null) || 
             (this.currentTime!=null &&
              this.currentTime.equals(other.getCurrentTime()))) &&
            this.fixedTopicSet == other.isFixedTopicSet();
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
        _hashCode += getValue();
        if (getTopic() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTopic());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTopic(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTopicExpressionDialects() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTopicExpressionDialects());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTopicExpressionDialects(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTerminationTime() != null) {
            _hashCode += getTerminationTime().hashCode();
        }
        if (getCurrentTime() != null) {
            _hashCode += getCurrentTime().hashCode();
        }
        _hashCode += (isFixedTopicSet() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CounterRP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://counter.com", ">CounterRP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://counter.com", "Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topic");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "Topic"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicExpressionType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topicExpressionDialects");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "TopicExpressionDialects"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "CurrentTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fixedTopicSet");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsn/2004/06/wsn-WS-BaseNotification-1.2-draft-01.xsd", "FixedTopicSet"));
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
