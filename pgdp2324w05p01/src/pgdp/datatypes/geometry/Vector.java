package pgdp.datatypes.geometry;

public class Vector {
    int x;
    int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
}
