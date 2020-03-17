package com.example.chivas.dbres.db.room.manager;

import android.content.Context;

import com.example.chivas.dbres.db.room.base.BaseDao;
import com.example.chivas.dbres.db.room.dao.SimpleEntityDao;
import com.example.chivas.dbres.db.room.entity.SimpleEntity;

import java.util.List;

public class SimpleEntityManager extends BaseDao<SimpleEntity> implements SimpleEntityDao {

    private SimpleEntityDao mDao;

    public SimpleEntityManager(Context context) {
        super(context);
        mDao = mDatabase.getSimpleEntityDao();
    }

    @Override
    public void insertMultiObjects(List<SimpleEntity> list) {
        mDao.insertMultiObjects(list);
    }

    @Override
    public void deleteMultiObjects(List<SimpleEntity> list) {
        mDao.deleteMultiObjects(list);
    }

    @Override
    public void updateMultiObjects(List<SimpleEntity> list) {
        mDao.updateMultiObjects(list);
    }

    @Override
    public SimpleEntity queryById(long _id) {
        return mDao.queryById(_id);
    }

    @Override
    public List<SimpleEntity> queryAll() {
        return mDao.queryAll();
    }

    @Override
    public List<SimpleEntity> queryBySimpleInt(int simpleInt) {
        return mDao.queryBySimpleInt(simpleInt);
    }

    @Override
    public List<SimpleEntity> queryBySimpleString(String simpleString) {
        return mDao.queryBySimpleString(simpleString);
    }

    @Override
    public long getAllCount() {
        return mDao.getAllCount();
    }
}
