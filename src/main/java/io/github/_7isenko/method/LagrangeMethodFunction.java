package io.github._7isenko.method;

import io.github._7isenko.point.Point;

import java.util.ArrayList;

/**
 * @author 7isenko
 */
public class LagrangeMethodFunction implements Function {

    private final ArrayList<Point> points;
    protected final int size;

    public LagrangeMethodFunction(ArrayList<Point> points) {
        this.points = points;
        this.size = points.size();
    }

    public double solve(double x) {
        double y = 0;
        for (Point i : points) {
            double l = 1;
            for (Point j : points) {
                if (i != j) {
                    l *= ((x - j.x) / (i.x - j.x));
                }
            }
            y += l * i.y;
        }
        return y;
    }

}
