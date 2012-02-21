/**
 * NoPermissionFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package org.globus.wsrf.tests.basic;

public class NoPermissionFault extends org.apache.axis.AxisFault {
    public java.lang.String description;
    public java.lang.String getDescription() {
        return this.description;
    }

    public NoPermissionFault() {
    }

      public NoPermissionFault(java.lang.String description) {
        this.description = description;
    }

    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, description);
    }
}
