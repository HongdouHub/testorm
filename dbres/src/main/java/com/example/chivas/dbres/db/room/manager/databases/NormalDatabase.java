package com.example.chivas.dbres.db.room.manager.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chivas.dbres.db.room.dao.SimpleEntityDao;
import com.example.chivas.dbres.db.room.dao.SimpleIndexedEntityDao;
import com.example.chivas.dbres.db.room.entity.SimpleEntity;
import com.example.chivas.dbres.db.room.entity.SimpleIndexedEntity;

@Database(entities = {SimpleEntity.class, SimpleIndexedEntity.class},
        version = 2, exportSchema = false)
public abstract class NormalDatabase extends RoomDatabase {

    public abstract SimpleEntityDao getSimpleEntityDao();

    public abstract SimpleIndexedEntityDao getSimpleIndexedEntityDao();
}
