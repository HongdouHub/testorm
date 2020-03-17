package com.example.chivas.dbres.db.objectbox.manager;

import android.content.Context;

import com.example.chivas.dbres.db.objectbox.base.BaseDao;
import com.example.chivas.dbres.db.objectbox.entity.SimpleIndexedEntity;
import com.example.chivas.dbres.db.objectbox.entity.SimpleIndexedEntity_;

import java.util.List;

public class SimpleIndexedEntityManager extends BaseDao<SimpleIndexedEntity> {

    public SimpleIndexedEntityManager(Context context) {
        super(context, SimpleIndexedEntity.class);
    }


    public List<SimpleIndexedEntity> queryBySimpleString(String simpleString) {
        return mBox.query().equal(SimpleIndexedEntity_.simpleString, simpleString).build().find();
    }

    public List<SimpleIndexedEntity> queryBySimpleInt(int simpleInt) {
        return mBox.query().equal(SimpleIndexedEntity_.simpleInt, simpleInt).build().find();
    }
}
