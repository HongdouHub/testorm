package com.example.chivas.dbres.db.room.manager.base;

import android.content.Context;

import androidx.room.Room;

import com.example.chivas.dbres.db.room.manager.databases.NormalDatabase;

public class RoomManager {

    private static final String DB_NAME = "room_1.db";  // 数据库名称
    private NormalDatabase mDatabase;

    private RoomManager() {
        //
    }

    public static RoomManager getInstance() {
        return RoomManagerHolder.instance;
    }

    public void init(Context context) {
        if (null == mDatabase) {
            mDatabase = Room.databaseBuilder(context, NormalDatabase.class, DB_NAME).build();
        }
    }

    public NormalDatabase getDatabase() {
        return mDatabase;
    }

    public void closeDataBase() {
        if (null != mDatabase) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    private static class RoomManagerHolder {
        private static RoomManager instance = new RoomManager();
    }
}
