package com.example.chivas.dbres.db.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SimpleEntity {

    @Id
    private long _id;
    private boolean simpleBoolean;
    private byte simpleByte;
    private short simpleShort;
    private int simpleInt;
    private long simpleLong;
    private float simpleFloat;
    private double simpleDouble;
    private String simpleString;
    private byte[] simpleByteArray;

    @Generated(hash = 1545703718)
    public SimpleEntity(long _id, boolean simpleBoolean, byte simpleByte,
            short simpleShort, int simpleInt, long simpleLong, float simpleFloat,
            double simpleDouble, String simpleString, byte[] simpleByteArray) {
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
    @Generated(hash = 1682830787)
    public SimpleEntity() {
    }
    public long get_id() {
        return this._id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }
    public boolean getSimpleBoolean() {
        return this.simpleBoolean;
    }
    public void setSimpleBoolean(boolean simpleBoolean) {
        this.simpleBoolean = simpleBoolean;
    }
    public byte getSimpleByte() {
        return this.simpleByte;
    }
    public void setSimpleByte(byte simpleByte) {
        this.simpleByte = simpleByte;
    }
    public short getSimpleShort() {
        return this.simpleShort;
    }
    public void setSimpleShort(short simpleShort) {
        this.simpleShort = simpleShort;
    }
    public int getSimpleInt() {
        return this.simpleInt;
    }
    public void setSimpleInt(int simpleInt) {
        this.simpleInt = simpleInt;
    }
    public long getSimpleLong() {
        return this.simpleLong;
    }
    public void setSimpleLong(long simpleLong) {
        this.simpleLong = simpleLong;
    }
    public float getSimpleFloat() {
        return this.simpleFloat;
    }
    public void setSimpleFloat(float simpleFloat) {
        this.simpleFloat = simpleFloat;
    }
    public double getSimpleDouble() {
        return this.simpleDouble;
    }
    public void setSimpleDouble(double simpleDouble) {
        this.simpleDouble = simpleDouble;
    }
    public String getSimpleString() {
        return this.simpleString;
    }
    public void setSimpleString(String simpleString) {
        this.simpleString = simpleString;
    }
    public byte[] getSimpleByteArray() {
        return this.simpleByteArray;
    }
    public void setSimpleByteArray(byte[] simpleByteArray) {
        this.simpleByteArray = simpleByteArray;
    }
}
