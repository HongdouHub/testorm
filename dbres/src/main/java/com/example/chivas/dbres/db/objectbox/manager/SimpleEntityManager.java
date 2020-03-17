package com.example.chivas.dbres.db.objectbox.manager;

import android.content.Context;

import com.example.chivas.dbres.db.objectbox.base.BaseDao;
import com.example.chivas.dbres.db.objectbox.entity.SimpleEntity;
import com.example.chivas.dbres.db.objectbox.entity.SimpleEntity_;

import java.util.List;
import java.util.concurrent.Callable;

public class SimpleEntityManager extends BaseDao<SimpleEntity> {

    public SimpleEntityManager(Context context) {
        super(context, SimpleEntity.class);
    }

    public List<SimpleEntity> queryBySimpleString(String simpleString) {
        return mBox.query().equal(SimpleEntity_.simpleString, simpleString).build().find();
    }

    public List<SimpleEntity> queryBySimpleInt(int simpleInt) {
        return mBox.query().equal(SimpleEntity_.simpleInt, simpleInt).build().find();
    }

    public long getAllCount() {
        return mBox.count();
    }

    public SimpleEntity queryById(long _id) {
        return mBox.get(_id);
    }

    public SimpleEntity queryByIdInReadTx(final long _id) {
        return mBoxStore.callInReadTx(new Callable<SimpleEntity>() {
            @Override
            public SimpleEntity call() throws Exception {
                return mBox.get(_id);
            }
        });
    }
}
