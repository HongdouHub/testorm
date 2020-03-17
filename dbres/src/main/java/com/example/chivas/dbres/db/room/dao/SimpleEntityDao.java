package com.example.chivas.dbres.db.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.chivas.dbres.db.room.entity.SimpleEntity;

import java.util.List;

@Dao
public interface SimpleEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultiObjects(List<SimpleEntity> list);

    @Delete
    void deleteMultiObjects(List<SimpleEntity> list);

    @Update
    void updateMultiObjects(List<SimpleEntity> list);

    @Query("SELECT * FROM simple_entity WHERE _id = :id LIMIT 1")
    SimpleEntity queryById(long id);

    @Query("SELECT * FROM simple_entity")
    List<SimpleEntity> queryAll();

    @Transaction
    @Query("SELECT * FROM simple_entity WHERE simpleInt = :simpleInt")
    List<SimpleEntity> queryBySimpleInt(int simpleInt);

    @Transaction
    @Query("SELECT * FROM simple_entity WHERE simpleString = :simpleString")
    List<SimpleEntity> queryBySimpleString(String simpleString);

    @Query("SELECT COUNT(*) FROM simple_entity")
    long getAllCount();
}
