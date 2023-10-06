package com.mkkang.javaTest.foreachVsStream.multiThread;

import org.junit.jupiter.api.Test;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    static int start;
    static int end;
    static double space;

    @Test
    public void 동시접속사_변화() throws InterruptedException, IOException {
        // list 길이는 고정되어있고 동시 접속자 수가 start에서 end 만큼 증가할 때
        start = 1;
        end = 100;
        space = 1;

        int listLength = (int)1E4; // 10,000 만

        int length = (int)Math.ceil((end-start)/space);

        int[] xData = new int[length];

        int[] foreachTime = new int[length];
        int[] foreachMemory = new int[length];

        int[] streamTime = new int[length];
        int[] streamMemory = new int[length];


        int count = 0;
        for(int i=start; i<end; i+=space) {
            xData[count] = i;
            Runtime.getRuntime().gc();
            foreachTime[count] = forEachThreadTest(i, listLength);
            foreachMemory[count] = (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

            Runtime.getRuntime().gc();
            streamTime[count] = streamThreadTest(i, listLength);
            streamMemory[count] = (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            count++;
        }

        // chart return
        createTimeChart(xData, foreachTime, streamTime, "동접자 수");
        createMemChart(xData, foreachMemory, streamMemory, "동접자 수");
    }

    @Test
    public void 리스트길이_변화() throws InterruptedException, IOException {
        // 동시 접속자 수(10명)는 고정되어있고, list 길이만 start에서 end로 변화할 때
        start = (int)1E3;
        end = (int)1E4;
        space = 50;
        int threadNumber = 10;

        int length = (int)Math.ceil((end-start)/space);

        int[] xData = new int[length];

        int[] foreachTime = new int[length];
        int[] foreachMemory = new int[length];

        int[] streamTime = new int[length];
        int[] streamMemory = new int[length];


        int count = 0;
        for(int i=start; i<end; i+=space) {
            xData[count] = i;
            Runtime.getRuntime().gc();
            foreachTime[count] = forEachThreadTest(threadNumber, i);
            foreachMemory[count] = (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

            Runtime.getRuntime().gc();
            streamTime[count] = streamThreadTest(threadNumber, i);
            streamMemory[count] = (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            count++;
        }

        // chart return
        createTimeChart(xData, foreachTime, streamTime, "리스트 길이");
        createMemChart(xData, foreachMemory, streamMemory, "리스트 길이");
    }

    public int forEachThreadTest(int threadNum, int initNum) throws InterruptedException {
        int numberOfThreads = threadNum;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch endSignal = new CountDownLatch(numberOfThreads);
        long startTime, endTime;

        // 스레드에서 실행할 작업을 정의하고 제출
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(() -> {
                try {
                    List<HumanDTO> humanList = new ArrayList<>();
                    init(initNum, humanList);

                    // 시작 시그널 기다리기
                    startSignal.await();

                    // 작업 수행
                    Runtime.getRuntime().gc();
                    forEachTest(humanList);

                    // 작업 완료 시그널 카운트 다운
                    endSignal.countDown();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        startTime = System.currentTimeMillis();
        startSignal.countDown();

        endSignal.await();
        endTime = System.currentTimeMillis();
        // 스레드 풀 종료
        executorService.shutdown();

        return (int)(endTime - startTime);
    }

    public int streamThreadTest(int threadNum, int initNum) throws InterruptedException {
        int numberOfThreads = threadNum;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch endSignal = new CountDownLatch(numberOfThreads);
        long startTime, endTime;

        // 스레드에서 실행할 작업을 정의하고 제출
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(() -> {
                try {
                    List<HumanDTO> humanList = new ArrayList<>();
                    init(initNum, humanList);

                    // 시작 시그널 기다리기
                    startSignal.await();

                    // 작업 수행
                    streamTest(humanList);

                    // 작업 완료 시그널 카운트 다운
                    endSignal.countDown();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        startTime = System.currentTimeMillis();
        startSignal.countDown();

        endSignal.await();
        endTime = System.currentTimeMillis();
        // 스레드 풀 종료
        executorService.shutdown();

        return (int)(endTime - startTime);
    }

    static void init(int N, List<HumanDTO> humanList) {
        humanList = new ArrayList<>();
        for(int i=0; i<N; i++) {
            humanList.add(new HumanDTO(i, "mkkang"+i));
        }
    }

    static void forEachTest(List<HumanDTO> humanList) {
        List<String> nameList = new ArrayList<>();
        for(HumanDTO human : humanList) {
            nameList.add(human.getName());
        }
    }

    static void streamTest(List<HumanDTO> humanList) {
        humanList.stream().map(h -> h.getName()).collect(Collectors.toList());
    }

    static void createTimeChart(int[] xData, int[] foreachData, int[] streamData, String title) throws IOException {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("foreach vs stream").xAxisTitle(title).yAxisTitle("time").build();
        chart.addSeries("foreach", xData, foreachData);
        chart.addSeries("stream", xData, streamData);

        BitmapEncoder.saveBitmap(chart, "./"+title+"chart-time-"+start+"-to-"+end+"-with-"+space, BitmapEncoder.BitmapFormat.PNG);
    }
    static void createMemChart(int[] xData, int[] foreachData, int[] streamData, String title) throws IOException {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("foreach vs stream").xAxisTitle(title).yAxisTitle("memory").build();
        chart.addSeries("foreach", xData, foreachData);
        chart.addSeries("stream", xData, streamData);

        BitmapEncoder.saveBitmap(chart, "./"+title+"chart-memory-"+start+"-to-"+end+"-with-"+space, BitmapEncoder.BitmapFormat.PNG);
    }
}
