package com.jhs.taolibao.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dds on 2016/6/25.
 *
 * @TODO
 */
public class DateUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    // 将时间cuo改为时间String

    public static String timeStamp2Date(String seconds, String format) {
        String str = seconds.substring(6, seconds.length() - 2);
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(str)));
    }

    //
    public static Date timeStamp2Date(String seconds) {
        String str = seconds.substring(6, seconds.length() - 2);
        return new Date(Long.valueOf(str));
    }

    public static String timeStamp2Date1(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    //判断时间是否在15：30-9：00
    public static boolean isTrueTime2(Date now) {
        int hours = now.getHours();
        int minutes = now.getMinutes();
        if ((hours >= 0 && hours < 9) || (hours == 15 && minutes >= 30) || (hours >= 16 && hours < 24)) {
            return true;
        }
        return false;
    }


    public static String getTimeString(Date time) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
        if (time == null) {
            return "";
        }
        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        if (today - day >= 1) {
            return df.format(time);
        } else {
            long diff = new Date().getTime() - time.getTime();
            long r = 0;
            if (diff > hour) {
                r = (diff / hour);
                return r + "个小时前";
            }
            if (diff > minute) {
                r = (diff / minute);
                return r + "分钟前";
            } else {
                return "刚刚";
            }
        }

    }


}
