package org.cagrid.demo.photosharing.utils;

public enum ImageType {

	PNG ("PNG"),
	JPG ("JPG");

	private String extension;
	
	ImageType(String extension) {
		this.extension = extension;
	}
	
	public String getExtension() {
		return this.extension;
	}
	
	@Override
	public String toString() {
		return this.extension;
	}
	
}
