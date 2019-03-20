package com.example.shengtingapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static String convertToTZTime(String normalTime) {
        return normalTime.replace(" ", "T") + ".021Z";

    }


    public static String getQtimeStrByDiffDay(int diffDay) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        //  SimpleDateFormat formatDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + diffDay);
        return formatDate.format(c.getTime());
    }

    public static String getQtimeStrByMonthLastDay(int month) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, month + 1);
        cal_1.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        return formatDate.format(cal_1.getTime());
    }

    public static HashSet<String> getListTime(String type, int range) throws ParseException {
        HashSet<String> datas = new LinkedHashSet<>();
        range = -range;
        if ("month".equals(type)) {
            for (int index = range; index < 0;index++) {
                datas.add(getQtimeStrByMonthLastDay(index));
            }

        } else if ("week".equals(type)) {
            range++;
            for (int index = range; index <= 0;index++) {
                int calcWeek = (7 * index )- 1;
                datas.add(getQtimeStrByDiffDay(calcWeek));
            }

        } else if ("day".equals(type)) {
            for (int index = range; index < 0;index++) {
                datas.add(getQtimeStrByDiffDay(index));
            }
        }
        return datas;
    }

    public static List<String> getMonthDays(String month) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date=formatDate.parse(month);
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        //Date nowDate =formatDate2.parse(formatDate2.format(cal_1.getTime()));
        cal_1.setTime(date);
        cal_1.set(Calendar.DAY_OF_MONTH, cal_1.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal_1.getTime();
        cal_1.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        Date beginDate = cal_1.getTime();
        List<String> queryData = new ArrayList<>();
        //while (endDate.compareTo(beginDate) >= 0&&nowDate.compareTo(beginDate)>0) {
        while (endDate.compareTo(beginDate) >= 0) {
            String day = formatDate2.format(beginDate);
            queryData.add(day);
            beginDate=addDay(beginDate, 1);
        }
        return queryData;
    }
    public static String getPreMonthLastDay(String month) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date=formatDate.parse(month);
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.setTime(date);
        cal_1.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        Date beginDate = cal_1.getTime();
        return formatDate2.format(beginDate);
    }

    public static Date addDay(Date date,int add){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day +add);
        return c.getTime();
    }

    public static String shangTangTimeToStr(String captureTime){
        try {
            String captureTime1=captureTime.replace("T", " ").replace("Z", "");
            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdfTime.parse(captureTime1);
            Long time=date.getTime()+(60*1000*60*8);
           return sdfTime.format(new Date(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Long shangTangTimeToLong(Long captureTime){
        try {
            Long time=captureTime+(60*1000*60*8);
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
