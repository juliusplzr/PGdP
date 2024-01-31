package pgdp.pingulib.math.matrix;

/**
 * @author Maximilian Anzinger
 */

public class Playground {

	/**
	 * Outputs some performance measures in the console.
	 * 
	 * @param op     operation to be tested
	 * @param n      number of test runs
	 * @param d      dimension of the matrixes
	 * @param minDim argument for the execution of add/mul parallel
	 */
	public static void plotPerformance(OP op, int n, int d, int minDim) {
		System.out.println("Plotting " + (op == OP.ADD ? "ADD" : "MUL") + " performance:\n");
		System.out.println("Sequential | Parallel | S/P");

		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		double avg = 0.0;
		for (int i = 0; i <= n; i++) {
			SquareMatrix A = SquareMatrix.generateRandomMatrix(d);
			SquareMatrix B = SquareMatrix.generateRandomMatrix(d);
			long startSequential = System.nanoTime();
			if (op == OP.ADD) {
				SquareMatrixAdd.addSequential(A, B);
			} else {
				SquareMatrixMul.mulSequential(A, B);
			}
			long endSequential = System.nanoTime();
			long startParallel;
			startParallel = System.nanoTime();
			if (op == OP.ADD) {
				SquareMatrixAdd.addParallel(A, B, minDim);
			} else {
				SquareMatrixMul.mulParallel(A, B, minDim);
			}
			long endParallel = System.nanoTime();
			if (i != 0) { // run i = 0 is cut for better accuracy
				System.out.print(String.format("%,.2fms", (endSequential - startSequential) / 1e6));
				System.out.print(" | ");
				System.out.print(String.format("%,.2fms", (endParallel - startParallel) / 1e6));
				System.out.print(" | ");
				double q = (double) (endSequential - startSequential) / (double) (endParallel - startParallel);
				avg += q;
				System.out.println(String.format("%,.2f", q));
				if (q < min) {
					min = q;
				}
				if (max < q) {
					max = q;
				}
			}
		}
		System.out.println("Min. S/P: ~" + String.format("%,.2f", min));
		System.out.println("Max. S/P: ~" + String.format("%,.2f", max));
		System.out.println("Avg. S/P: ~" + String.format("%,.2f", avg / n));
	}

	/**
	 * Outputs some performance measures for matrix addition in the console.
	 * 
	 * @param n      number of test runs
	 * @param d      dimension of the matrixes
	 * @param minDim argument for the execution of add/mul parallel
	 */
	public static void plotAddPerformance(int n, int d, int minDim) {
		plotPerformance(OP.ADD, n, d, minDim);
	}

	/**
	 * Outputs some performance measures for matrix multiplication in the console.
	 * 
	 * @param n      number of test runs
	 * @param d      dimension of the matrixes
	 * @param minDim argument for the execution of add/mul parallel
	 */
	public static void plotMulPerformance(int n, int d, int minDim) {
		plotPerformance(OP.MUL, n, d, minDim);
	}

	public static void main(String[] args) {
		plotAddPerformance(10, 1 << 7, 1 << 5);
		plotMulPerformance(10, 1 << 7, 1 << 5);
	}

	private enum OP {
		ADD, MUL
	}
}
