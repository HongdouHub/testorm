package com.example.chivas.dbres.utils;

import android.util.Log;

/**
 * 日志工具
 */
public class LogUtils {

    private static final String TAG = "LogUtils";

    private LogUtils() {
        //
    }

    /**
     * 将日志写入到文件中 d
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void i(String format, Object... args) {
        Log.i(TAG, String.format(format, args));
    }

    public static void i(String message) {
        Log.i(TAG, message);
    }

    /**
     * 将日志写入到文件中 d
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void v(String format, Object... args) {
        Log.v(TAG, String.format(format, args));
    }

    public static void v(String message) {
        Log.v(TAG, message);
    }

    /**
     * 将日志写入到文件中 d
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void d(String format, Object... args) {
        Log.d(TAG, String.format(format, args));
    }

    public static void d(String message) {
        Log.d(TAG, message);
    }

    /**
     * 将日志写入到文件中 e
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void e(String format, Object... args) {
        Log.e(TAG, String.format(format, args));
    }

    public static void e(String message) {
        Log.d(TAG, message);
    }

    public static void e(Exception ex) {
        Log.d(TAG, ex.toString());
    }
}
