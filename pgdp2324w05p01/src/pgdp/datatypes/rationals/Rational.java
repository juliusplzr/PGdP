package pgdp.datatypes.rationals;

// Hier sollen neue Klassen erstellt werden.

public class Rational {

    int numerator;
    int denominator;

    public Rational (int numerator, int denominator) {
        this.numerator = numerator;

        if (denominator != 0) {
            this.denominator = denominator;
        } else {
            System.out.println("A Rational-Object with 'denominator' zero was created. Do NOT use!");
        }
    }

    public void multiplyBy(Rational other) {
        this.numerator *= other.numerator;
        this.denominator *= other.denominator;
    }

    public void divideBy(Rational other) {
        this.numerator *= other.denominator;
        this.denominator *= other.numerator;
    }

    public void add(Rational other) {
        this.numerator = this.numerator * other.denominator + this.denominator * other.numerator;
        this.denominator *= other.denominator;
    }

    public void subtract(Rational other) {
        this.numerator = this.numerator * other.denominator - this.denominator * other.numerator;
        this.denominator *= other.denominator;
    }

    public double toDouble() {
        return (double) this.numerator / this.denominator;
    }

    public String toString() {
        return this.numerator + " / " + this.denominator;
    }
}
