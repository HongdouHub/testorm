package com.example.chivas.dbres.db.realm.manager;

import android.content.Context;

import com.example.chivas.dbres.db.realm.base.BaseDao;
import com.example.chivas.dbres.db.realm.entity.SimpleIndexedEntity;

import java.util.List;

public class SimpleIndexedEntityManager extends BaseDao<SimpleIndexedEntity> {

    public SimpleIndexedEntityManager(Context context) {
        super(context);
    }

    public SimpleIndexedEntity queryById(long _id) {
        return queryById(_id, SimpleIndexedEntity.class);
    }

    public long getAllCount() {
        return mRealm.where(SimpleIndexedEntity.class).count();
    }

    public List<SimpleIndexedEntity> queryBySimpleString(String simpleString) {
        return mRealm.where(SimpleIndexedEntity.class)
                .equalTo("simpleString", simpleString)
                .findAll();
    }

    public List<SimpleIndexedEntity> queryBySimpleInt(int simpleInt) {
        return mRealm.where(SimpleIndexedEntity.class)
                .equalTo("simpleInt", simpleInt)
                .findAll();
    }
}
