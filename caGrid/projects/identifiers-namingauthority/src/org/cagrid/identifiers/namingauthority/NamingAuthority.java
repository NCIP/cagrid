package org.cagrid.identifiers.namingauthority;

public abstract class NamingAuthority {
	
	private NamingAuthorityConfig configuration;
	private IdentifierGenerator identifierGenerator;
	
	public NamingAuthorityConfig getConfiguration() { 
		return this.configuration; 
	}
	
	public void setConfiguration( NamingAuthorityConfig config ) {
		this.configuration = config;
	}
	
	public String generateIdentifier() { 
		return identifierGenerator.generate(configuration);
	}
	
	public IdentifierGenerator getIdentifierGenerator() {
		return identifierGenerator;
	}
	
	public void setIdentifierGenerator( IdentifierGenerator generator ) {
		this.identifierGenerator = generator;
	}
	
	public abstract void initialize();
}
