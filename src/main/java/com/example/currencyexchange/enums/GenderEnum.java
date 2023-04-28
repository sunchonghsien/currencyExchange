package com.example.currencyexchange.enums;

public enum GenderEnum {
    MALE(0, "帥哥"), FEMALE(1, "美女");
    private int type;
    private String text;

    GenderEnum(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public static String value(int type) {
        String text = "";
        for (GenderEnum l : values()) {
            if (l.type == type) {
                text = l.text;
                break;
            }
        }
        return text;
    }

}
