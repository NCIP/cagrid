package org.cagrid.demo.photosharing.utils;

public class ImageDescription {

	private Long id;
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	private String name;
	private String description;
	private String type;
	
	public ImageDescription(Long id, String name, String description, String type) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
	}
	
	
	
}
