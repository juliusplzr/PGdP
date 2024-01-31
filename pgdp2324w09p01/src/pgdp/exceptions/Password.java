package pgdp.exceptions;

public class Password {
    private final int nrUpperShould, nrLowerShould, lengthShould;
    private final char[] illegalChars;

    private static boolean matchesIllegalCharacter(
            char[] illegalChars,
            char c
    ) {
        for (char illegalChar : illegalChars)
            if (c == illegalChar) {
                return true;
            }
        return false;
    }

    public Password(
            int nrUpperShould, int nrLowerShould,
            int lengthShould, char[] illegalChars
    ) {
        this.nrUpperShould = nrUpperShould;
        this.nrLowerShould = nrLowerShould;
        this.lengthShould = lengthShould;
        this.illegalChars = illegalChars;
    }

    public void checkFormat(String pwd)
            throws IllegalCharException, NotEnoughException {
        int nrUpperIs = 0;
        int nrLowerIs = 0;
        int lengthIs = pwd.length();

        if (pwd.length() < lengthShould) {
            throw new NotLongEnoughException(lengthShould, lengthIs);
        }

        for (int i = 0; i < lengthIs; i++) {
            // illegal character
            if (matchesIllegalCharacter(illegalChars, pwd.charAt(i))) {
                throw new IllegalCharException(pwd.charAt(i));
            }
            // lower case
            else if (pwd.charAt(i) >= 'a' && pwd.charAt(i) <= 'z') {
                nrLowerIs++;
            }
            // upper case
            else if (pwd.charAt(i) >= 'A' && pwd.charAt(i) <= 'Z') {
                nrUpperIs++;
            }
        }

        if (nrUpperIs < nrUpperShould) {
            throw new NotEnoughUpperCaseException(nrUpperShould, nrUpperIs);
        }
        if (nrLowerIs < nrLowerShould) {
            throw new NotEnoughLowerCaseException(nrLowerShould, nrLowerIs);
        }
    }

    public void checkFormatWithLogging(String pwd) throws NotEnoughException, IllegalCharException {
        try {
            checkFormat(pwd);
        } catch (NotEnoughException | IllegalCharException e) {
            System.out.println(e);
            throw e;
        }
    }
}