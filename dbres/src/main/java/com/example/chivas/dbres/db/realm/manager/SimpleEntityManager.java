package com.example.chivas.dbres.db.realm.manager;

import android.content.Context;

import com.example.chivas.dbres.db.realm.base.BaseDao;
import com.example.chivas.dbres.db.realm.entity.SimpleEntity;

import java.util.List;

public class SimpleEntityManager extends BaseDao<SimpleEntity> {

    public SimpleEntityManager(Context context) {
        super(context);
    }

    public SimpleEntity queryById(long _id) {
        return queryById(_id, SimpleEntity.class);
    }

    public long getAllCount() {
        return mRealm.where(SimpleEntity.class).count();
    }

    public List<SimpleEntity> queryBySimpleString(String simpleString) {
        return mRealm.where(SimpleEntity.class)
                .equalTo("simpleString", simpleString)
                .findAll();
    }

    public List<SimpleEntity> queryBySimpleInt(int simpleInt) {
        return mRealm.where(SimpleEntity.class)
                .equalTo("simpleInt", simpleInt)
                .findAll();
    }
}
