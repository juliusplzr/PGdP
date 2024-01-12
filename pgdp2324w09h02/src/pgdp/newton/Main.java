package pgdp.newton;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        NewtonApproximator test = new NewtonApproximator(Math::sin, 3);
        System.out.println(test.approximateRoot(0.0000001, 900000));
    }
}
