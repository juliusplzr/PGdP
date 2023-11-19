package pgdp.datatypes.geometry;

public class Circle {
    Point center;
    int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public double getCircumference() {
        return 2 * Math.PI * this.radius;
    }

    public double getArea() {
        return Math.PI * this.radius * this.radius;
    }
}
