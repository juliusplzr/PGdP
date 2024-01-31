package pgdp.exceptions;

public class IllegalCharException extends Exception {
    private final char used;

    public IllegalCharException(char c) {
        this.used = c;
    }

    private String escapeSpecial(char used) {
        return switch (used) {
            case '\n' -> "line break (\\n)";
            case '\t' -> "tab (\\t)";
            case '\r' -> "carriage return (\\r)";
            case ' ' -> "whitespace";
            case '\b' -> "backspace (\\b)";
            case '\f' -> "formfeed (\\f)";
            default -> Character.toString(used);
        };
    }

    @Override
    public String toString() {
        return "Use of character: "
                + escapeSpecial(used) + " is prohibited!";
    }

}
