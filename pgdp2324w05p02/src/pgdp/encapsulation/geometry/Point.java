package pgdp.encapsulation.geometry;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getDistanceToOrigin() {
        return Math.sqrt(x*x + y*y);
    }
}
