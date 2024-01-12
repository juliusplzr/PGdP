package pgdp.newton;

import java.util.function.Function;

public class PinguMaths {
    public static Function<Double, Double> derivativeFunction(Function<Double, Double> f, double epsilon) {
        return x -> (f.apply(x + epsilon) - f.apply(x)) / epsilon;
    }
}
