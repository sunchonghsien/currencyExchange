package com.example.currencyexchange.helper;

public class Constant {
    public final static boolean OPEN = true;
    public final static boolean CLOSE = false;
    public final static Integer PAGE_LIMIT20 = 20;

    public static class ISO_4217 {
        public final static String USD = "USD";
        public final static String TWD = "TWD";
        public final static String CNY = "CNY";
        public final static String JPY = "JPY";
        public final static String HKD = "HKD";
    }

    public static class DATE {
        public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    }

    public static class REDIS_KEY {
        public final static String BANKOFTAIWAN = "BankOfTaiwan";
    }

    public static class RESPONSE_CODE {
        public final static int ok = 200;
    }

    public static class RESPONSE_MSG {
        public final static String ok = "请求成功";
        public final static String error = "请求失敗";
    }
}
