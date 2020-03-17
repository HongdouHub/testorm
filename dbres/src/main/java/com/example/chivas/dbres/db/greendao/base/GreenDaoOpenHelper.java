package com.example.chivas.dbres.db.greendao.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.chivas.dbres.db.greendao.entity.BillDesDao;
import com.example.chivas.dbres.db.greendao.entity.DaoMaster;
import com.example.chivas.dbres.db.greendao.entity.SimpleEntityDao;
import com.example.chivas.dbres.db.greendao.entity.SimpleIndexedEntityDao;
import com.example.chivas.dbres.db.greendao.entity.WaybillInfoDao;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库升级帮助类
 *
 * 需要在onUpdate中添加需要迁移的表
 */
public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {

    public GreenDaoOpenHelper(Context context, String name) {
        super(context, name);
    }

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        GreenDaoMigrationHelper.getInstance().migrate(db,
                WaybillInfoDao.class, BillDesDao.class,
                SimpleEntityDao.class, SimpleIndexedEntityDao.class);
    }
}
