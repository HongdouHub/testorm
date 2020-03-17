package com.example.chivas.dbres.db.room.base;

import android.content.Context;
import android.database.Cursor;

import com.example.chivas.dbres.db.room.manager.base.RoomManager;
import com.example.chivas.dbres.db.room.manager.databases.NormalDatabase;
import com.example.chivas.dbres.utils.LogUtils;

import java.io.Closeable;
import java.io.IOException;

public class BaseDao<T> {

    protected RoomManager mManager;
    protected NormalDatabase mDatabase;

    public BaseDao(Context context) {
        mManager = RoomManager.getInstance();
        mManager.init(context);
        mDatabase = mManager.getDatabase();
    }

    public String getSQLiteVersion() {
        String sql = "SELECT sqlite_version() AS sqlite_version";
        Cursor cursor = null;
        try {
            cursor = mDatabase.query(sql, null);
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } finally {
            close(cursor);
        }
        return null;
    }

    /**
     * 关闭数据库一般在onDestroy中使用
     */
    public void closeDataBase() {
        mManager.closeDataBase();
    }

    protected void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtils.e("BaseDao close exception: " + e);
                closeable = null;
            }
        }
    }
}
