package com.example.chivas.dbres.db.greendao.manager.base;

import android.content.Context;

import com.example.chivas.dbres.db.greendao.base.GreenDaoOpenHelper;
import com.example.chivas.dbres.db.greendao.entity.DaoMaster;
import com.example.chivas.dbres.db.greendao.entity.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

public class DaoManager {

    private static final String DB_NAME = "greendao_1.db";  // 数据库名称
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private GreenDaoOpenHelper mHelper;

    private DaoManager() {
        //
    }

    public static DaoManager getInstance() {
        return DaoManagerHolder.instance;
    }

    public void init(Context context) {
        if (null == mDaoMaster) {
            mHelper = new GreenDaoOpenHelper(context, DB_NAME, null);
            mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        }
    }

    public DaoSession getDaoSession() {
        if (null == mDaoSession) {
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    public void setDebug(boolean debug) {
        QueryBuilder.LOG_SQL = debug;
        QueryBuilder.LOG_VALUES = debug;
    }

    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
        mDaoMaster = null;
    }

    private void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private void closeHelper() {
        if (null != mHelper) {
            mHelper.close();
            mHelper = null;
        }
    }

    private static class DaoManagerHolder {
        private static DaoManager instance = new DaoManager();
    }
}
