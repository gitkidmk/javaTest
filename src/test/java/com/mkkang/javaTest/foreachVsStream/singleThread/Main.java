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

import static com.mkkang.javaTest.foreachVsStream.utils.Utils.createChart;

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

    @Test
    public void test() throws IOException{
        /**
        * 주석 조건과 같이 multi thread 테스트의 리스트 길이 변화와 같은 환경에서 테스트하게되면 둘 다 시간은 0 ~ 1ms 소요
        * 따라서 100,000(십만) 이상의 리스트 길이로 가야 stream이 시간에서 이득을 본다.
        *
        *        start = (int)1E4;
        *        end = (int)1E5;
        *        space = (int)1E3;
        */

        start = (int)1E5;
        end = (int)1E6;
        space = (int)1E5;

        List<Integer> nList = new ArrayList<>();
        for(int i=start; i<=end; i=i+space) {
            nList.add(i);
        }

        int[] nArr = nList.stream().mapToInt(Integer::intValue).toArray();
        double[] forEachTimeArr = new double[nArr.length];
        double[] streamTimeArr = new double[nArr.length];
        int[] forEachMemArr = new int[nArr.length];
        int[] streamMemArr = new int[nArr.length];

        for(int i=0; i<nArr.length; i++) {
            init(nArr[i]);

            Runtime.getRuntime().gc();
            forEachTimeArr[i] = forEachTest();
            forEachMemArr[i] = (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

            Runtime.getRuntime().gc();
            streamTimeArr[i] = streamTest();
            streamMemArr[i] = (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

        }

        createChart(nArr, forEachTimeArr, streamTimeArr, "single-thread-리스트 길이", "time");
        createChart(nArr, forEachMemArr, streamMemArr, "single-thread-리스트 길이", "memory");

    }


    static void init(int N) {
        humanList = new ArrayList<>();
        for(int i=0; i<N; i++) {
            humanList.add(new HumanDTO(i, "mkkang"+i));
        }
    }

    static double forEachTest() {
        beforeTime = System.currentTimeMillis();

        List<String> nameList = new ArrayList<>();
        for(HumanDTO human : humanList) {
            nameList.add(human.getName());
        }

        afterTime = System.currentTimeMillis();
        System.out.println("afterTime = " + afterTime);
        System.out.println("beforeTime = " + beforeTime);
        return afterTime - beforeTime;

    }

    static double streamTest() {
        beforeTime = System.currentTimeMillis();

        humanList.stream().map(h -> h.getName()).collect(Collectors.toList());

        afterTime = System.currentTimeMillis();
        return afterTime - beforeTime;
    }

}
