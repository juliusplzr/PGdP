package pgdp.array;

public class Array {
	public static int[][] minsAndMaxs(int[][] a) {
		if (a.length == 0) {
			return new int[0][0];
		}

		int[][] extrema = new int[a.length][2];
		for (int i = 0; i < a.length; i++) {
			int min_i = Integer.MAX_VALUE;
			int max_i = Integer.MIN_VALUE;

			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] > max_i) {
					max_i = a[i][j];
				}

				if (a[i][j] < min_i) {
					min_i = a[i][j];
				}
			}

			extrema[i][0] = min_i;
			extrema[i][1] = max_i;
		}

		return extrema;
	}

	public static int[][] transpose(int[][] a) {
		if (a.length == 0) {
			return new int[0][0];
		}

		int height = a.length;
		int width = a[0].length;

        	for (int[] ints : a) {
            		if (ints.length != width) {
                		throw new IllegalArgumentException("Inner Arrays must be of same length!");
            		}
        	}

		int[][] transpose = new int[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				transpose[i][j] = a[j][i];
			}
		}

		return transpose;
	}

	public static int[] linearize(int[][] a) {
		if (a.length == 0) {
			return new int[0];
		}

		int elemCount = 0;
		
        	for (int[] ints : a) {
            		for (int j = 0; j < ints.length; j++) {
                		elemCount++;
            		}
       		}

		int[] linearized = new int[elemCount];

		int index = 0;
		for (int[] ints : a) {
            		for (int anInt : ints) {
                		if (index < elemCount) {
                    			linearized[index] = anInt;
					index++;
                		}

            		}
		}

		return linearized;
	}

	public static boolean crossword(char[][] letterGrid, char[] word) {
		if (letterGrid.length == 0) {
			return false;
		} else if (word.length > letterGrid.length && word.length > letterGrid[0].length) {
			return false;
		} else {
			boolean found = false;

			for (int x = 0; x < letterGrid.length; x++) {
				for (int y = 0; y < letterGrid[x].length; y++) {
					found = traverseRight(letterGrid, word, x, y) || traverseDown(letterGrid, word, x, y) ||
							traverseDiagonal(letterGrid, word, x, y);

					if (found) {
						break;
					}
				}

				if (found) {
					break;
				}
			}

			return found;

		}
	}

	// <|=================== Helpers ===================|>

	public static boolean traverseRight(char[][] letterGrid, char[] word, int startIndexX, int startIndexY) {
		int letterIndex = 0;

		for (int j = startIndexY; j < letterGrid[startIndexX].length; j++) {
			if (word[letterIndex] == letterGrid[startIndexX][j]) {
				letterIndex++;
			} else {
				break;
			}
		}

		return letterIndex == word.length;
	}

	public static boolean traverseDown(char[][] letterGrid, char[] word, int startIndexX, int startIndexY) {
		int letterIndex = 0;

		for (int i = startIndexX; i < letterGrid.length; i++) {
			if (word[letterIndex] == letterGrid[i][startIndexY]) {
				letterIndex++;
			} else {
				break;
			}
		}

		return letterIndex == word.length;
	}

	public static boolean traverseDiagonal(char[][] letterGrid, char[] word, int startIndexX, int startIndexY)  {
		int letterIndex = 0;
		int deltaX = letterGrid[0].length - startIndexX;

		if (deltaX == 0 && word.length > 0) {
			return false;
		}

		int i = startIndexX;
		int j = startIndexY;

		while (deltaX > 0) {
			if (letterGrid[i][j] == word[letterIndex]) {
				i++;
				j++;
				letterIndex++;
			} else {
				break;
			}
			deltaX--;
		}

		return letterIndex == word.length;
	}
}
