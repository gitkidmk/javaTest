package com.mkkang.javaTest.Enum;

public enum Color {
    RED("R"),
    YELLOW("Y"),
    BLUE("B"),
    AQUAMARINE("A");

    // RED와 같이 상수와 매핑할 또 다른 상수 "R"을 정의하기 위해 String code 사용
    String code;
    // 생성자와 유사한 역할을 한다.
    Color(String code) {
        this.code = code;
    }
    // DB에는 RED와 같은 상수가 아닌, R과 같은 code 값이 들어가기 때문에 getCode 메소드 생성
    public String getCode() {
        return this.code;
    }
}
