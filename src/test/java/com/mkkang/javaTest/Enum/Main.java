package com.mkkang.javaTest.Enum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Main {
    @Test
    public void nameTest() {
        Color red = Color.RED;

        Assertions.assertEquals(red.name(), "RED");
    }
    @Test
    public void getCodeTest() {
        Color red = Color.RED;
        String code = red.getCode();

        Assertions.assertEquals(code, "R");
    }
    @Test
    public void valueOfTest() {
        String name = "AQUAMARINE";
        Color color = Color.valueOf(name);

        Assertions.assertEquals(color, Color.AQUAMARINE);
    }
}
