package funcint;

import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        Function<Double, Double> func = x -> x * x;

        double start = 1;
        double end = 9;
        double step = 1;

        showTableWithExceptionHandling(start, end, step, func);
    }

    public static void showTableWithExceptionHandling(double start, double end, double step, Function<Double, Double> func) {
        try {
            showTable(start, end, step, func);
        } catch (InvalidRangeException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void showTable(double start, double end, double step, Function<Double, Double> func) throws InvalidRangeException {
        if (!isValidRange(start, end, step)) {
            throw new InvalidRangeException(start, end, step);
        }

        int i = 0;
        double next = start;

        while (next < end) {
            System.out.println(next + "\t" + func.apply(next));
            i++;
            next = start + i * step;
        }

    }

    protected static boolean isValidRange(double start, double end, double step) {
        if (step == 0) {
            return false;
        }

        if (start <= end && step > 0) {
            return true;
        }

        return start >= end && step < 0;
    }
}
