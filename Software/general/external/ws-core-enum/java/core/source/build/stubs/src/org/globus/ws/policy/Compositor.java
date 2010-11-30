/**
 * Compositor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;

public class Compositor  implements java.io.Serializable {
    private org.globus.ws.policy.CompositorContent[] compositorContent;
    private org.globus.ws.policy.OpenUsageType usage;  // attribute
    private java.lang.Integer preference;  // attribute
    private org.apache.axis.types.Id id;  // attribute

    public Compositor() {
    }

    public Compositor(
           org.globus.ws.policy.CompositorContent[] compositorContent,
           org.apache.axis.types.Id id,
           java.lang.Integer preference,
           org.globus.ws.policy.OpenUsageType usage) {
           this.compositorContent = compositorContent;
           this.usage = usage;
           this.preference = preference;
           this.id = id;
    }


    /**
     * Gets the compositorContent value for this Compositor.
     * 
     * @return compositorContent
     */
    public org.globus.ws.policy.CompositorContent[] getCompositorContent() {
        return compositorContent;
    }


    /**
     * Sets the compositorContent value for this Compositor.
     * 
     * @param compositorContent
     */
    public void setCompositorContent(org.globus.ws.policy.CompositorContent[] compositorContent) {
        this.compositorContent = compositorContent;
    }

    public org.globus.ws.policy.CompositorContent getCompositorContent(int i) {
        return this.compositorContent[i];
    }

    public void setCompositorContent(int i, org.globus.ws.policy.CompositorContent _value) {
        this.compositorContent[i] = _value;
    }


    /**
     * Gets the usage value for this Compositor.
     * 
     * @return usage
     */
    public org.globus.ws.policy.OpenUsageType getUsage() {
        return usage;
    }


    /**
     * Sets the usage value for this Compositor.
     * 
     * @param usage
     */
    public void setUsage(org.globus.ws.policy.OpenUsageType usage) {
        this.usage = usage;
    }


    /**
     * Gets the preference value for this Compositor.
     * 
     * @return preference
     */
    public java.lang.Integer getPreference() {
        return preference;
    }


    /**
     * Sets the preference value for this Compositor.
     * 
     * @param preference
     */
    public void setPreference(java.lang.Integer preference) {
        this.preference = preference;
    }


    /**
     * Gets the id value for this Compositor.
     * 
     * @return id
     */
    public org.apache.axis.types.Id getId() {
        return id;
    }


    /**
     * Sets the id value for this Compositor.
     * 
     * @param id
     */
    public void setId(org.apache.axis.types.Id id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Compositor)) return false;
        Compositor other = (Compositor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.compositorContent==null && other.getCompositorContent()==null) || 
             (this.compositorContent!=null &&
              java.util.Arrays.equals(this.compositorContent, other.getCompositorContent()))) &&
            ((this.usage==null && other.getUsage()==null) || 
             (this.usage!=null &&
              this.usage.equals(other.getUsage()))) &&
            ((this.preference==null && other.getPreference()==null) || 
             (this.preference!=null &&
              this.preference.equals(other.getPreference()))) &&
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
        if (getCompositorContent() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCompositorContent());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCompositorContent(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUsage() != null) {
            _hashCode += getUsage().hashCode();
        }
        if (getPreference() != null) {
            _hashCode += getPreference().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Compositor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Compositor"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("usage");
        attrField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Usage"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "OpenUsageType"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("preference");
        attrField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Preference"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("id");
        attrField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/07/utility", "Id"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ID"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("compositorContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "CompositorContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "CompositorContent"));
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
