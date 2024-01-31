package pgdp.pingulib.math.matrix;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

/**
 * @author Maximilian Anzinger
 */

public class SquareMatrix implements Serializable {

	private static final long serialVersionUID = 117669030460994L;
	private BigInteger value;
	private final int dimension;
	private SquareMatrix[][] quadrants;

	/**
	 * Initialize matrix filled with 0's
	 * 
	 * @param n dimension of the matrix
	 */
	public SquareMatrix(int n) {

		if (!(n != 0 && (n & (n - 1)) == 0) || n < 1) {
			throw new IllegalArgumentException("n less than 1 or not a power of 2!");
		}

		if (n == 1) {
			value = BigInteger.ZERO;
			quadrants = null;
		} else {
			value = null;
			quadrants = new SquareMatrix[][] { { new SquareMatrix(n / 2), new SquareMatrix(n / 2) },
					{ new SquareMatrix(n / 2), new SquareMatrix(n / 2) } };
		}
		dimension = n;
	}

	/**
	 * Initialize matrix with the given block matrixes
	 * 
	 * @param M11 left upper block matrix
	 * @param M12 right upper block matrix
	 * @param M21 left lower block matrix
	 * @param M22 right lower block matrix
	 */
	public SquareMatrix(SquareMatrix M11, SquareMatrix M12, SquareMatrix M21, SquareMatrix M22) {
		if (M11 == null || M12 == null || M21 == null || M22 == null) {
			throw new IllegalArgumentException("one of the block matrixes is null");
		}
		if (!(M11.getDimension() == M12.getDimension() && M12.getDimension() == M21.getDimension()
				&& M21.getDimension() == M22.getDimension())) {
			throw new IllegalArgumentException("dimensions do not match");
		}

		dimension = M11.getDimension() * 2;
		quadrants = new SquareMatrix[][] { { M11, M12 }, { M21, M22 } };

	}

	/**
	 * Initialize matrix with the given values
	 * 
	 * @param values
	 */
	public SquareMatrix(BigInteger[][] values) {
		int n = values.length;
		if (!(n != 0 && (n & (n - 1)) == 0) || n < 1) {
			throw new IllegalArgumentException("illegal dimensions");
		}
		for (BigInteger[] arr : values) {
			if (arr == null || arr.length != n) {
				throw new IllegalArgumentException("illegal dimensions");
			}
		}
		dimension = n;
		if (n == 1) {
			if (values[0][0] == null) {
				throw new IllegalArgumentException("array contains null value");
			}
			value = values[0][0];
			quadrants = null;
			return;
		}
		value = null;
		quadrants = new SquareMatrix[][] {
				{ new SquareMatrix(values, 1, n / 2, 1, n / 2), new SquareMatrix(values, 1, n / 2, n / 2 + 1, n) },
				{ new SquareMatrix(values, n / 2 + 1, n, 1, n / 2),
						new SquareMatrix(values, n / 2 + 1, n, n / 2 + 1, n) } };
	}

	/**
	 * Initialize matrix with the given values of a sub array
	 * 
	 * @param values
	 * @param iMin   sub array start index top
	 * @param iMax   sub array end index bottom
	 * @param jMin   sub array start index left side
	 * @param jMax   sub array end index right side
	 */
	private SquareMatrix(BigInteger[][] values, int iMin, int iMax, int jMin, int jMax) {
		int n = iMax - iMin + 1;
		dimension = n;
		if (n == 1) {
			if (values[iMin - 1][jMin - 1] == null) {
				throw new IllegalArgumentException("array contains null value");
			}
			value = values[iMin - 1][jMin - 1];
			quadrants = null;
		} else {
			value = null;
			quadrants = new SquareMatrix[][] {
					{ new SquareMatrix(values, iMin, n / 2 + iMin - 1, jMin, n / 2 + jMin - 1),
							new SquareMatrix(values, iMin, n / 2 + iMin - 1, n / 2 + jMin, jMax) },
					{ new SquareMatrix(values, n / 2 + iMin, iMax, jMin, n / 2 + jMin - 1),
							new SquareMatrix(values, n / 2 + iMin, iMax, n / 2 + jMin, jMax) } };
		}
	}

	public int getDimension() {
		return dimension;
	}

	/**
	 * Retrieves the value at index (i,j)
	 * 
	 * @param i row (starting at index 1)
	 * @param j column (starting at index 1)
	 * @return value at index (i,j)
	 */
	public BigInteger get(int i, int j) {
		if (i < 1 || j < 1 || dimension < i || dimension < j) {
			throw new IllegalArgumentException("i and/or j out of bounds!");
		}

		if (value != null) {
			return value;
		}

		return quadrants[i <= dimension / 2 ? 0 : 1][j <= dimension / 2 ? 0 : 1]
				.get(i <= dimension / 2 ? i : i - (dimension / 2), j <= dimension / 2 ? j : j - (dimension / 2));
	}

	/**
	 * Set the value at index (i,j)
	 * 
	 * @param i        row (starting at index 1)
	 * @param j        column (starting at index 1)
	 * @param newValue new value
	 */
	public void set(int i, int j, BigInteger newValue) {
		if (i < 1 || j < 1 || dimension < i || dimension < j) {
			throw new IllegalArgumentException("i and/or j out of bounds!");
		}
		if (newValue == null) {
			throw new IllegalArgumentException("value can not be null");
		}
		if (value != null) {
			value = newValue;
			return;
		}
		quadrants[i <= dimension / 2 ? 0 : 1][j <= dimension / 2 ? 0 : 1].set(
				i <= dimension / 2 ? i : i - (dimension / 2), j <= dimension / 2 ? j : j - (dimension / 2), newValue);
	}

	/**
	 * Retrieves the block matrix at index (i,j)
	 * 
	 * @param i row (starting at index 1)
	 * @param j column (starting at index 1)
	 * @return block matrix
	 */
	public SquareMatrix getQuadrant(int i, int j) {
		if (i < 1 || j < 1 || 2 < i || 2 < j) {
			throw new IllegalArgumentException("i and/or j out of bounds!");
		}
		return quadrants[i - 1][j - 1];
	}

	/**
	 * Replaces the block matrix at index (i,j) with the given matrix
	 * 
	 * @param i        row (starting at index 1)
	 * @param j        column (starting at index 1)
	 * @param quadrant new block matrix
	 */
	public void setQuadrant(int i, int j, SquareMatrix quadrant) {
		if (i < 1 || j < 1 || 2 < i || 2 < j) {
			throw new IllegalArgumentException("i and/or j out of bounds!");
		}
		if (quadrant == null) {
			throw new IllegalArgumentException("quadrant can not be null");
		}
		if (quadrant.getDimension() != dimension / 2) {
			throw new IllegalArgumentException("illegal dimensions");
		}
		quadrants[i - 1][j - 1] = quadrant;
	}

	/**
	 * Negates each value
	 */
	public void negate() {
		if (dimension == 1) {
			value = value.negate();
		} else {
			quadrants[0][0].negate();
			quadrants[0][1].negate();
			quadrants[1][0].negate();
			quadrants[1][1].negate();
		}
	}

	public void transpose() {
		if (dimension != 1) {
			quadrants[0][0].transpose();
			quadrants[0][1].transpose();
			quadrants[1][0].transpose();
			quadrants[1][1].transpose();
			SquareMatrix tmp = quadrants[0][1];
			quadrants[0][1] = quadrants[1][0];
			quadrants[1][0] = tmp;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof SquareMatrix) {
			SquareMatrix other = (SquareMatrix) o;
			if (dimension != other.getDimension()) {
				return false;
			}
			if (dimension == 1) {
				return value.equals(other.value);
			} else {
				for (int i = 0; i < 2; i++) {
					for (int j = 0; j < 2; j++) {
						if (!quadrants[i][j].equals(other.quadrants[i][j])) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		for (int i = 1; i <= dimension; i++) {
			for (int j = 1; j <= dimension; j++) {
				out.append(this.get(i, j).toString());
				if (j != dimension) {
					out.append("\t");
				}
			}
			if (i != dimension) {
				out.append("\n");
			}
		}
		return out.toString();
	}

	/**
	 * Generates a matrix of dimension n and fills it with random values. This
	 * method will use the default-initialized random number generator.
	 * 
	 * @see java.util.Random
	 * 
	 * @param n dimension of the matrix
	 * @return matrix
	 */
	public static SquareMatrix generateRandomMatrix(int n) {
		if (!(n != 0) && ((n & (n - 1)) == 0) || n < 1) {
			throw new IllegalArgumentException("n less than 1 or not a power of 2!");
		}

		return generateRandomMatrix(n, new Random());
	}

	/**
	 * Generates a matrix of dimension n and fills it with random values. This
	 * method will use the given random number generator.
	 * 
	 * @see java.util.Random
	 * 
	 * @param n   dimension of the matrix
	 * @param rnd random number generator
	 * @return matrix
	 */
	public static SquareMatrix generateRandomMatrix(int n, Random rnd) {
		if (!(n != 0) && ((n & (n - 1)) == 0) || n < 1) {
			throw new IllegalArgumentException("n less than 1 or not a power of 2!");
		}

		SquareMatrix M = new SquareMatrix(n);
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				M.set(i, j, BigInteger.valueOf(rnd.nextLong()));
			}
		}
		return M;
	}
}
