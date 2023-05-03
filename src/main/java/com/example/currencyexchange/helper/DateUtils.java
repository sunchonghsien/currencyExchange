package com.example.currencyexchange.helper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

@Component
public class DateUtils {
    public static ZoneId SHANGHAI = ZoneId.of("Asia/Shanghai");

    public static long getTimestampToMillis() {
        ZonedDateTime dateTime = newZonedDateTimeOf(newLocalDateTime(Calendar.getInstance().getTimeInMillis()));
        return dateTime.toInstant().toEpochMilli();
    }

    public static long strToTimestamp(String time) throws ParseException {
        return new SimpleDateFormat(Constant.DATE.YYYY_MM_DD_HH_MM_SS).parse(time).getTime();
    }

    public static String dateFormatByNowToY_M_D_H_M_S() {
        return timestampFormat(getTimestampToMillis(), Constant.DATE.YYYY_MM_DD_HH_MM_SS);
    }


    public static String timestampFormat(long timestamp, String format) {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(format);
        LocalDateTime local = newLocalDateTime(timestamp);
        ZonedDateTime zonedDateTime = newZonedDateTimeOf(local);
        return zonedDateTime.format(ofPattern);
    }
    public static String timestampToY_M_D_H_M_S(long timestamp) {
        return timestampFormat(timestamp,Constant.DATE.YYYY_MM_DD_HH_MM_SS);
    }

    public static Timestamp timestamp(long timestamp) {
        ZonedDateTime dateTime = newZonedDateTimeOf(newLocalDateTime(timestamp));
        return Timestamp.valueOf(dateTime.toLocalDateTime());
    }

    public static Timestamp tampByNow() {
        ZonedDateTime dateTime = newZonedDateTimeOf(LocalDateTime.now());
        return Timestamp.valueOf(dateTime.toLocalDateTime());
    }

    private static ZonedDateTime newZonedDateTimeOf(LocalDateTime local) {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(local, SHANGHAI);
        return zonedDateTime;
    }

    private static LocalDateTime newLocalDateTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return LocalDateTime.of(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DATE),
                cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND),
                cal.get(Calendar.MILLISECOND)
        );
    }


}
