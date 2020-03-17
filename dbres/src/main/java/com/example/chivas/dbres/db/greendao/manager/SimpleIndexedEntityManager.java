package com.example.chivas.dbres.db.greendao.manager;

import android.content.Context;

import com.example.chivas.dbres.db.greendao.base.BaseDao;
import com.example.chivas.dbres.db.greendao.entity.SimpleIndexedEntity;
import com.example.chivas.dbres.db.greendao.entity.SimpleIndexedEntityDao;
import com.example.chivas.dbres.utils.LogUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class SimpleIndexedEntityManager extends BaseDao<SimpleIndexedEntity> {

    public SimpleIndexedEntityManager(Context context) {
        super(context);
    }


    /******************************新增***********************************/

    @Override
    public boolean insertMultiObjects(List<SimpleIndexedEntity> objects) {
        try {
            mManager.getDaoSession().getSimpleIndexedEntityDao().insertInTx(objects);
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    /******************************删除***********************************/



    /******************************修改***********************************/

    @Override
    public void updateMultiObjects(final List<SimpleIndexedEntity> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            mSession.getSimpleIndexedEntityDao().updateInTx(objects);
            LogUtils.d("greendao update " + objects.size() + " success");
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /******************************查询***********************************/

    public List<SimpleIndexedEntity> queryBySimpleString(String simpleString) {
        QueryBuilder<SimpleIndexedEntity> queryBuilder = mSession.getSimpleIndexedEntityDao().queryBuilder();
        queryBuilder.where(SimpleIndexedEntityDao.Properties.SimpleString.eq(simpleString));
        return queryBuilder.list();
    }

    public List<SimpleIndexedEntity> queryBySimpleInt(int simpleInt) {
        QueryBuilder<SimpleIndexedEntity> queryBuilder = mSession.getSimpleIndexedEntityDao().queryBuilder();
        queryBuilder.where(SimpleIndexedEntityDao.Properties.SimpleInt.eq(simpleInt));
        return queryBuilder.list();
    }

    public void beginTransaction() {
        mSession.getSimpleIndexedEntityDao().getDatabase().beginTransaction();
    }

    public void endTransaction() {
        mSession.getSimpleIndexedEntityDao().getDatabase().endTransaction();
    }
}
