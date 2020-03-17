package com.example.chivas.dbres.db.room.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "simple_indexed_entity",
        indices = {@Index("simpleInt"), @Index("simpleString")})
public class SimpleIndexedEntity {

    @PrimaryKey
    private long _id;
    private boolean simpleBoolean;
    private byte simpleByte;
    private short simpleShort;

    // index
    private int simpleInt;
    private long simpleLong;
    private float simpleFloat;
    private double simpleDouble;

    // index
    private String simpleString;
    private byte[] simpleByteArray;

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
