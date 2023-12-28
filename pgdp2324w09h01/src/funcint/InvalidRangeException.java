package funcint;

public class InvalidRangeException extends Exception {

    protected double start;
    protected double end;
    protected double step;
    public InvalidRangeException(double start, double end, double step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    public String toString() {
        return "Invalid range " + "(" + start + ", " + end + ", " + step + ")";
    }
}
