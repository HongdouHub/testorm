package com.example.chivas.dbres.db.room.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.chivas.dbres.db.room.base.BaseDao;
import com.example.chivas.dbres.db.room.manager.SimpleEntityManager;
import com.example.chivas.dbres.db.room.manager.SimpleIndexedEntityManager;
import com.example.chivas.dbres.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.chivas.dbres.db.room.utils.DaoManagerConstants.SIMPLE_ENTITY;
import static com.example.chivas.dbres.db.room.utils.DaoManagerConstants.SIMPLE_INDEXED_ENTITY;

public class RoomUtils {

    private static Map<String, BaseDao> daoManagers;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private RoomUtils() {
        //
    }

    public static boolean isInitialized() {
        return null != context;
    }

    public static void init(Context context) {
        RoomUtils.context = context.getApplicationContext();
        if (daoManagers == null) {
            daoManagers = new ConcurrentHashMap<>();    // 线程安全
            LogUtils.d("RoomUtils init daoManagers map");
        }
    }

    public static SimpleEntityManager getSimpleEntityManager() {
        SimpleEntityManager manager = (SimpleEntityManager) daoManagers.get(SIMPLE_ENTITY);
        if (null == manager) {
            manager = new SimpleEntityManager(context);
            daoManagers.put(SIMPLE_ENTITY, manager);
        }
        return manager;
    }

    public static SimpleIndexedEntityManager getSimpleIndexedEntityManager() {
        SimpleIndexedEntityManager manager = (SimpleIndexedEntityManager) daoManagers.get(SIMPLE_INDEXED_ENTITY);
        if (null == manager) {
            manager = new SimpleIndexedEntityManager(context);
            daoManagers.put(SIMPLE_INDEXED_ENTITY, manager);
        }
        return manager;
    }

    // 关闭数据库
    public static void closeAllDataBase() {
        if (daoManagers != null && !daoManagers.isEmpty()) {
            for (BaseDao baseDao : daoManagers.values()) {
                baseDao.closeDataBase();
            }
            daoManagers = null;
        }
    }

    public static void setNull() {
        if (null != daoManagers) {
            daoManagers.clear();
        }
        LogUtils.d("RoomUtils set null");
    }
}
