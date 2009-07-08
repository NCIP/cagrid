package org.cagrid.demo.photosharing.utils;


public class Image {

	private ImageDescription description;
	
	private String uu64encode;
	
	public Image(ImageDescription description, String uu64encode) {
		this.description = description;
		this.uu64encode = uu64encode;
	}
	
	public ImageDescription getDescription() {
		return description;
	}

	public String getImageData() {
		return uu64encode;
	}
	
	
}
