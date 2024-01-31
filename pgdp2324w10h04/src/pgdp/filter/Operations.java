
package pgdp.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public final class Operations {
	
	//Verstecke den Konstruktor einer utility KlasseS
	private Operations() {}

	private static final double RED_FACTOR = 0.299;
	private static final double GREEN_FACTOR = 0.587;
	private static final double BLUE_FACTOR = 0.114;
	
	public static Frame grayscale(Frame frame) {

		BufferedImage img = frame.getPixels();

		for (int x = 0; x < img.getWidth(); x++) {

			for (int y = 0; y < img.getHeight(); y++) {

				Color col = new Color(img.getRGB(x, y));
				int avg = ((int) (RED_FACTOR * col.getRed() + GREEN_FACTOR * col.getGreen() + BLUE_FACTOR * col.getBlue())) & 0xff;
				Color newCol = new Color(avg, avg, avg, col.getAlpha());
				img.setRGB(x, y, newCol.getRGB());

			}
		}

		return frame;
	}

	public static Function<Frame, Frame> crop(int width, int height) {
		return (f) -> {

			BufferedImage in = f.getPixels();
			BufferedImage out = new BufferedImage(width, height, in.getType());

			Frame cropped = new Frame(out, f.getFrameNumber());

			int x_offset = (width - f.getWidth()) / 2;
			int y_offset = (height - f.getHeight()) / 2;

			for (int x = 0; x < width; x++) {

				for (int y = 0; y < height; y++) {

					if (x - x_offset < 0 || y - y_offset < 0 || x - x_offset >= f.getWidth() || y - y_offset >= f.getHeight()) {
						out.setRGB(x, y, Color.BLACK.getRGB());
					} else {
						out.setRGB(x, y, in.getRGB(x - x_offset, y - y_offset));
					}
				}
			}

			return cropped;
		};
	}

	public static Function<Frame, Frame> encode(String msg) {
		return f -> {

			int fnum = f.getFrameNumber();
			int width = f.getWidth() - f.getWidth()%8;
			String subMsg = msg.substring(Math.min(fnum * width / 8, msg.length()),
					Math.min((fnum + 1) * width / 8, msg.length()));

			int[] bits = new int[subMsg.length() * 8];

			for (int i = 0; i < subMsg.length(); i++) {

				int b = subMsg.charAt(i);

				for (int j = 0; j < 8; j++) {
					bits[i * 8 + 7 - j] = b & 1;
					b = b >>> 1;
				}
			}

			for (int i = 0; i < bits.length; i++) {
				f.getPixels().setRGB(i, f.getHeight() - 1,
						(bits[i] == 1) ? Color.WHITE.getRGB() : Color.black.getRGB());
			}

			return f;
		};
	}
	
	public static String decode(Frame f) {

		int[] bytes = new int[f.getWidth() / 8];

		for (int i = 0; i < bytes.length; i++) {

			for (int j = 0; j < 8; j++) {

				bytes[i] <<= 1;

				Color c = new Color(f.getPixels().getRGB(i * 8 + j, f.getHeight() - 1));

				int val = c.getRed() + c.getGreen() + c.getBlue();

				bytes[i] |= val > 3 * 128 ? 1 : 0;
			}
		}

		StringBuilder out = new StringBuilder();

		for (int i : bytes) {
			out.append((char) i);
		}

		return out.toString();
	}

}
