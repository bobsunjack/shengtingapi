package com.example.shengtingapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    public final static SimpleDateFormat sdfDay = new SimpleDateFormat(
            "yyyy-MM-dd");

    public final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    public final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static Long getTimeByDay(String timeStr) throws ParseException {
        SimpleDateFormat formate = new SimpleDateFormat(
                "yyyy-MM-dd");
        return formate.parse(timeStr).getTime();
    }

    public static Long getTimeByTime(String timeStr) throws ParseException {
        SimpleDateFormat formate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return formate.parse(timeStr).getTime();
    }

    public static String convertToTZTime(String normalTime){
        return normalTime.replace(" ", "T") + ".021Z";

    }
}
