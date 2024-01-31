package pgdp.exceptions;

public class NotEnoughLowerCaseException extends NotEnoughLetterException {
    public NotEnoughLowerCaseException(int should, int is) {
        super(should, is);
    }

    public String toString() {
        return "NotEnoughLowerCaseException: " + super.toString();
    }
}
