package com.example.chivas.dbres.db.room.manager;

import android.content.Context;

import com.example.chivas.dbres.db.room.base.BaseDao;
import com.example.chivas.dbres.db.room.dao.SimpleIndexedEntityDao;
import com.example.chivas.dbres.db.room.entity.SimpleIndexedEntity;

import java.util.List;

public class SimpleIndexedEntityManager extends BaseDao<SimpleIndexedEntity>
        implements SimpleIndexedEntityDao {

    private SimpleIndexedEntityDao mDao;

    public SimpleIndexedEntityManager(Context context) {
        super(context);
        mDao = mDatabase.getSimpleIndexedEntityDao();
    }

    @Override
    public void insertMultiObjects(List<SimpleIndexedEntity> list) {
        mDao.insertMultiObjects(list);
    }

    @Override
    public void deleteMultiObjects(List<SimpleIndexedEntity> list) {
        mDao.deleteMultiObjects(list);
    }

    @Override
    public void updateMultiObjects(List<SimpleIndexedEntity> list) {
        mDao.updateMultiObjects(list);
    }

    @Override
    public SimpleIndexedEntity queryById(long _id) {
        return mDao.queryById(_id);
    }

    @Override
    public List<SimpleIndexedEntity> queryAll() {
        return mDao.queryAll();
    }

    @Override
    public List<SimpleIndexedEntity> queryBySimpleInt(int simpleInt) {
        return mDao.queryBySimpleInt(simpleInt);
    }

    @Override
    public List<SimpleIndexedEntity> queryBySimpleString(String simpleString) {
        return mDao.queryBySimpleString(simpleString);
    }

    @Override
    public long getAllCount() {
        return mDao.getAllCount();
    }
}
