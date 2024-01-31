package pgdp.exceptions;

public class NotEnoughException extends Exception {
    private final int is;
    private final int should;

    public NotEnoughException(int should, int is) {
        if (is >= should) {
            throw new IllegalArgumentException();
        }

        this.should = should;
        this.is = is;
    }

    @Override
    public String toString() {
        return "Should be: " + should + "but is: " + is + "!";
    }
}
