package pgdp.pingulib.math.matrix;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Maximilian Anzinger
 */

public class SquareMatrixAdd {

	private static final int MIN_DIM = 1 << 1;

	/**
	 * Sequential matrix addition of A and B
	 * 
	 * @param A matrix
	 * @param B matrix
	 * @return result of the matrix addition in a new matrix
	 */
	public static SquareMatrix addSequential(SquareMatrix A, SquareMatrix B) {
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
			for (int j = 1; j <= A.getDimension(); j++) {
				C.set(i, j, A.get(i, j).add(B.get(i, j)));
			}
		}
		return C;
	}

	/**
	 * Parallel matrix addition of A and B. If the dimensions are smaller or equal
	 * than the default value <MIN_DIM>, the sequential matrix addition will be
	 * applied.
	 * 
	 * @param A matrix
	 * @param B matrix
	 * @return result of the matrix addition in a new matrix
	 */
	public static SquareMatrix addParallel(SquareMatrix A, SquareMatrix B) {
		return addParallel(A, B, MIN_DIM);
	}

	/**
	 * Parallel matrix addition of A and B. If <minDim> is smaller than the default
	 * value <MIN_DIM>, the default value will be used. If the dimensions are
	 * smaller or equal than <minDim>, the sequential matrix addition will be
	 * applied.
	 * 
	 * @param A      matrix
	 * @param B      matrix
	 * @param minDim dimension
	 * @return result of the matrix addition in a new matrix
	 */
	public static SquareMatrix addParallel(SquareMatrix A, SquareMatrix B, int minDim) {
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
			return addSequential(A, B);
		}

		SquareMatrix[] result = new SquareMatrix[1];
		AddComputeThread thread = new AddComputeThread(0, A, B, minDim, result);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result[0];
	}

	private static class AddComputeThread extends ComputeThread {

		private static AtomicInteger computeThreadCounter = new AtomicInteger(0);

		/**
		 * initialize compute thread for matrix addition.
		 * 
		 * @param threadID index where result will be stored
		 * @param A        first matrix
		 * @param B        second matrix
		 * @param minDim   threshold for sequential computation
		 * @param results  array reference where the result will be stored
		 */
		AddComputeThread(int threadID, SquareMatrix A, SquareMatrix B, int minDim, SquareMatrix[] results) {
			super(threadID, A, B, minDim, results);
			this.setName("AddComputeThread-" + computeThreadCounter.incrementAndGet());
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
				super.getResults()[super.getThreadID()] = addSequential(super.getA(), super.getB());
			} else { // revursively generate new threads
				SquareMatrix[] C = new SquareMatrix[4];
				AddComputeThread[] threads = new AddComputeThread[] {
						new AddComputeThread(0, super.getA().getQuadrant(1, 1), super.getB().getQuadrant(1, 1),
								super.getMinDim(), C),
						new AddComputeThread(1, super.getA().getQuadrant(1, 2), super.getB().getQuadrant(1, 2),
								super.getMinDim(), C),
						new AddComputeThread(2, super.getA().getQuadrant(2, 1), super.getB().getQuadrant(2, 1),
								super.getMinDim(), C),
						new AddComputeThread(3, super.getA().getQuadrant(2, 2), super.getB().getQuadrant(2, 2),
								super.getMinDim(), C) };

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

				// combine intermediate results to final result
				super.getResults()[super.getThreadID()] = new SquareMatrix(C[0], C[1], C[2], C[3]);
			}
		}
	}
}
