package pgdp.streams;

import java.util.Arrays;

public class StreamParallelizationExample {

    /*
        Waits 300 milliseconds, then calculates and returns i*2.
     */
    public static int complexCalculation(int i) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i * 2;
    }


    public static void main(String[] args) {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // TODO 5.1 (nothing to change here, just try to experiment with the different stream calls)
        // sequential execution
        streamExecutionSequential(numbers);

        // TODO 5.2 (nothing to change here, just try to experiment with the different stream calls)
        // parallel execution
        //streamExecutionParallel(numbers);

        // TODO 5.3 (nothing to change here, just try to experiment with the different stream calls)
        // parallel execution (keeping order)
        //streamExecutionParallelKeepOrder(numbers);
    }

    public static void streamExecutionSequential(Integer[] numbers) {
        Arrays.stream(numbers)
                .map(StreamParallelizationExample::complexCalculation)
                .forEach(System.out::println);
    }


    public static void streamExecutionParallel(Integer[] numbers) {
        Arrays.stream(numbers)
                .parallel()
                .map(StreamParallelizationExample::complexCalculation)
                .forEach(System.out::println);
    }


    public static void streamExecutionParallelKeepOrder(Integer[] numbers) {
        Arrays.stream(numbers)
                .parallel()
                .map(StreamParallelizationExample::complexCalculation)
                .forEachOrdered(System.out::println);
    }
}
