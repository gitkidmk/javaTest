package com.mkkang.javaTest.HigherOrderFunction;

/*
* 고차함수 구현
* */

import java.util.Arrays;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        higherOrderFunctionEx1((intArr) -> Arrays.stream(intArr).mapToLong(i -> Long.valueOf((long) (i*Math.pow(10,10)))).toArray());

        higherOrderFunctionEx2((Void) -> generalMethod(new int[] {1,2,3,4,5}));

        higherOrderFunctionEx3(intArr -> Arrays.stream(intArr).mapToLong(i -> Long.valueOf((long) (i*Math.pow(10,10)))).toArray());
    }
    static void higherOrderFunctionEx1(Function<int[], long[]> lambdaFunc) {
        System.out.println("START");

        long[] result = lambdaFunc.apply(new int[] {1, 2, 3, 4, 5});
        Arrays.stream(result).forEach(System.out::println);

        System.out.println("END");
    }

    static void higherOrderFunctionEx2(Function<Void, long[]> lambdaFunc) {
        System.out.println("START");

        long[] result = lambdaFunc.apply(null);
        Arrays.stream(result).forEach(System.out::println);

        System.out.println("END");
    }
    static void higherOrderFunctionEx3(MyLambdaFunc myLambdaFunc) {
        System.out.println("START");

        long[] result = myLambdaFunc.toLongArr(new int[]{1, 2, 3, 4, 5});
        Arrays.stream(result).forEach(System.out::println);

        System.out.println("END");
    }
    static long[] generalMethod(int[] arr) {
        return Arrays.stream(arr).mapToLong(i -> Long.valueOf((long)(i*Math.pow(10,10)))).toArray();
    }
}

@FunctionalInterface
interface MyLambdaFunc {
    long[] toLongArr(int[] intArr);
}