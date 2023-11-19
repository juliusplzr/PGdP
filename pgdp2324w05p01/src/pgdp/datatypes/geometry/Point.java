package pgdp.datatypes.geometry;

public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getDistanceToOrigin() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector getVectorTo(Point other) {
        return new Vector(other.x - this.x, other.y - this.y);
    }
}
