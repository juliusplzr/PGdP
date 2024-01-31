package pgdp.pingulib.math.matrix;

public abstract class ComputeThread extends Thread {

	private final int threadID;
	private final int minDim;
	private SquareMatrix A;
	private SquareMatrix B;
	private SquareMatrix[] results;

	/**
	 * initialize compute thread for matrix operation.
	 * 
	 * @param threadID index where result will be stored
	 * @param A        first matrix
	 * @param B        second matrix
	 * @param minDim   threshold for sequential computation
	 * @param results  array reference where the result will be stored
	 */
	public ComputeThread(int threadID, SquareMatrix A, SquareMatrix B, int minDim, SquareMatrix[] results) {
		this.threadID = threadID;
		this.minDim = minDim;
		this.A = A;
		this.B = B;
		this.results = results;
	}

	protected SquareMatrix getA() {
		return A;
	}

	protected void setA(SquareMatrix a) {
		A = a;
	}

	protected SquareMatrix getB() {
		return B;
	}

	protected void setB(SquareMatrix b) {
		B = b;
	}

	protected SquareMatrix[] getResults() {
		return results;
	}

	protected void setResults(SquareMatrix[] results) {
		this.results = results;
	}

	protected int getThreadID() {
		return threadID;
	}

	protected int getMinDim() {
		return minDim;
	}
}
