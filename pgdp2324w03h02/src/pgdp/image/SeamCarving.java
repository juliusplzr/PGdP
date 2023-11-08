package pgdp.image;

import java.util.Arrays;

public class SeamCarving {

	public int computeGradientMagnitude(int v1, int v2) {
		// Euclidean norm of rgb pixel difference squared
		int blueDiffSq = (valueBlue(v1) - valueBlue(v2)) * (valueBlue(v1) - valueBlue(v2));
		int redDiffSq = (valueRed(v1) - valueRed(v2)) * (valueRed(v1) - valueRed(v2));
		int greenDiffSq = (valueGreen(v1) - valueGreen(v2)) * (valueGreen(v1) - valueGreen(v2));
		return blueDiffSq + redDiffSq + greenDiffSq;
	}

	public void toGradientMagnitude(int[] picture, int[] gradientMagnitude, int width, int height) {
		// Return void if either parameter 0
		if (width == 0 || height == 0) {
			return;
		}

		for (int y = 0; y < height; y++) {
			for (int x = 1; x < (width - 1); x++) {
				int leftNeighbor = picture[y * width + x];
				int rightNeighbor = picture[toIndex(x + 1, y, width)];
				gradientMagnitude[toIndex(x, y, width)] = computeGradientMagnitude(leftNeighbor, rightNeighbor);
			}
		}

		for (int y = 1; y < (height - 1); y++) {
			for (int x = 0; x < width; x++) {
				int topNeighbor = picture[toIndex(x, y - 1, width)];
				int bottomNeighbor = picture[toIndex(x, y + 1, width)];
				gradientMagnitude[y * width + x] += computeGradientMagnitude(topNeighbor, bottomNeighbor);
			}
		}

		for (int x = 0; x < width; x++) {
			gradientMagnitude[toIndex(x, 0, width)] = Integer.MAX_VALUE;
			gradientMagnitude[toIndex(x, height -1, width)] = Integer.MAX_VALUE;
		}
		for (int y = 0; y < height; y++) {
			gradientMagnitude[toIndex(0, y, width)] = Integer.MAX_VALUE;
			gradientMagnitude[toIndex(width -1, y, width)] = Integer.MAX_VALUE;
		}
	}

	public int toIndex(int x, int y, int width) {
		return y * width + x;
	}

	public void combineMagnitudeWithMask(int[] gradientMagnitude, int[] mask, int width, int height) {
		if (width == 0 || height == 0) {
			return;
		}

		for (int i = 0; i < mask.length; i++) {
			if (valueRed(mask[i]) == 0 && valueGreen(mask[i]) == 0 && valueBlue(mask[i]) == 0) {
				gradientMagnitude[i] = Integer.MAX_VALUE;
			}
		}
	}

	public void buildSeams(int[][] seams, long[] seamWeights, int[] gradientMagnitude,
			int width, int height) {
		if (width == 0 || height == 0) {
			return;
		}

		// Iterate over columns (x-axis)
		for (int colIndex = 0; colIndex < width; colIndex++) {
			seams[colIndex][0] = colIndex;
			seamWeights[colIndex] = gradientMagnitude[colIndex];
		}

		for (int colIndex = 0; colIndex < width; colIndex++) {
			int[] seam = seams[colIndex];

			// Iterate over rows (y-axis)
			for (int rowIndex = 1; rowIndex < height; rowIndex++) {
				int currentCol = seam[rowIndex - 1];

				int left = currentCol > 0 ? gradientMagnitude[rowIndex * (currentCol - 1) + width] : Integer.MAX_VALUE;
				int right = currentCol < width - 1 ? gradientMagnitude[rowIndex * (currentCol + 1) + width] : Integer.MAX_VALUE;
				int middle = gradientMagnitude[rowIndex * currentCol + width];

				if (middle <= left && middle <= right) {
					seam[rowIndex] = currentCol;
					seamWeights[colIndex] += middle;
				} else if (left <= right) {
					seam[rowIndex] = currentCol - 1;
					seamWeights[colIndex] += left;
				} else {
					seam[rowIndex] = currentCol + 1;
					seamWeights[colIndex] += right;
				}
			}
		}

	}

	public void removeSeam(int[] seam, int[] image, int height, int oldWidth) {
		if (height == 0 || oldWidth <= 1) {
			return;
		}

		int counter = 0;
		int rowIndex = 0;
		int remove = (seam[rowIndex]) * oldWidth + rowIndex;

		for (int colIndex = 0; colIndex < image.length; colIndex++) {
			if (colIndex == remove) {
				counter++;
				rowIndex++;

				if (rowIndex < height) {
					remove = (seam[rowIndex]) * oldWidth + rowIndex;
				}
			} else {
				image[colIndex - counter] = image[colIndex];
			}
		}
	}

	public int[] shrink(int[] image, int[] mask, int width, int height, int newWidth) {
		if (width == 0 || height == 0 || newWidth == 0) {
			return new int[0];
		}

		int[] gradientMagnitude = new int[width * height];
		int[][] seams = new int[width][height];
		long[] seamWeights = new long[width];

		for (int w = width; w > newWidth; w--) {
			toGradientMagnitude(image, gradientMagnitude, w, height);
			combineMagnitudeWithMask(gradientMagnitude, mask, w, height);
			buildSeams(seams, seamWeights, gradientMagnitude, w, height);

			int min = 0;
			for (int i = 0; i < w; i++) {
				if (seamWeights[i] < seamWeights[min]) {
					min = i;
				}
			}

			removeSeam(seams[min], image, height, w);
			removeSeam(seams[min], mask, height, w);
		}

		return Arrays.copyOf(image, newWidth * height);
	}


	// <|=================== Helpers ===================|>

	// Get RGB values from pixel int, for further info visit
	// https://dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	private static int valueRed(int pixel) {
		return (pixel>>16) & 0xff;
	}
	private static int valueGreen(int pixel) {
		return (pixel>>8) & 0xff;
	}
	private static int valueBlue(int pixel) {
		return pixel & 0xff;
	}
}