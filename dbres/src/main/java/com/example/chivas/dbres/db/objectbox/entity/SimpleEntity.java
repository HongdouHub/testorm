package com.example.chivas.dbres.db.objectbox.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity(useNoArgConstructor = true)
@NameInDb("SimpleEntity")
public class SimpleEntity {

    /*@Index
    如果这个字段经常用来查询，加上 @Index 可以提高查询速度。
    @Transient
    如果字段不需要储存到数据库。
    @NameInDb
    指定字段储存在数据库中的名称。
    @Id(assignable = true)
    手动分配 ID。
    @Relation
    一对一或一对多关系*/

    @Id(assignable = true)
    long _id;       // 主键id
    boolean simpleBoolean;
    byte simpleByte;
    short simpleShort;
    int simpleInt;
    long simpleLong;
    float simpleFloat;
    double simpleDouble;
    String simpleString;
    byte[] simpleByteArray;

    public SimpleEntity() {
    }

    public SimpleEntity(long _id) {
        this._id = _id;
    }

    public SimpleEntity(long _id, boolean simpleBoolean, byte simpleByte, short simpleShort, int simpleInt, long simpleLong, float simpleFloat, double simpleDouble, String simpleString, byte[] simpleByteArray) {
        this._id = _id;
        this.simpleBoolean = simpleBoolean;
        this.simpleByte = simpleByte;
        this.simpleShort = simpleShort;
        this.simpleInt = simpleInt;
        this.simpleLong = simpleLong;
        this.simpleFloat = simpleFloat;
        this.simpleDouble = simpleDouble;
        this.simpleString = simpleString;
        this.simpleByteArray = simpleByteArray;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public boolean getSimpleBoolean() {
        return simpleBoolean;
    }

    public void setSimpleBoolean(boolean simpleBoolean) {
        this.simpleBoolean = simpleBoolean;
    }

    public byte getSimpleByte() {
        return simpleByte;
    }

    public void setSimpleByte(byte simpleByte) {
        this.simpleByte = simpleByte;
    }

    public short getSimpleShort() {
        return simpleShort;
    }

    public void setSimpleShort(short simpleShort) {
        this.simpleShort = simpleShort;
    }

    public int getSimpleInt() {
        return simpleInt;
    }

    public void setSimpleInt(int simpleInt) {
        this.simpleInt = simpleInt;
    }

    public long getSimpleLong() {
        return simpleLong;
    }

    public void setSimpleLong(long simpleLong) {
        this.simpleLong = simpleLong;
    }

    public float getSimpleFloat() {
        return simpleFloat;
    }

    public void setSimpleFloat(float simpleFloat) {
        this.simpleFloat = simpleFloat;
    }

    public double getSimpleDouble() {
        return simpleDouble;
    }

    public void setSimpleDouble(double simpleDouble) {
        this.simpleDouble = simpleDouble;
    }

    public String getSimpleString() {
        return simpleString;
    }

    public void setSimpleString(String simpleString) {
        this.simpleString = simpleString;
    }

    public byte[] getSimpleByteArray() {
        return simpleByteArray;
    }

    public void setSimpleByteArray(byte[] simpleByteArray) {
        this.simpleByteArray = simpleByteArray;
    }
}
