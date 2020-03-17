package com.example.chivas.testorm;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.example.chivas.dbres.utils.AppContext;

import java.util.List;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(this);
        final String processName = getProcessName(this);
        if (!TextUtils.isEmpty(processName) && TextUtils.equals(getProcessName(this), getPackageName())) {
            init();
        }
    }

    private void init() {
        // 初始化GreenDao数据库
//        GreenDaoUtils.init(this);

        // 初始化ObjectBox数据库
//        ObjectBoxUtils.init(this);
    }

    /**
     * 获取当前进程名
     */
    public static String getProcessName(Context appContext) {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == android.os.Process.myPid()) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
