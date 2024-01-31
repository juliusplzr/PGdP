package pgdp.filter;

import java.awt.image.BufferedImage;

public class Frame {

	private final BufferedImage pixels;
	private final int frameNumber;
	private final int width;
	private final int height;

	public Frame(int width, int height, int frameNumber) {

		this.frameNumber = frameNumber;
		this.width = width;
		this.height = height;

		pixels = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public Frame(BufferedImage pixels, int frameNumber) {

		this.pixels = pixels;
		this.frameNumber = frameNumber;
		this.width = pixels.getWidth();
		this.height = pixels.getHeight();
	}

	public BufferedImage getPixels() {
		return pixels;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
