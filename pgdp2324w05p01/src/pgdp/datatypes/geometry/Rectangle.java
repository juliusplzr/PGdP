package pgdp.datatypes.geometry;

public class Rectangle {
    Point bottomLeftCorner;
    int width;
    int height;

    public Rectangle(Point bottomLeftCorner, int width, int height) {
        this.bottomLeftCorner = bottomLeftCorner;
        this.width = width;
        this.height = height;
    }

    public int getCircumference() {
        return 2 * this.width + 2 * this.height;
    }

    public int getArea() {
        return this.width * this.height;
    }
}
