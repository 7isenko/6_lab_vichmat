package io.github._7isenko;

import io.github._7isenko.method.ChebyshevFunction;
import io.github._7isenko.method.LagrangeMethodFunction;
import io.github._7isenko.point.Point;
import org.knowm.xchart.XYChart;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author 7isenko
 */
public class Main {

    private static final InputReader inputReader = new InputReader();
    private static final GraphBuilder graphBuilder = new GraphBuilder();

    public static void main(String[] args) {
        //  blockErrorStream();

        System.out.print("Введите степень полинома Чебышева: ");
        ChebyshevFunction chebyshevFunction = new ChebyshevFunction(inputReader.readIntFromConsole());

        System.out.print("Введите левую границу: ");
        double xl = inputReader.readDoubleFromConsole();
        System.out.print("Введите правую границу: ");
        double xr = inputReader.readDoubleFromConsole();
        System.out.print("Введите шаг: ");
        double h = inputReader.readDoubleFromConsole();

        ArrayList<Point> points = new ArrayList<>();

        for (double i = xl; i <= xr; i += h) {
            points.add(new Point(i, chebyshevFunction.solve(i)));
        }

        LagrangeMethodFunction lagrangeMethodFunction = new LagrangeMethodFunction(points);

        XYChart chart = graphBuilder.drawPoints(points);
        graphBuilder.addFunctionToGraph(lagrangeMethodFunction, "Lagrange", points, chart, true, Color.ORANGE);
        graphBuilder.addFunctionToGraph(chebyshevFunction, "Chebyshev", points, chart, false, Color.RED);

        System.out.println("Чтобы узнать значение в любой точке, введите x:");
        while (true) {
            System.out.print("x = ");
            double x = inputReader.readDoubleFromConsole();
            double y = lagrangeMethodFunction.solve(x);
            System.out.println("y = " + y);
            System.out.println("Реальное значение y = " + chebyshevFunction.solve(x));
            graphBuilder.addPoint(new Point(x, y), chart);

        }
    }

    private static void blockErrorStream() {
        try {
            System.setErr(new PrintStream("errors"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
