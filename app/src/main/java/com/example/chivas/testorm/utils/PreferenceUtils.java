package com.example.chivas.testorm.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.chivas.dbres.utils.AppContext;
import com.example.chivas.dbres.utils.LogUtils;

public class PreferenceUtils {
    private static final String PREFERENCE_NAME = "preference"; // 共享文件名
    private static final String DEFAULT_VALUE = ""; //默认空值

    private PreferenceUtils() {
    }

    private static SharedPreferences getSharedPreference() {
        return AppContext.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean putString(String tag, String value) {
        SharedPreferences prefs = getSharedPreference();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(tag, value);
        return editor.commit();
    }

    public static String getString(String tag) {
        return getString(tag, DEFAULT_VALUE);
    }

    public static boolean putBoolean(String tag, boolean value) {
        SharedPreferences prefs = getSharedPreference();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(tag, value);
        return editor.commit();
    }

    public static boolean getBoolean(String tag) {
        return getBoolean(tag, false);
    }

    public static String getString(String tag, String defaultValue) {
        SharedPreferences prefs = getSharedPreference();
        return prefs.getString(tag, defaultValue);
    }

    public static boolean removeKey(String key) {
        SharedPreferences preferences = getSharedPreference();
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains(key)) {
            editor.remove(key);
            LogUtils.d("removeSPKey: " + key);
            return editor.commit();
        }
        return true;
    }

    public static boolean putLong(String tag, Long value) {
        SharedPreferences prefs = getSharedPreference();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(tag, value);
        return editor.commit();
    }

    public static long getLong(String tag, long defaultValue) {
        SharedPreferences prefs = getSharedPreference();
        return prefs.getLong(tag, defaultValue);
    }

    public static boolean putInt(String tag, int value) {
        SharedPreferences prefs = getSharedPreference();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(tag, value);
        return editor.commit();
    }

    public static int getInt(String tag, int defaultValue) {
        SharedPreferences prefs = getSharedPreference();
        return prefs.getInt(tag, defaultValue);
    }

    public static boolean getBoolean(String tag, boolean defaultValue) {
        SharedPreferences prefs = getSharedPreference();
        return prefs.getBoolean(tag, defaultValue);
    }
}