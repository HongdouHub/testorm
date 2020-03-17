package com.example.chivas.dbres.db.greendao.manager;

import android.content.Context;

import com.example.chivas.dbres.db.greendao.base.BaseDao;
import com.example.chivas.dbres.db.greendao.entity.SimpleEntity;
import com.example.chivas.dbres.db.greendao.entity.SimpleEntityDao;
import com.example.chivas.dbres.utils.LogUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class SimpleEntityManager extends BaseDao<SimpleEntity> {

    public SimpleEntityManager(Context context) {
        super(context);
    }

    /******************************新增***********************************/

    @Override
    public boolean insertMultiObjects(List<SimpleEntity> objects) {
        try {
            mManager.getDaoSession().getSimpleEntityDao().insertInTx(objects);
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    /******************************删除***********************************/



    /******************************修改***********************************/

    @Override
    public void updateMultiObjects(final List<SimpleEntity> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            mSession.getSimpleEntityDao().updateInTx(objects);
            LogUtils.d("greendao update " + objects.size() + " success");
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /******************************查询***********************************/

    public SimpleEntity queryById(long _id) {
        return mSession.getSimpleEntityDao().load(_id);
    }

    public long getAllCount() {
        return mSession.getSimpleEntityDao().count();
    }

    public List<SimpleEntity> queryBySimpleString(String simpleString) {
        QueryBuilder<SimpleEntity> queryBuilder = mSession.getSimpleEntityDao().queryBuilder();
        queryBuilder.where(SimpleEntityDao.Properties.SimpleString.eq(simpleString));
        return queryBuilder.list();
    }

    public List<SimpleEntity> queryBySimpleInt(int simpleInt) {
        QueryBuilder<SimpleEntity> queryBuilder = mSession.getSimpleEntityDao().queryBuilder();
        queryBuilder.where(SimpleEntityDao.Properties.SimpleInt.eq(simpleInt));
        return queryBuilder.list();
    }

    public void beginTransaction() {
        mSession.getSimpleEntityDao().getDatabase().beginTransaction();
    }

    public void endTransaction() {
        mSession.getSimpleEntityDao().getDatabase().endTransaction();
    }

}
