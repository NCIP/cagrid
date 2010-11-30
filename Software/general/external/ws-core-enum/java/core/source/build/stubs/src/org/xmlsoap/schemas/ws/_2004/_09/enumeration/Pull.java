/**
 * Pull.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2004._09.enumeration;

public class Pull  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext;
    private org.apache.axis.types.Duration maxTime;
    private org.apache.axis.types.PositiveInteger maxElements;
    private org.apache.axis.types.PositiveInteger maxCharacters;
    private org.apache.axis.message.MessageElement [] _any;

    public Pull() {
    }

    public Pull(
           org.apache.axis.message.MessageElement [] _any,
           org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext,
           org.apache.axis.types.PositiveInteger maxCharacters,
           org.apache.axis.types.PositiveInteger maxElements,
           org.apache.axis.types.Duration maxTime) {
           this.enumerationContext = enumerationContext;
           this.maxTime = maxTime;
           this.maxElements = maxElements;
           this.maxCharacters = maxCharacters;
           this._any = _any;
    }


    /**
     * Gets the enumerationContext value for this Pull.
     * 
     * @return enumerationContext
     */
    public org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType getEnumerationContext() {
        return enumerationContext;
    }


    /**
     * Sets the enumerationContext value for this Pull.
     * 
     * @param enumerationContext
     */
    public void setEnumerationContext(org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext) {
        this.enumerationContext = enumerationContext;
    }


    /**
     * Gets the maxTime value for this Pull.
     * 
     * @return maxTime
     */
    public org.apache.axis.types.Duration getMaxTime() {
        return maxTime;
    }


    /**
     * Sets the maxTime value for this Pull.
     * 
     * @param maxTime
     */
    public void setMaxTime(org.apache.axis.types.Duration maxTime) {
        this.maxTime = maxTime;
    }


    /**
     * Gets the maxElements value for this Pull.
     * 
     * @return maxElements
     */
    public org.apache.axis.types.PositiveInteger getMaxElements() {
        return maxElements;
    }


    /**
     * Sets the maxElements value for this Pull.
     * 
     * @param maxElements
     */
    public void setMaxElements(org.apache.axis.types.PositiveInteger maxElements) {
        this.maxElements = maxElements;
    }


    /**
     * Gets the maxCharacters value for this Pull.
     * 
     * @return maxCharacters
     */
    public org.apache.axis.types.PositiveInteger getMaxCharacters() {
        return maxCharacters;
    }


    /**
     * Sets the maxCharacters value for this Pull.
     * 
     * @param maxCharacters
     */
    public void setMaxCharacters(org.apache.axis.types.PositiveInteger maxCharacters) {
        this.maxCharacters = maxCharacters;
    }


    /**
     * Gets the _any value for this Pull.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this Pull.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Pull)) return false;
        Pull other = (Pull) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.enumerationContext==null && other.getEnumerationContext()==null) || 
             (this.enumerationContext!=null &&
              this.enumerationContext.equals(other.getEnumerationContext()))) &&
            ((this.maxTime==null && other.getMaxTime()==null) || 
             (this.maxTime!=null &&
              this.maxTime.equals(other.getMaxTime()))) &&
            ((this.maxElements==null && other.getMaxElements()==null) || 
             (this.maxElements!=null &&
              this.maxElements.equals(other.getMaxElements()))) &&
            ((this.maxCharacters==null && other.getMaxCharacters()==null) || 
             (this.maxCharacters!=null &&
              this.maxCharacters.equals(other.getMaxCharacters()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any())));
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
        if (getEnumerationContext() != null) {
            _hashCode += getEnumerationContext().hashCode();
        }
        if (getMaxTime() != null) {
            _hashCode += getMaxTime().hashCode();
        }
        if (getMaxElements() != null) {
            _hashCode += getMaxElements().hashCode();
        }
        if (getMaxCharacters() != null) {
            _hashCode += getMaxCharacters().hashCode();
        }
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
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
        new org.apache.axis.description.TypeDesc(Pull.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", ">Pull"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enumerationContext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "EnumerationContext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "EnumerationContextType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "MaxTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "duration"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxElements");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "MaxElements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxCharacters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "MaxCharacters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
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
