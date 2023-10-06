package com.mkkang.javaTest.foreachVsStream.singleThread;

import org.junit.jupiter.api.Test;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class HumanDTO {
    int id;
    String name;

    String getName() {
        return this.name;
    }

    HumanDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

@SpringBootTest
public class Main {

    static List<HumanDTO> humanList = new ArrayList<>();
    static long beforeTime;
    static long afterTime;

    static int start;
    static int end;
    static int space;


    static void init(int N) {
        humanList = new ArrayList<>();
        for(int i=0; i<N; i++) {
            humanList.add(new HumanDTO(i, "mkkang"+i));
        }
    }

    static int forEachTest() {
        beforeTime = System.currentTimeMillis();

        List<String> nameList = new ArrayList<>();
        for(HumanDTO human : humanList) {
            nameList.add(human.getName());
        }

        afterTime = System.currentTimeMillis();
        return (int)(afterTime - beforeTime);

    }

    static int streamTest() {
        beforeTime = System.currentTimeMillis();

        humanList.stream().map(h -> h.getName()).collect(Collectors.toList());

        afterTime = System.currentTimeMillis();
        return (int)(afterTime - beforeTime);
    }

    static void createTimeChart(int[] xData, int[] foreachData, int[] streamData) throws IOException {
//        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title("foreach vs stream").xAxisTitle("N").yAxisTitle("time").build();
        chart.addSeries("foreach", xData, foreachData);
        chart.addSeries("stream", xData, streamData);
//        // Show it
        BitmapEncoder.saveBitmap(chart, "./chart-"+start+"-to-"+end+"-with-"+space, BitmapEncoder.BitmapFormat.PNG);
    }
    static void createMemChart(int[] xData, long[] foreachData, long[] streamData) throws IOException {
//        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title("foreach vs stream").xAxisTitle("N").yAxisTitle("time").build();
        chart.addSeries("foreach", xData, Arrays.stream(foreachData).mapToInt(i -> (int)i).toArray());
        chart.addSeries("stream", xData, Arrays.stream(streamData).mapToInt(i -> (int)i).toArray());
//        // Show it
        BitmapEncoder.saveBitmap(chart, "./chart-mem-"+start+"-to-"+end+"-with-"+space, BitmapEncoder.BitmapFormat.PNG);
    }

    @Test
    public void test() throws IOException{
        start = (int)10E3;
        end = (int)10E4;
        space = (int)10E2;

        System.out.println("start = " + start);
        System.out.println("end = " + end);
        System.out.println("space = " + space);

        List<Integer> nList = new ArrayList<>();
        for(int i=start; i<=end; i=i+space) {
            nList.add(i);
        }

        int[] nArr = nList.stream().mapToInt(Integer::intValue).toArray();
        int[] forEachTimeArr = new int[nArr.length];
        int[] streamTimeArr = new int[nArr.length];
        long[] forEachMemArr = new long[nArr.length];
        long[] streamMemArr = new long[nArr.length];

        for(int i=0; i<nArr.length; i++) {
            init(nArr[i]);

            Runtime.getRuntime().gc();
            forEachTimeArr[i] = forEachTest();
            forEachMemArr[i] = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            Runtime.getRuntime().gc();
            streamTimeArr[i] = streamTest();
            streamMemArr[i] = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        }

        createTimeChart(nArr, forEachTimeArr, streamTimeArr);
        createMemChart(nArr, forEachMemArr, streamMemArr);

    }
}
