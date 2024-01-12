package pgdp.newton;

import java.util.function.Function;

import static pgdp.newton.Polynoms.polynom;

public class NewtonApproximator {

    private Function<Double, Double> f;
    private Function<Double, Double> fPrime;
    private double x;
    private int numberOfIterations;

    public NewtonApproximator(Function<Double, Double> f, Function<Double, Double> fPrime, double x0) {
        this.f = f;
        this.fPrime = fPrime;
        this.x = x0;
        this.numberOfIterations = 0;
    }

    public NewtonApproximator(Function<Double, Double> f, double x0) {
        this(f, PinguMaths.derivativeFunction(f, 0.0000001), x0);
    }

    public void nextStep() {
        x = x - f.apply(x) / fPrime.apply(x);
        numberOfIterations++;
    }

    public double approximateRoot(double epsilon, int maxIterations) {
        while (numberOfIterations < maxIterations && Math.abs(f.apply(x)) > epsilon) {
            nextStep();
        }

        return x;
    }
}
