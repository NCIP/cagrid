/**
 * ResourcePropertyValueChangeNotificationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.properties;

public class ResourcePropertyValueChangeNotificationType  implements java.io.Serializable {
    private org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeOldValue oldValue;
    private org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeNewValue newValue;

    public ResourcePropertyValueChangeNotificationType() {
    }

    public ResourcePropertyValueChangeNotificationType(
           org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeNewValue newValue,
           org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeOldValue oldValue) {
           this.oldValue = oldValue;
           this.newValue = newValue;
    }


    /**
     * Gets the oldValue value for this ResourcePropertyValueChangeNotificationType.
     * 
     * @return oldValue
     */
    public org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeOldValue getOldValue() {
        return oldValue;
    }


    /**
     * Sets the oldValue value for this ResourcePropertyValueChangeNotificationType.
     * 
     * @param oldValue
     */
    public void setOldValue(org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeOldValue oldValue) {
        this.oldValue = oldValue;
    }


    /**
     * Gets the newValue value for this ResourcePropertyValueChangeNotificationType.
     * 
     * @return newValue
     */
    public org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeNewValue getNewValue() {
        return newValue;
    }


    /**
     * Sets the newValue value for this ResourcePropertyValueChangeNotificationType.
     * 
     * @param newValue
     */
    public void setNewValue(org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationTypeNewValue newValue) {
        this.newValue = newValue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResourcePropertyValueChangeNotificationType)) return false;
        ResourcePropertyValueChangeNotificationType other = (ResourcePropertyValueChangeNotificationType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oldValue==null && other.getOldValue()==null) || 
             (this.oldValue!=null &&
              this.oldValue.equals(other.getOldValue()))) &&
            ((this.newValue==null && other.getNewValue()==null) || 
             (this.newValue!=null &&
              this.newValue.equals(other.getNewValue())));
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
        if (getOldValue() != null) {
            _hashCode += getOldValue().hashCode();
        }
        if (getNewValue() != null) {
            _hashCode += getNewValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResourcePropertyValueChangeNotificationType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourcePropertyValueChangeNotificationType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "OldValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">ResourcePropertyValueChangeNotificationType>OldValue"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "NewValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">ResourcePropertyValueChangeNotificationType>NewValue"));
        elemField.setNillable(true);
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
