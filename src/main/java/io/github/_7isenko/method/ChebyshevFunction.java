package io.github._7isenko.method;

/**
 * @author 7isenko
 */
public class ChebyshevFunction implements Function {

    private final int power;

    public ChebyshevFunction(int power) {
        this.power = power;
    }

    @Override
    public double solve(double x) {
        double[] values = new double[power +1];
        values[0] = 1D;
        values[1] = (x);
        for (int i = 2; i <= power; i++) {
            values[i] = 2 * x * values[i - 1] - values[i - 2];
        }
        return values[power];
    }
}
