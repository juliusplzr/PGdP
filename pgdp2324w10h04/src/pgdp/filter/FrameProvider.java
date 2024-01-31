
package pgdp.filter;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * Utility class to provide Frames
 *
 * @return
 */
public class FrameProvider {

	private FFmpegFrameGrabber grabber;
	private Java2DFrameConverter paintConverter;
	private File video;
	private int frameCount;

	// Nur Fürs Testing, nicht benutzen
	public FrameProvider() {}
	
	public FrameProvider(String path) {

		video = new File(path);

		grabber = new FFmpegFrameGrabber(video);
		try {
			grabber.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paintConverter = new Java2DFrameConverter();
	}

	public boolean fileExists() {
		return video.exists();
	}

	public int getWidth() {
		return grabber.getImageWidth();
	}

	public int getHeight() {
		return grabber.getImageHeight();
	}

	public double getFrameRate() {
		return grabber.getFrameRate();
	}

	public int getBitrate() {
		return grabber.getVideoBitrate();
	}

	public Frame nextFrame() throws Exception {

		org.bytedeco.javacv.Frame frame = grabber.grabFrame(false, true, true, false);

		if (frame == null) {
			return null;
		}

		return new Frame(deepCopy(paintConverter.convert(frame)), frameCount++);
	}
	
	private static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public void close() throws org.bytedeco.javacv.FrameGrabber.Exception {
		grabber.close();
		paintConverter.close();
	}
}
