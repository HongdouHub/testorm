package com.example.chivas.dbres.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class ExtraFileUtils {

    public static File getDefaultPathFile(String dbName, Context context) {
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dbDir = sdcardPath + "/database";
        LogUtils.e("SD卡管理：", "dbDir=" + dbDir);
        File baseFile = new File(dbDir);
        // 目录不存在则自动创建目录
        if (!baseFile.exists()) {
            baseFile.mkdirs();
            LogUtils.i("SDCard dbDir=" + dbDir);
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(baseFile.getPath());
        buffer.append(File.separator);
        dbDir = buffer.toString();// 数据库所在目录
        buffer.append(File.separator);
        buffer.append(dbName);
        String dbPath = buffer.toString();// 数据库路径
        // 判断目录是否存在，不存在则创建该目录
        File dirFile = new File(dbDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
            LogUtils.i("dbDir=" + dbDir);
        }
        // 数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        // 判断文件是否存在，不存在则创建该文件
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                LogUtils.i("dbPath=" + dbPath);
            } catch (IOException e) {
                LogUtils.e("IOException:" + e);
            }
        } else {
            isFileCreateSuccess = true;
        }
        // 返回数据库文件对象
        if (isFileCreateSuccess)
            return dbFile;
        else
            return context.getDatabasePath(dbName);
    }

}
