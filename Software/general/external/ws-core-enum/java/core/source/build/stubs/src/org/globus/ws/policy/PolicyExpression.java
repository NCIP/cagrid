/**
 * PolicyExpression.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.ws.policy;

public class PolicyExpression  implements java.io.Serializable {
    private org.globus.ws.policy.CompositorContent[] compositorContent;
    private org.apache.axis.types.NCName name;  // attribute
    private org.apache.axis.types.URI targetNamespace;  // attribute
    private org.apache.axis.types.Id id;  // attribute

    public PolicyExpression() {
    }

    public PolicyExpression(
           org.globus.ws.policy.CompositorContent[] compositorContent,
           org.apache.axis.types.Id id,
           org.apache.axis.types.NCName name,
           org.apache.axis.types.URI targetNamespace) {
           this.compositorContent = compositorContent;
           this.name = name;
           this.targetNamespace = targetNamespace;
           this.id = id;
    }


    /**
     * Gets the compositorContent value for this PolicyExpression.
     * 
     * @return compositorContent
     */
    public org.globus.ws.policy.CompositorContent[] getCompositorContent() {
        return compositorContent;
    }


    /**
     * Sets the compositorContent value for this PolicyExpression.
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
     * Gets the name value for this PolicyExpression.
     * 
     * @return name
     */
    public org.apache.axis.types.NCName getName() {
        return name;
    }


    /**
     * Sets the name value for this PolicyExpression.
     * 
     * @param name
     */
    public void setName(org.apache.axis.types.NCName name) {
        this.name = name;
    }


    /**
     * Gets the targetNamespace value for this PolicyExpression.
     * 
     * @return targetNamespace
     */
    public org.apache.axis.types.URI getTargetNamespace() {
        return targetNamespace;
    }


    /**
     * Sets the targetNamespace value for this PolicyExpression.
     * 
     * @param targetNamespace
     */
    public void setTargetNamespace(org.apache.axis.types.URI targetNamespace) {
        this.targetNamespace = targetNamespace;
    }


    /**
     * Gets the id value for this PolicyExpression.
     * 
     * @return id
     */
    public org.apache.axis.types.Id getId() {
        return id;
    }


    /**
     * Sets the id value for this PolicyExpression.
     * 
     * @param id
     */
    public void setId(org.apache.axis.types.Id id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PolicyExpression)) return false;
        PolicyExpression other = (PolicyExpression) obj;
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
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.targetNamespace==null && other.getTargetNamespace()==null) || 
             (this.targetNamespace!=null &&
              this.targetNamespace.equals(other.getTargetNamespace()))) &&
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getTargetNamespace() != null) {
            _hashCode += getTargetNamespace().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PolicyExpression.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "PolicyExpression"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("name");
        attrField.setXmlName(new javax.xml.namespace.QName("", "Name"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "NCName"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("targetNamespace");
        attrField.setXmlName(new javax.xml.namespace.QName("", "TargetNamespace"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
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
