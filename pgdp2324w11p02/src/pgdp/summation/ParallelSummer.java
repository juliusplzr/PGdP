package pgdp.summation;

public class ParallelSummer extends Thread {
    private int[] array;
    private int lowerBound;
    private int upperBound;

    private long result = 0;

    public ParallelSummer(int[] array, int lowerBound, int upperBound) {
        this.array = array;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void run() {
        for(int i = lowerBound; i < upperBound; i++) {
            result += array[i];
        }
    }

    public long getResult() {
        return result;
    }
}
