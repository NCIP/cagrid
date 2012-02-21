/**
 * PullResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.xmlsoap.schemas.ws._2004._09.enumeration;

public class PullResponse  implements java.io.Serializable {
    private org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext;
    private org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType items;
    private java.lang.Object endOfSequence;

    public PullResponse() {
    }

    public PullResponse(
           java.lang.Object endOfSequence,
           org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext,
           org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType items) {
           this.enumerationContext = enumerationContext;
           this.items = items;
           this.endOfSequence = endOfSequence;
    }


    /**
     * Gets the enumerationContext value for this PullResponse.
     * 
     * @return enumerationContext
     */
    public org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType getEnumerationContext() {
        return enumerationContext;
    }


    /**
     * Sets the enumerationContext value for this PullResponse.
     * 
     * @param enumerationContext
     */
    public void setEnumerationContext(org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType enumerationContext) {
        this.enumerationContext = enumerationContext;
    }


    /**
     * Gets the items value for this PullResponse.
     * 
     * @return items
     */
    public org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType getItems() {
        return items;
    }


    /**
     * Sets the items value for this PullResponse.
     * 
     * @param items
     */
    public void setItems(org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType items) {
        this.items = items;
    }


    /**
     * Gets the endOfSequence value for this PullResponse.
     * 
     * @return endOfSequence
     */
    public java.lang.Object getEndOfSequence() {
        return endOfSequence;
    }


    /**
     * Sets the endOfSequence value for this PullResponse.
     * 
     * @param endOfSequence
     */
    public void setEndOfSequence(java.lang.Object endOfSequence) {
        this.endOfSequence = endOfSequence;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PullResponse)) return false;
        PullResponse other = (PullResponse) obj;
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
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              this.items.equals(other.getItems()))) &&
            ((this.endOfSequence==null && other.getEndOfSequence()==null) || 
             (this.endOfSequence!=null &&
              this.endOfSequence.equals(other.getEndOfSequence())));
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
        if (getItems() != null) {
            _hashCode += getItems().hashCode();
        }
        if (getEndOfSequence() != null) {
            _hashCode += getEndOfSequence().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PullResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", ">PullResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enumerationContext");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "EnumerationContext"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "EnumerationContextType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "Items"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "ItemListType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endOfSequence");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration", "EndOfSequence"));
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
