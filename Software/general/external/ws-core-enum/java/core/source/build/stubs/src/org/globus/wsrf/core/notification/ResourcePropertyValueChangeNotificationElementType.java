/**
 * ResourcePropertyValueChangeNotificationElementType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.core.notification;

public class ResourcePropertyValueChangeNotificationElementType  implements java.io.Serializable {
    private org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType resourcePropertyValueChangeNotification;

    public ResourcePropertyValueChangeNotificationElementType() {
    }

    public ResourcePropertyValueChangeNotificationElementType(
           org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType resourcePropertyValueChangeNotification) {
           this.resourcePropertyValueChangeNotification = resourcePropertyValueChangeNotification;
    }


    /**
     * Gets the resourcePropertyValueChangeNotification value for this ResourcePropertyValueChangeNotificationElementType.
     * 
     * @return resourcePropertyValueChangeNotification
     */
    public org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType getResourcePropertyValueChangeNotification() {
        return resourcePropertyValueChangeNotification;
    }


    /**
     * Sets the resourcePropertyValueChangeNotification value for this ResourcePropertyValueChangeNotificationElementType.
     * 
     * @param resourcePropertyValueChangeNotification
     */
    public void setResourcePropertyValueChangeNotification(org.oasis.wsrf.properties.ResourcePropertyValueChangeNotificationType resourcePropertyValueChangeNotification) {
        this.resourcePropertyValueChangeNotification = resourcePropertyValueChangeNotification;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResourcePropertyValueChangeNotificationElementType)) return false;
        ResourcePropertyValueChangeNotificationElementType other = (ResourcePropertyValueChangeNotificationElementType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resourcePropertyValueChangeNotification==null && other.getResourcePropertyValueChangeNotification()==null) || 
             (this.resourcePropertyValueChangeNotification!=null &&
              this.resourcePropertyValueChangeNotification.equals(other.getResourcePropertyValueChangeNotification())));
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
        if (getResourcePropertyValueChangeNotification() != null) {
            _hashCode += getResourcePropertyValueChangeNotification().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResourcePropertyValueChangeNotificationElementType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://wsrf.globus.org/core/notification", "ResourcePropertyValueChangeNotificationElementType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourcePropertyValueChangeNotification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourcePropertyValueChangeNotification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourcePropertyValueChangeNotificationType"));
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
