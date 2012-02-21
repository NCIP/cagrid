/**
 * TransformsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.w3.www._2000._09.xmldsig;

public class TransformsType  implements java.io.Serializable {
    private org.w3.www._2000._09.xmldsig.TransformType[] transform;

    public TransformsType() {
    }

    public TransformsType(
           org.w3.www._2000._09.xmldsig.TransformType[] transform) {
           this.transform = transform;
    }


    /**
     * Gets the transform value for this TransformsType.
     * 
     * @return transform
     */
    public org.w3.www._2000._09.xmldsig.TransformType[] getTransform() {
        return transform;
    }


    /**
     * Sets the transform value for this TransformsType.
     * 
     * @param transform
     */
    public void setTransform(org.w3.www._2000._09.xmldsig.TransformType[] transform) {
        this.transform = transform;
    }

    public org.w3.www._2000._09.xmldsig.TransformType getTransform(int i) {
        return this.transform[i];
    }

    public void setTransform(int i, org.w3.www._2000._09.xmldsig.TransformType _value) {
        this.transform[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransformsType)) return false;
        TransformsType other = (TransformsType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transform==null && other.getTransform()==null) || 
             (this.transform!=null &&
              java.util.Arrays.equals(this.transform, other.getTransform())));
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
        if (getTransform() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransform());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransform(), i);
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
        new org.apache.axis.description.TypeDesc(TransformsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "TransformsType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transform");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "Transform"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#", "TransformType"));
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
