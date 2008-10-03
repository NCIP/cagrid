package org.cagrid.gme.service;

import java.net.URI;

import org.cagrid.gme.domain.XMLSchemaBundle;
import org.cagrid.gme.stubs.types.NoSuchNamespaceExistsFault;
import org.springframework.test.annotation.ExpectedException;


public class GMEGetDependenciesSimpleTestCase extends GMETestCaseWithSimpleModel {

    public void testAddAll() throws Exception {
        publishAllSchemas();

        assertSchemaImportsSchema(this.testSchemaSimpleA, this.testSchemaSimpleB);
        assertNotImported(this.testSchemaSimpleA);
        assertSchemaImportsSchema(this.testSchemaSimpleB, this.testSchemaSimpleC);
        assertNoImports(this.testSchemaSimpleC);
        assertSchemaImportsSchema(this.testSchemaSimpleD, this.testSchemaSimpleB);
        assertNotImported(this.testSchemaSimpleD);
        assertSchemaImportsSchema(this.testSchemaSimpleD, this.testSchemaSimpleE);
        assertNoImports(this.testSchemaSimpleE);
        assertNoImports(this.testSchemaSimpleF);
        assertNotImported(this.testSchemaSimpleF);
    }


    @ExpectedException(NoSuchNamespaceExistsFault.class)
    public void testInvalidSchemaImports() throws Exception {
        publishAllSchemas();

        this.gme.getImportedNamespaces(new URI("http://invalid"));
    }


    @ExpectedException(NoSuchNamespaceExistsFault.class)
    public void testInvalidSchemaImported() throws Exception {
        publishAllSchemas();

        this.gme.getImportingNamespaces(new URI("http://invalid"));
    }


    @ExpectedException(NoSuchNamespaceExistsFault.class)
    public void testNullSchemaImports() throws Exception {
        publishAllSchemas();

        this.gme.getImportedNamespaces(null);
    }


    @ExpectedException(NoSuchNamespaceExistsFault.class)
    public void testNullSchemaImported() throws Exception {
        publishAllSchemas();

        this.gme.getImportingNamespaces(null);
    }


    @ExpectedException(NoSuchNamespaceExistsFault.class)
    public void testNullBundle() throws Exception {
        publishAllSchemas();

        this.gme.getSchemBundle(null);
    }


    @ExpectedException(NoSuchNamespaceExistsFault.class)
    public void testInvalidBundle() throws Exception {
        publishAllSchemas();

        this.gme.getSchemBundle(new URI("http://invalid"));
    }


    public void testBundle() throws Exception {
        publishAllSchemas();

        XMLSchemaBundle schemBundle = this.gme.getSchemBundle(this.testSchemaSimpleA.getTargetNamespace());
        // TODO: make assertions on this
    }
}
