/**
 * GetMultipleResourceProperties_Element.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.oasis.wsrf.properties;

public class GetMultipleResourceProperties_Element  implements java.io.Serializable {
    private javax.xml.namespace.QName[] resourceProperty;

    public GetMultipleResourceProperties_Element() {
    }

    public GetMultipleResourceProperties_Element(
           javax.xml.namespace.QName[] resourceProperty) {
           this.resourceProperty = resourceProperty;
    }


    /**
     * Gets the resourceProperty value for this GetMultipleResourceProperties_Element.
     * 
     * @return resourceProperty
     */
    public javax.xml.namespace.QName[] getResourceProperty() {
        return resourceProperty;
    }


    /**
     * Sets the resourceProperty value for this GetMultipleResourceProperties_Element.
     * 
     * @param resourceProperty
     */
    public void setResourceProperty(javax.xml.namespace.QName[] resourceProperty) {
        this.resourceProperty = resourceProperty;
    }

    public javax.xml.namespace.QName getResourceProperty(int i) {
        return this.resourceProperty[i];
    }

    public void setResourceProperty(int i, javax.xml.namespace.QName _value) {
        this.resourceProperty[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetMultipleResourceProperties_Element)) return false;
        GetMultipleResourceProperties_Element other = (GetMultipleResourceProperties_Element) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resourceProperty==null && other.getResourceProperty()==null) || 
             (this.resourceProperty!=null &&
              java.util.Arrays.equals(this.resourceProperty, other.getResourceProperty())));
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
        if (getResourceProperty() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResourceProperty());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResourceProperty(), i);
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
        new org.apache.axis.description.TypeDesc(GetMultipleResourceProperties_Element.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", ">GetMultipleResourceProperties"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceProperty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceProperties-1.2-draft-01.xsd", "ResourceProperty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "QName"));
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
