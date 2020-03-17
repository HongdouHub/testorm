package com.example.chivas.dbres.db.greendao.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.chivas.dbres.db.greendao.base.BaseDao;
import com.example.chivas.dbres.db.greendao.base.ExtraBaseDao;
import com.example.chivas.dbres.db.greendao.manager.BillDesManager;
import com.example.chivas.dbres.db.greendao.manager.SimpleEntityManager;
import com.example.chivas.dbres.db.greendao.manager.SimpleIndexedEntityManager;
import com.example.chivas.dbres.db.greendao.manager.WaybillInfoManager;
import com.example.chivas.dbres.db.greendao.manager.base.DaoManager;
import com.example.chivas.dbres.db.greendao.manager.base.ExtraDaoManager;
import com.example.chivas.dbres.utils.LogUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.chivas.dbres.db.greendao.utils.DaoManagerConstants.BILL_DES;
import static com.example.chivas.dbres.db.greendao.utils.DaoManagerConstants.SIMPLE_ENTITY;
import static com.example.chivas.dbres.db.greendao.utils.DaoManagerConstants.SIMPLE_INDEXED_ENTITY;
import static com.example.chivas.dbres.db.greendao.utils.DaoManagerConstants.WAYBILL_INFO;

/**
 * 数据库工具类
 * 维护内置内部数据库{@link BaseDao}及外部数据库{@link ExtraBaseDao}
 * 添加数据表需要添加相应的get方法，将对应的Manager初始化并放入{@link #daoManagers}或{@link #extraDaoManagers}
 * 请不要修改{@link #closeAllDataBase}，{@link #setNull}
 */
public class GreenDaoUtils {

    private static Map<String, BaseDao> daoManagers;
    private static Map<String, ExtraBaseDao> extraDaoManagers;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private GreenDaoUtils() {
        //
    }

    public static boolean isInitialized() {
        return null != context;
    }

    // 初始化数据库工具类
    public static void init(Context context) {
        GreenDaoUtils.context = context.getApplicationContext();
        if (daoManagers == null) {
            daoManagers = new ConcurrentHashMap<>();    // 线程安全
            LogUtils.d("GreenDaoUtils init daoManagers map");
        }
        if (extraDaoManagers == null) {
            extraDaoManagers = new ConcurrentHashMap<>();   // 线程安全
            LogUtils.d("GreenDaoUtils init extraDaoManagers map");
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
        DaoManager.getInstance().closeDataBase();
        ExtraDaoManager.getInstance().closeDataBase();
        getBillDesManager().setEnableWriteAheadLogging(false);
        if (null != daoManagers) {
            daoManagers.clear();
        }
        if (null != extraDaoManagers) {
            extraDaoManagers.clear();
        }
        LogUtils.d("GreenDaoUtils set null");
    }
}
