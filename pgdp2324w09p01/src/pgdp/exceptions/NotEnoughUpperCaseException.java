package pgdp.exceptions;

public class NotEnoughUpperCaseException extends NotEnoughLetterException {
    public NotEnoughUpperCaseException(int should, int is) {
        super(should, is);
    }

    @Override
    public String toString() {
        return "NotEnoughUpperCaseException: " + super.toString();
    }
}
