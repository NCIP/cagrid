package org.cagrid.identifiers.namingauthority.impl;

import org.cagrid.identifiers.core.*;
import org.cagrid.identifiers.namingauthority.*;
import org.cagrid.identifiers.namingauthority.util.Database;

import org.cagrid.identifiers.namingauthority.util.*;
import org.cagrid.identifiers.namingauthority.http.HttpServer;

public class NamingAuthorityImpl extends NamingAuthority implements IdentifierMaintainer, IdentifierUser {

	private HttpServer httpServer = null;
	private Database database;
	
	public void initialize() {
		database.initialize();
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public void setDatabase( Database database ) {
		this.database = database;
	}

	public String create(IdentifierValues values) throws Exception {
		if (values == null)
			throw new Exception("Input IdentifierValues can't be null");
		
		String identifier = generateIdentifier();
		database.save(identifier, values);
        return IdentifierUtil.build(getConfiguration().getPrefix(), identifier);
	}
	
	public IdentifierValues getValues( String identifier ) {
		return database.getValues(
				IdentifierUtil.getLocalName( getConfiguration().getPrefix(), identifier ));
	}
	
	
	// This starts a jetty http server for debugging/playing/whatever
	// purposes, not used in a live/production system
	public synchronized void startHttpServer() {
		if (httpServer == null) {
			httpServer = new HttpServer(this, 
					((NamingAuthorityConfigImpl)getConfiguration()).getHttpServerPort());
			httpServer.start();
		}
	}
}
