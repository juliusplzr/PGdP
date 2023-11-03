package pgdp.functions;

public class Functions {

    public static int square(int n) {
        int square = n*n;
        return square;
    }

    public static int sumOfSquares(int a, int b) {
        int aSquared = square(a);
        int bSquared = square(b);
        int sum = aSquared + bSquared;

        return sum;
    }

    public static int cube(int n) {
        return n * n * n;
    }

    public static int average(int a, int b, int c) {
        return (a + b + c)/3;
    }

    public static boolean isPythagoreanTriple(int a, int b, int c) {
        return sumOfSquares(a, b) == square(c);
    }
}
