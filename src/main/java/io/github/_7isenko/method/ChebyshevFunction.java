package io.github._7isenko.method;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 7isenko
 */
public class ChebyshevFunction implements Function {

    private final int kind;

    public ChebyshevFunction(int kind) {
        this.kind = kind;
    }

    @Override
    public double solve(double x) {
        double[] values = new double[kind+1];
        values[0] = 1D;
        values[1] = (x);
        for (int i = 2; i <= kind; i++) {
            values[i] = 2 * x * values[i - 1] - values[i - 2];
        }
        return values[kind];
    }
}
