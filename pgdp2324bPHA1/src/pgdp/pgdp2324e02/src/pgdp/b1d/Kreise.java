package pgdp.b1d;

public class Kreise {
    /**
     * Checks whether the point (x, y) is on or inside the circle with radius r
     * @param r Radius of the circle
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return if the point is on or inside the circle
     */
    public static boolean isOnOrInsideCircle(int r, int x, int y) {
        return x * x + y * y <= r * r;
    }

    /**
     * Checks whether the point (x, y) is in the red area
     * Rea area is defined as the area inside the circle with radius 200
     * and outside the squares with the following corners:
     * Square 1: (0,0) (0, 100), (100,0), (100, 100)
     * Square 2: (-100, -100), (-100, 0), (0, -100), (0, 0)
     * Sides of the squares and the circle are included in the red area
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return if the point in the red area
     */
    public static boolean isInRedArea(int x, int y) {
        if (!isOnOrInsideCircle(200, x, y)) {
            return false;
        } else if ((x >= 0 && y >= 0) && (max(x, y) <= 100)) {
            return false;
        } else if (((x <= 0) && (y <= 0)) && (min(x, y) >= -100)) {
            return false;
        } else {
            return true;
        }
    }

    // Helper
    private static int max(int num1, int num2) {
        return (num1 >= num2) ? num1 : num2;
    }

    // Helper
    private static int min(int num1, int num2) {
        return (num1 >= num2) ? num2 : num1;
    }

    /**
     * Counts the number of points with integer coordinates in the circle with n
     * @param n Radius of the circle
     * @return Number of points in or on the circle
     */
    public static int countPointsOnOrInsideCircle(int n) {
        int redPointsCount = 0;

        for (int x = -n; x <= n; x++) {
            for (int y = -n; y <= n; y++) {
                if (isOnOrInsideCircle(n, x,y)) {
                    redPointsCount++;
                }
            }
        }

        return redPointsCount;
    }

    /**
     * Approximates pi by counting the number of points with integer coordinates in the circle with radius n
     * and dividing it by the number of points in smallest enclosing square and multiplying by 4
     * @param n Radius of the circle
     * @return Approximation of pi
     */
    public static double approximatePi(int n) {
        double dots = (2 * n + 1) * (2 * n + 1);

        return (countPointsOnOrInsideCircle(n) / dots) * 4;
    }

    public static void main(String[] args) {
        System.out.println(approximatePi( 800));
    }
}
