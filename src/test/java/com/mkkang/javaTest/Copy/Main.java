package com.mkkang.javaTest.Copy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    @Test
    public void aEqualB() {
        int a = 1;
        int b = a;
        b = 2;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    @Test
    public void typeTClone() throws CloneNotSupportedException {
        Human[] origin = new Human[] {new Human(), new Human(), new Human()};
        Human[] copy = new Human[3];
        for(int i=0; i<origin.length; i++) {
            copy[i] = origin[i].clone();
        }
        copy[2].age = 2;
        copy[2].name = "이름2";

        System.out.println("Origin");
        for(Human h : origin) {
            System.out.println(h.age + "," + h.name);
        }


        System.out.println("Copy");
        for(Human h : copy) {
            System.out.println(h.age + "," + h.name);
        }
    }
    @Test
    @DisplayName("clone()을 사용해 Arrays 깊은 복사")
    public void arrCopyTest() {
        int[] origin = new int[]{1,2,3,4};
        int[] copy = origin.clone();

        origin[1] = 100;
        printOriginAndCopy(origin, copy);
    }

    @Test
    @DisplayName("Custom clone을 사용해 이차원 배열 깊은 복사")
    public void twoDimensionArrCopyTest() {
        int[][] origin = new int[3][4];
        int[][] copy = new int[3][4];

        for(int i=0; i< origin.length; i++) {
            copy[i] = origin[i].clone();
        }

        origin[1][2] = 1;
        copy[1][2] = 2;

        printOriginAndCopy(origin, copy);

    }

    @Test
    @DisplayName("생성자를 통해 List 깊은 복사")
    public void listCopyTest() {
        List<Integer> origin = new ArrayList<>(Arrays.asList(1,2,3,4));
        List<Integer> copy = new ArrayList<>(origin);

        origin.set(1, 100);
        printOriginAndCopy(origin, copy);
    }

    public <T> void printOriginAndCopy(List<T> origin, List<T> copy) {
        for(int i=0; i<origin.size(); i++) {
            if(i == 0) {
                System.out.println("origin" + "\t" + "copy");
            }
            System.out.println(origin.get(i) + "\t\t" + copy.get(i));
        }
    }
    public void printOriginAndCopy(int[] origin, int[] copy) {
        for(int i=0; i<origin.length; i++) {
            if(i == 0) {
                System.out.println("origin" + "\t" + "copy");
            }
            System.out.println(origin[i] + "\t\t" + copy[i]);
        }
    }


    private void printOriginAndCopy(int[][] origin, int[][] copy) {
        for(int i=0; i<origin.length; i++) {
            for(int j=0; j<origin[i].length; j++) {
                if(i == 0 && j == 0) {
                    System.out.println("origin" + "\t" + "copy");
                }
                System.out.println(origin[i][j] + "\t\t" + copy[i][j]);
            }
            System.out.println("### EOL");
        }
    }

}

class Human implements Cloneable{
    int age;
    String name;

    public Human() {
        this.age = 0;
        this.name = "이름0";
    }
    @Override
    public Human clone() throws CloneNotSupportedException {
        Human humanClone = (Human) super.clone();
        humanClone.age = this.age;
        humanClone.name = new String(this.name);
        return humanClone;
    }

}
