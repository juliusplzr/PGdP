package pgdp.newton;

import java.util.function.Function;

public class Polynoms {

    public static double power(double base, int exp) {
        if (exp == 0) {
            return 1;
        }

        return base * power(base, exp - 1);
    }

    public static Function<Double, Double> polynom(double[] coefficients) {
        return input -> {
            double res = 0;

            for (int i = 0; i < coefficients.length; i++) {
                res += coefficients[i] * power(input, i);
            }

            return res;
        };
    }

    public static Function<Double, Double> polynomDerivative(double[] coefficients) {
        return input -> {
            double res = 0;

            for (int i = 1; i < coefficients.length; i++) {
                res += i * coefficients[i] * power(input, i - 1);
            }

            return res;
        };
    }
}
