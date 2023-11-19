package pgdp.encapsulation.geometry;

public class Rectangle {
    private Point bottomLeftCorner;

    private Point topRightCorner;

    public Rectangle(Point bottomLeftCorner, int width, int height) {
        this.bottomLeftCorner = bottomLeftCorner;
        this.topRightCorner = new Point(bottomLeftCorner.getX() + width, bottomLeftCorner.getY() + height);
    }

    public Point getBottomLeftCorner() {
        return bottomLeftCorner;
    }

    public int getWidth() {
        return topRightCorner.getX() - bottomLeftCorner.getX();
    }

    public int getHeight() {
        return topRightCorner.getY() - bottomLeftCorner.getY();
    }

    public int getCircumference() {
        return 2 * getWidth() + 2 * getHeight();
    }

    public int getArea() {
        return getWidth() * getHeight();
    }
}
