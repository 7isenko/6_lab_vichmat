package io.github._7isenko;

import io.github._7isenko.method.Function;
import io.github._7isenko.point.Point;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 7isenko
 */
public class GraphBuilder {

    private SwingWrapper<XYChart> currentWrapper;
    private double minX = 0;
    private double maxX = 0;
    private static final double DELTA = 0.1;
    private final ArrayList<Function> savedFunctions = new ArrayList<>();
    private final ArrayList<Color> savedColors = new ArrayList<>();

    public XYChart drawPoints(ArrayList<Point> points) {
        ArrayList<ArrayList<Double>> split = splitPoints(points);
        ArrayList<Double> xp = split.get(0);
        ArrayList<Double> yp = split.get(1);

        XYChart chart = new XYChartBuilder().width(600).height(400).title("Lagrange").xAxisTitle("x").yAxisTitle("y").build();
        chart.addSeries("points", xp, yp).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter).setMarkerColor(Color.GREEN);
        chart.getStyler().setZoomEnabled(true);
        currentWrapper = new SwingWrapper<>(chart);
        currentWrapper.displayChart().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return chart;
    }

    public void addPoint(Point point, XYChart chart) {
        chart.addSeries("your point" + ThreadLocalRandom.current().nextDouble(5), new double[]{point.x}, new double[]{point.y}).setMarkerColor(Color.BLUE).setMarker(SeriesMarkers.CROSS).setShowInLegend(false);

        if (point.x > maxX) {
            double xVal = maxX;
            maxX = point.x + DELTA;
            repaintFunctions(xVal, maxX, chart);
        }
        if (point.x < minX) {
            double xVal = minX;
            minX = point.x - DELTA;
            repaintFunctions(minX, xVal, chart);
        }

        currentWrapper.repaintChart();
    }

    private void repaintFunctions(double from, double to, XYChart chart) {
        for (int i = 0; i < savedFunctions.size(); i++) {
            Function savedFunction = savedFunctions.get(i);
            ArrayList<Double> xGraph = new ArrayList<>();
            ArrayList<Double> yGraph = new ArrayList<>();
            double x = from;
            while (x <= to) {
                double yVal = savedFunction.solve(x);
                xGraph.add(x);
                yGraph.add(yVal);
                x += 0.1;
            }
            if (!xGraph.isEmpty()) {
                chart.addSeries("1123" + ThreadLocalRandom.current().nextDouble(3), xGraph, yGraph).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line).setMarker(SeriesMarkers.NONE).setLineColor(savedColors.get(i)).setShowInLegend(false);
            }
        }
        currentWrapper.repaintChart();
    }

    public void addFunctionToGraph(Function function, String name, ArrayList<Point> points, XYChart chart, boolean changeMaxMin, Color color) {
        savedFunctions.add(function);
        savedColors.add(color);

        ArrayList<ArrayList<Double>> split = splitPoints(points);
        ArrayList<Double> xp = split.get(0);

        if (changeMaxMin) {
            minX = Collections.min(xp) - DELTA;
            maxX = Collections.max(xp) + DELTA;
        }

        createGraph(chart, name, function, minX, maxX, XYSeries.XYSeriesRenderStyle.Line, color);
        currentWrapper.repaintChart();
    }

    private void createGraph(XYChart chart, String name, Function function, double leftBorder, double rightBorder, XYSeries.XYSeriesRenderStyle renderStyle, Color lineColor) {
        if (leftBorder > rightBorder) {
            double tmp = leftBorder;
            leftBorder = rightBorder;
            rightBorder = tmp;
        }

        ArrayList<Double> xGraph = new ArrayList<>();
        ArrayList<Double> yGraph = new ArrayList<>();
        double xVal = leftBorder;
        while (xVal <= rightBorder) {
            double yVal = function.solve(xVal);
            xGraph.add(xVal);
            yGraph.add(yVal);
            xVal += 0.1;
        }

        if (!xGraph.isEmpty()) {
            chart.addSeries(name, xGraph, yGraph).setXYSeriesRenderStyle(renderStyle).setMarker(SeriesMarkers.NONE).setLineColor(lineColor);
        }
        currentWrapper.repaintChart();
    }

    private static ArrayList<ArrayList<Double>> splitPoints(List<io.github._7isenko.point.Point> points) {
        ArrayList<ArrayList<Double>> rez = new ArrayList<>();
        rez.add(new ArrayList<>());
        rez.add(new ArrayList<>());

        for (Point point : points) {
            rez.get(0).add(point.x);
            rez.get(1).add(point.y);
        }
        return rez;
    }
}
