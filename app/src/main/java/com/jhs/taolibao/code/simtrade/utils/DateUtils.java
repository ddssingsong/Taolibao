package com.jhs.taolibao.code.simtrade.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期工具类
 * Created by KANGXIANGTAO on 2016/7/11.
 */
public class DateUtils {


    public static Date getCurrentTime() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return curDate;
    }

    private static final long INTERVAL_IN_MILLISECONDS = 30 * 1000;

    public static String getTimestampString(Date messageDate) {

        boolean isChinese = true;

        String format = null;

        long messageTime = messageDate.getTime();
        if (isSameDay(messageTime)) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(messageDate);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            format = "HH:mm";

            if (hour > 17) {
                if(isChinese){
                    format = "晚上 hh:mm";
                }

            }else if(hour >= 0 && hour <= 6){
                if(isChinese){
                    format = "凌晨 hh:mm";
                }
            } else if (hour > 11 && hour <= 17) {
                if(isChinese){
                    format = "下午 hh:mm";
                }

            } else {
                if(isChinese){
                    format = "上午 hh:mm";
                }
            }
        } else if (isYesterday(messageTime)) {
            if(isChinese){
                format = "昨天 HH:mm";
            }else{
                format = "MM-dd HH:mm";
            }

        } else {
            if(isChinese){
                format = "M月d日 HH:mm";
            }else{
                format = "MM-dd HH:mm";
            }
        }

        if(isChinese){
            return new SimpleDateFormat(format, Locale.CHINA).format(messageDate);
        }else{
            return new SimpleDateFormat(format, Locale.US).format(messageDate);
        }
    }
    public static boolean isSameDay(long inputTime) {

        TimeInfo tStartAndEndTime = getTodayStartAndEndTime();
        if(inputTime>tStartAndEndTime.getStartTime()&&inputTime<tStartAndEndTime.getEndTime())
            return true;
        return false;
    }

    public static boolean isYesterday(long inputTime) {
        TimeInfo yStartAndEndTime = getYesterdayStartAndEndTime();
        if(inputTime>yStartAndEndTime.getStartTime()&&inputTime<yStartAndEndTime.getEndTime())
            return true;
        return false;
    }

    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -1);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    public static TimeInfo getTodayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    /**
     * 获取当前星期几
     * @return 星期几
     */
    public static String getCurrentWeek(){
        String week = "星期";
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.SUNDAY:
                week += "日";
                break;
            case Calendar.MONDAY:
                week += "一";
                break;
            case Calendar.TUESDAY:
                week += "二";
                break;
            case Calendar.WEDNESDAY:
                week += "三";
                break;
            case Calendar.THURSDAY:
                week += "四";
                break;
            case Calendar.FRIDAY:
                week += "五";
                break;
            case Calendar.SATURDAY:
                week += "六";
                break;
        }
        return week;
    }

    /**
     * 获取两周内特定时间的周几
     * @param time 毫秒时间
     * @return 周几 下周几  x月x日
     */
    public static String getWeekByTime(long time){

        String strWeek = "";
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(time);

        int day = calendar1.get(Calendar.DAY_OF_WEEK);
        int week = calendar1.get(Calendar.WEEK_OF_YEAR);
        int curWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        int curDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (curWeek == week){
            //同一周
            strWeek = "周";
            if (curDay == day){
                return "今日";
            }else if (curDay == Calendar.SUNDAY){
                strWeek = "下周";
            }
        }else if(week == curWeek + 1){
            //下周
            strWeek = "下周";
            if (day == Calendar.SUNDAY){
                strWeek = "周";
            }
        }else {
            //显示 x月x日
            //Log.e("DateUtils", "getWeekByTime:M月d日 " + time +"  "+new SimpleDateFormat("M月d日", Locale.CHINA).format(new Date(time)));
            return new SimpleDateFormat("M月d日", Locale.CHINA).format(new Date(time));
        }

         switch (day) {
             case Calendar.SUNDAY:
                 strWeek += "日";
                  break;
              case Calendar.MONDAY:
                  strWeek += "一";
                  break;
              case Calendar.TUESDAY:
                  strWeek += "二";
                  break;
              case Calendar.WEDNESDAY:
                  strWeek += "三";
                  break;
              case Calendar.THURSDAY:
                  strWeek += "四";
                  break;
              case Calendar.FRIDAY:
                  strWeek += "五";
                  break;
              case Calendar.SATURDAY:
                  strWeek += "六";
                  break;
        }

        return strWeek;
    }

    /**
     *
     * @param timeLength second
     * @return
     */
    public static String toTimeBySecond(long  timeLength) {
//      timeLength /= 1000;
        int minute = (int)timeLength / 60;
        int hour = 0;
        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        int second = (int)timeLength % 60;
        return String.format(" %02d 小时 %02d 分钟 %02d 秒", hour, minute, second);

    }

    /**
     * 根据字符串转换为时间戳
     * @param strTime 2016-07-31
     * @return
     */
    public static long getTime(String strTime){
        long time = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(strTime);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
