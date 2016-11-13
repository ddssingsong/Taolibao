package com.jhs.taolibao.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dds on 2016/6/29.
 *
 * @TODO
 */
public class CalendarUtil {
    private static Calendar calendar = Calendar.getInstance();//实例化日历


    // 获得当前年份
    public static int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    // 获得当前月份
    public static int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }


    // 获得今天在本月的第几天(获得当前日)
    public static int getDayOfMonth() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    //将Date装换为String

    public static String convertDateToString(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dateTime);
    }

    public static String convertDateToString1(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("MM");
        return df.format(dateTime);
    }

    public static String convertDateToString2(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd");
        return df.format(dateTime);
    }

    public static String convertDateToString3(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        return df.format(dateTime);
    }
    public static String convertDateToString4(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(dateTime);
    }
    //得到二个日期间的间隔天数

    public static long getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return 0;
        }
        return day;
    }


    //将短时间格式字符串转换为时间 yyyy-MM-dd
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    //将短时间格式字符串转换为时间 yyyy-MM-dd
    public static Date strToDate1(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    //两个时间之间的天数
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }


    //系统当前时间

    public static String getCurrentDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentDate1() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentDate(String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }
    // 得到几天前的时间

    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    // 得到几天后的时间

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }


    /**
     * @param dateStr 时间
     * @param flag    显示哪个
     * @return
     */
    public static int getDateInt(String dateStr, int flag) {
        SimpleDateFormat sf = new SimpleDateFormat();
        try {
            Date date = sf.parse(dateStr);
            calendar.setTime(date);
            if (flag == 0) {
                return calendar.get(Calendar.YEAR);
            } else if (flag == 1) {
                return calendar.get(Calendar.MONTH) + 1;
            } else {
                return calendar.get(Calendar.DAY_OF_MONTH);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }

    }


}
