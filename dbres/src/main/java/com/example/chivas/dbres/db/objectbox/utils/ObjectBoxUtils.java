package com.example.chivas.dbres.db.objectbox.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.chivas.dbres.db.objectbox.base.BaseDao;
import com.example.chivas.dbres.db.objectbox.base.ExtraBaseDao;
import com.example.chivas.dbres.db.objectbox.manager.BillDesManager;
import com.example.chivas.dbres.db.objectbox.manager.SimpleEntityManager;
import com.example.chivas.dbres.db.objectbox.manager.SimpleIndexedEntityManager;
import com.example.chivas.dbres.db.objectbox.manager.WaybillInfoManager;
import com.example.chivas.dbres.db.objectbox.manager.base.BoxStoreManager;
import com.example.chivas.dbres.db.objectbox.manager.base.ExtraBoxStoreManager;
import com.example.chivas.dbres.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.chivas.dbres.db.objectbox.utils.DaoManagerConstants.BILL_DES;
import static com.example.chivas.dbres.db.objectbox.utils.DaoManagerConstants.SIMPLE_ENTITY;
import static com.example.chivas.dbres.db.objectbox.utils.DaoManagerConstants.SIMPLE_INDEXED_ENTITY;
import static com.example.chivas.dbres.db.objectbox.utils.DaoManagerConstants.WAYBILL_INFO;

public class ObjectBoxUtils {

    private static Map<String, BaseDao> daoManagers;
    private static Map<String, ExtraBaseDao> extraDaoManagers;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private ObjectBoxUtils() {
        //
    }

    // 初始化数据库工具类
    public static void init(Context context) {
        ObjectBoxUtils.context = context.getApplicationContext();
        if (daoManagers == null) {
            daoManagers = new ConcurrentHashMap<>();    // 线程安全
            LogUtils.d("ObjectBoxUtils init daoManagers map");
        }
        if (extraDaoManagers == null) {
            extraDaoManagers = new ConcurrentHashMap<>();   // 线程安全
            LogUtils.d("ObjectBoxUtils init extraDaoManagers map");
        }
    }

    public static WaybillInfoManager getWaybillInfoManager() {
        WaybillInfoManager manager = (WaybillInfoManager) daoManagers.get(WAYBILL_INFO);
        if (null == manager) {
            manager = new WaybillInfoManager(context);
            daoManagers.put(WAYBILL_INFO, manager);
        }
        return manager;
    }

    public static BillDesManager getBillDesManager() {
        BillDesManager manager = (BillDesManager) extraDaoManagers.get(BILL_DES);
        if (null == manager) {
            manager = new BillDesManager(context);
            extraDaoManagers.put(BILL_DES, manager);
        }
        return manager;
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
        BoxStoreManager.getInstance().close();
        ExtraBoxStoreManager.getInstance().close();
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
        LogUtils.d("ObjectBoxUtils set null");
    }

    public static boolean deleteAllFiles() {
        boolean deleteNative = BoxStoreManager.getInstance().deleteAllFiles();
        boolean deleteExtra = ExtraBoxStoreManager.getInstance().deleteAllFiles();
        LogUtils.d("ObjectBoxUtils deleteNative:" + deleteNative + ", deleteExtra:" + deleteExtra);
        return true;
    }
}
