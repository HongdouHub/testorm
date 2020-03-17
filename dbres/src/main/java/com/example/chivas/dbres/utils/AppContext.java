package com.example.chivas.dbres.utils;

import android.content.Context;

public class AppContext {

    private static Context mContext;

    /**
     * 构造函数
     */
    private AppContext() {

    }

    /**
     * 全局context和编译类型
     *
     * @param context
     */
    public static void init(Context context) {
        AppContext.mContext = context.getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
