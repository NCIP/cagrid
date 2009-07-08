package org.cagrid.demo.photosharing.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.media.jai.Interpolation;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFrame;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.widget.DisplayJAI;

public class ImageUtils {

	public static byte[] loadImageAsBytes(String filename) throws IOException {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		int off = 0;
		while ((len = fis.read(buf)) != -1) {
			baos.write(buf, 0, len);
			//off += len;
		}
		return baos.toByteArray();
	}

	public static String encode(byte[] bytes) {
		BASE64Encoder encoder = new BASE64Encoder();
		return new String(encoder.encode(bytes));
	}

	public static byte[] decode(String theString) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(theString);
	}


	public static RenderedImage loadImage(String filename) {
		RenderedImage image = JAI.create("fileload", filename);
		return image;
	}

	public static RenderedImage loadImageFromBytes(byte[] bytes) throws IOException {
		SeekableStream ss = new ByteArraySeekableStream(bytes);
		RenderedImage image = JAI.create("stream", ss);

		return image;
	}

	/*
	public static String encodeImage(RenderedImage image) {
	    String format = "PNM";

	    PNMEncodeParam param = new PNMEncodeParam();
	    param.setRaw(false);

	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    RenderedOp op = JAI.create("encode", image,
	                               baos, format, param);

	    return baos.toString();

	}

	public static RenderedImage decodeImage(String data) throws IOException {

		SeekableStream ss = new ByteArraySeekableStream(data.getBytes());
	    RenderedImage image = JAI.create("stream", ss);

	    return image;
	}
	 */

	public static RenderedImage zoom(RenderedImage image, float amount) {
		AffineTransform tr = new AffineTransform(amount, 
				0, 
				0, 
				amount, 
				0.0, 
				0.0); 
		//Specify the type of interpolation. 
		Interpolation interp = new InterpolationNearest(); 
		//Create the affine operation. 
		PlanarImage im2 = (PlanarImage)JAI.create("affine", image, tr, 
				interp); 
		return im2;
	}
	
	/*
	public static RenderedImage rotate45degrees(RenderedImage image, float amount) {
		AffineTransform tr = new AffineTransform(0.707107, 
				-0.707106, 
				0.707106, 
				0.707106, 
				0.0, 
				0.0); 
		//Specify the type of interpolation. 
		Interpolation interp = new InterpolationNearest(); 
		//Create the affine operation. 
		PlanarImage im2 = (PlanarImage)JAI.create("affine", image, tr, 
				interp); 
		return im2;
	}
	
	public static RenderedImage rotate(RenderedImage image, int degrees) {
		// Create the rotation angle (45 degrees) and convert to 
		// radians. 
		int value = degrees; 
		float angle = (float)(value * (Math.PI/180.0F)); 
		// Create a ParameterBlock and specify the source and 
		// parameters 
		int originY = image.getHeight() / 2;
		int originX = image.getWidth() / 2;
		ParameterBlock pb = new ParameterBlock(); 
		pb.addSource(image);                   // The source image 
		pb.add((float)originX);                       // The x origin 
		pb.add((float)originY);                       // The y origin 
		pb.add(angle);                      // The rotation angle 
		pb.add(new InterpolationNearest()); // The interpolation 
		// Create the rotate operation 
		return JAI.create("Rotate", pb, null);
	}
	*/

	public static void main(String[] args) throws IOException, InterruptedException {
		byte[] imageBytes = ImageUtils.loadImageAsBytes("osu_medcenter logo.jpg");
		String encodedImage = ImageUtils.encode(imageBytes);
		byte[] decoded = ImageUtils.decode(encodedImage);
		RenderedImage image = ImageUtils.loadImageFromBytes(decoded);

		DisplayJAI display = new DisplayJAI(image);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.getContentPane().add(display);
		frame.pack();
		frame.show();

		/*
		RenderedImage rotationImage = ImageUtils.rotate(image, 90);
		DisplayJAI rotationDisplay = new DisplayJAI(rotationImage);
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.getContentPane().add(rotationDisplay);
		frame.pack();
		frame.show();
		*/

		RenderedImage rotationImage = ImageUtils.zoom(image, 2);
		DisplayJAI rotationDisplay = new DisplayJAI(rotationImage);
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.getContentPane().add(rotationDisplay);
		frame.pack();
		frame.show();

		/*
		rotationImage = ImageUtils.rotate45degrees(image, 90);
		rotationDisplay = new DisplayJAI(rotationImage);
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.getContentPane().add(rotationDisplay);
		frame.pack();
		frame.show();
		*/

	}

}
