package pgdp.encapsulation.geometry;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDistanceToOrigin() {
        return Math.sqrt(x*x + y*y);
    }
}
