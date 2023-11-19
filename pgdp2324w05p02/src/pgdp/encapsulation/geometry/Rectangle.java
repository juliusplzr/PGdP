package pgdp.encapsulation.geometry;

public class Rectangle {
    public Point bottomLeftCorner;
    public int width;
    public int height;
    public Point topRightCorner;

    public Rectangle(Point bottomLeftCorner, int width, int height) {
        this.bottomLeftCorner = bottomLeftCorner;
        this.width = width;
        this.height = height;
        this.topRightCorner = new Point(bottomLeftCorner.x + width, bottomLeftCorner.y + height);

    }

    public int getCircumference() {
        int width = topRightCorner.x - bottomLeftCorner.x;
        int height = topRightCorner.y - bottomLeftCorner.y;
        return 2 * width + 2 * height;
    }

    public int getArea() {
        int width = topRightCorner.x - bottomLeftCorner.x;
        int height = topRightCorner.y - bottomLeftCorner.y;
        return width * height;
    }
}
