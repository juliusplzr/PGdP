package pgdp.pingulib.math.matrix;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Maximilian Anzinger
 */

public class SquareMatrixMul {

	private static final int MIN_DIM = 1 << 1;

	/**
	 * Sequential matrix multiplication of A and B
	 * 
	 * @param A matrix
	 * @param B matrix
	 * @return result of the matrix multiplication in a new matrix
	 */
	public static SquareMatrix mulSequential(SquareMatrix A, SquareMatrix B) {
		if (A == null) {
			throw new IllegalArgumentException("A can not be null");
		}
		if (B == null) {
			throw new IllegalArgumentException("B can not be null");
		}
		if (A.getDimension() != B.getDimension()) {
			throw new IllegalArgumentException("A and B have different dimensions");
		}
		SquareMatrix C = new SquareMatrix(A.getDimension());
		for (int i = 1; i <= A.getDimension(); i++) {
			for (int k = 1; k <= A.getDimension(); k++) {
				C.set(i, k, BigInteger.ZERO);
				for (int j = 1; j <= A.getDimension(); j++) {
					C.set(i, k, C.get(i, k).add(A.get(i, j).multiply(B.get(j, k))));
				}
			}
		}
		return C;
	}

	/**
	 * Parallel matrix multiplication of A and B. If the dimensions are smaller or
	 * equal than the default value <MIN_DIM>, the sequential matrix multiplication
	 * will be applied.
	 * 
	 * @param A matrix
	 * @param B matrix
	 * @return result of the matrix multiplication in a new matrix
	 */
	public static SquareMatrix mulParallel(SquareMatrix A, SquareMatrix B) {
		return mulParallel(A, B, MIN_DIM);
	}

	/**
	 * Parallel matrix multiplication of A and B. If <minDim> is smaller than the
	 * default value <MIN_DIM>, the default value will be used. If the dimensions
	 * are smaller or equal than <minDim>, the sequential matrix multiplication will
	 * be applied.
	 * 
	 * @param A      matrix
	 * @param B      matrix
	 * @param minDim dimension
	 * @return result of the matrix multiplication in a new matrix
	 */
	public static SquareMatrix mulParallel(SquareMatrix A, SquareMatrix B, int minDim) {
		if (A == null) {
			throw new IllegalArgumentException("A can not be null");
		}
		if (B == null) {
			throw new IllegalArgumentException("B can not be null");
		}
		if (A.getDimension() != B.getDimension()) {
			throw new IllegalArgumentException("A and B have different dimensions");
		}

		if (minDim < MIN_DIM) {
			minDim = MIN_DIM;
		}

		if (A.getDimension() <= minDim) {
			return mulSequential(A, B);
		}

		SquareMatrix[] result = new SquareMatrix[1];
		ComputeThread thread = new MulComputeThread(0, A, B, minDim, result);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result[0];
	}

	private static class MulComputeThread extends ComputeThread {

		private static AtomicInteger computeThreadCounter = new AtomicInteger(0);

		/**
		 * initialize compute thread for matrix multiplication.
		 * 
		 * @param threadID index where result will be stored
		 * @param A        first matrix
		 * @param B        second matrix
		 * @param minDim   threshold for sequential computation
		 * @param results  array reference where the result will be stored
		 */
		MulComputeThread(int threadID, SquareMatrix A, SquareMatrix B, int minDim, SquareMatrix[] results) {
			super(threadID, A, B, minDim, results);
			this.setName("MulComputeThread-" + computeThreadCounter.incrementAndGet());
		}

		/**
		 * resets the thread counter
		 */
		public static synchronized void resetThreadCount() {
			computeThreadCounter = new AtomicInteger(0);
		}

		/**
		 * retrieves the current thread count
		 * 
		 * @return thread count
		 */
		public static AtomicInteger getThreadCount() {
			return computeThreadCounter;
		}

		@Override
		public void run() {
			if (super.getA().getDimension() <= super.getMinDim()) {
				super.getResults()[super.getThreadID()] = mulSequential(super.getA(), super.getB());
			} else { // recursively generate new threads
				// compute first intermediate results (8 products of two quadrants)
				SquareMatrix[] products = new SquareMatrix[8];
				ComputeThread[] threads = generateComputeThreads(super.getA(), super.getB(), super.getMinDim(),
						products);

				// start threads
				for (int i = 0; i < threads.length; i++) {
					threads[i].start();
				}
				// wait for threads to compute intermediate results
				for (int i = 0; i < threads.length; i++) {
					try {
						threads[i].join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// add product results for each quadrant of the result C
				SquareMatrix[] C = new SquareMatrix[4];
				for (int i = 0; i < C.length; i++) {
					C[i] = SquareMatrixAdd.addParallel(products[2 * i], products[2 * i + 1], super.getMinDim());
				}

				super.getResults()[super.getThreadID()] = new SquareMatrix(C[0], C[1], C[2], C[3]);
			}
		}

		private static ComputeThread[] generateComputeThreads(SquareMatrix A, SquareMatrix B, int minDim,
				SquareMatrix[] results) {
			ComputeThread[] threads = new ComputeThread[] {
					new MulComputeThread(0, A.getQuadrant(1, 1), B.getQuadrant(1, 1), minDim, results),
					new MulComputeThread(1, A.getQuadrant(1, 2), B.getQuadrant(2, 1), minDim, results),
					new MulComputeThread(2, A.getQuadrant(1, 1), B.getQuadrant(1, 2), minDim, results),
					new MulComputeThread(3, A.getQuadrant(1, 2), B.getQuadrant(2, 2), minDim, results),
					new MulComputeThread(4, A.getQuadrant(2, 1), B.getQuadrant(1, 1), minDim, results),
					new MulComputeThread(5, A.getQuadrant(2, 2), B.getQuadrant(2, 1), minDim, results),
					new MulComputeThread(6, A.getQuadrant(2, 1), B.getQuadrant(1, 2), minDim, results),
					new MulComputeThread(7, A.getQuadrant(2, 2), B.getQuadrant(2, 2), minDim, results) };
			return threads;
		}
	}
}
