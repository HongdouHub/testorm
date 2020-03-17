package com.example.chivas.dbres.db.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.chivas.dbres.db.room.entity.SimpleIndexedEntity;

import java.util.List;

@Dao
public interface SimpleIndexedEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiObjects(List<SimpleIndexedEntity> list);

    @Delete
    void deleteMultiObjects(List<SimpleIndexedEntity> list);

    @Update
    void updateMultiObjects(List<SimpleIndexedEntity> list);

    @Query("SELECT * FROM simple_indexed_entity WHERE _id = :id LIMIT 1")
    SimpleIndexedEntity queryById(long id);

    @Query("SELECT * FROM simple_indexed_entity")
    List<SimpleIndexedEntity> queryAll();

    @Transaction
    @Query("SELECT * FROM simple_indexed_entity WHERE simpleInt = :simpleInt")
    List<SimpleIndexedEntity> queryBySimpleInt(int simpleInt);

    @Transaction
    @Query("SELECT * FROM simple_indexed_entity WHERE simpleString = :simpleString")
    List<SimpleIndexedEntity> queryBySimpleString(String simpleString);

    @Query("SELECT COUNT(*) FROM simple_indexed_entity")
    long getAllCount();
}
