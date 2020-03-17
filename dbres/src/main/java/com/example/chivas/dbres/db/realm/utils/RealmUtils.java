package com.example.chivas.dbres.db.realm.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.chivas.dbres.db.realm.base.BaseDao;
import com.example.chivas.dbres.db.realm.base.ExtraBaseDao;
import com.example.chivas.dbres.db.realm.manager.SimpleEntityManager;
import com.example.chivas.dbres.db.realm.manager.SimpleIndexedEntityManager;
import com.example.chivas.dbres.db.realm.manager.base.ExtraRealmManager;
import com.example.chivas.dbres.db.realm.manager.base.RealmManager;
import com.example.chivas.dbres.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.chivas.dbres.db.realm.utils.DaoManagerConstants.SIMPLE_ENTITY;
import static com.example.chivas.dbres.db.realm.utils.DaoManagerConstants.SIMPLE_INDEXED_ENTITY;

public class RealmUtils {

    private static Map<String, BaseDao> daoManagers;
    private static Map<String, ExtraBaseDao> extraDaoManagers;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private RealmUtils() {
        //
    }

    public static boolean isInitialized() {
        return null != context;
    }

    // 初始化数据库工具类
    public static void init(Context context) {
        RealmUtils.context = context.getApplicationContext();
        if (daoManagers == null) {
            daoManagers = new ConcurrentHashMap<>();    // 线程安全
            LogUtils.d("RealmUtils init daoManagers map");
        }
        if (extraDaoManagers == null) {
            extraDaoManagers = new ConcurrentHashMap<>();   // 线程安全
            LogUtils.d("RealmUtils init extraDaoManagers map");
        }
    }

    public static SimpleEntityManager getSimpleEntityManager() {
        SimpleEntityManager manager = (SimpleEntityManager) daoManagers.get(SIMPLE_ENTITY);
        if (manager == null) {
            manager = new SimpleEntityManager(context);
            daoManagers.put(SIMPLE_ENTITY, manager);
        }
        return manager;
    }

    public static SimpleIndexedEntityManager getSimpleIndexedEntityManager() {
        SimpleIndexedEntityManager manager = (SimpleIndexedEntityManager) daoManagers.get(SIMPLE_INDEXED_ENTITY);
        if (manager == null) {
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
        if (extraDaoManagers != null && !extraDaoManagers.isEmpty()) {
            for (ExtraBaseDao baseDao : extraDaoManagers.values()) {
                baseDao.closeDataBase();
            }
            extraDaoManagers = null;
        }
    }

    public static void setNull() {
        if (null != daoManagers) {
            daoManagers.clear();
        }
        if (null != extraDaoManagers) {
            extraDaoManagers.clear();
        }
        LogUtils.d("RealmUtils set null");
    }

    public static boolean deleteAllFiles() {
        boolean deleteNative = RealmManager.getInstance().deleteAllFiles();
        boolean deleteExtra = ExtraRealmManager.getInstance().deleteAllFiles();
        LogUtils.d("RealmUtils deleteNative:" + deleteNative + ", deleteExtra:" + deleteExtra);
        return true;
    }
}
