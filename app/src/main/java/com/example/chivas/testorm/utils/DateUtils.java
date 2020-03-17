package com.example.chivas.testorm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private DateUtils() {
        //
    }

    /**
     * 获取指定时间
     *
     * @param timeInMillis 要格式的时间
     * @param dateFormat   时间格式
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }


    /**
     * 获取当前时间  默认时间格式(如2014-02-24 15:41)
     *
     * @param timeInMillis 要格式的时间
     * @return
     */
    public static String getCurrentTime(long timeInMillis) {
        return getTime(timeInMillis, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.CHINESE));
    }
}
