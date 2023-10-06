package com.mkkang.javaTest.foreachVsStream.utils;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.io.IOException;
import java.util.Arrays;

public class Utils {
    public static void createChart(int[] xData, int[] foreachData, int[] streamData, String title, String type) throws IOException {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("foreach vs stream").xAxisTitle(title).yAxisTitle(type).build();
        chart.addSeries("foreach", xData, foreachData);
        chart.addSeries("stream", xData, streamData);

        BitmapEncoder.saveBitmap(chart, "./"+title+"-chart-"+type, BitmapEncoder.BitmapFormat.PNG);
    }

    public static void createChart(int[] xData, double[] foreachData, double[] streamData, String title, String type) throws IOException {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("foreach vs stream").xAxisTitle(title).yAxisTitle(type).build();
        chart.addSeries("foreach", Arrays.stream(xData).mapToDouble(i -> (double)i).toArray(), foreachData);
        chart.addSeries("stream", Arrays.stream(xData).mapToDouble(i -> (double)i).toArray(), streamData);

        BitmapEncoder.saveBitmap(chart, "./"+title+"-chart-"+type, BitmapEncoder.BitmapFormat.PNG);
    }
}
