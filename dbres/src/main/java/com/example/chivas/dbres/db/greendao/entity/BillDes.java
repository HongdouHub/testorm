package com.example.chivas.dbres.db.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class BillDes {

    @Id(autoincrement = true)
    private Long _id;       // 主键

    private String billNo;  // 运单号
    private String des;     // 目的地
    @Generated(hash = 437081775)
    public BillDes(Long _id, String billNo, String des) {
        this._id = _id;
        this.billNo = billNo;
        this.des = des;
    }
    @Generated(hash = 1477143023)
    public BillDes() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getBillNo() {
        return this.billNo;
    }
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    public String getDes() {
        return this.des;
    }
    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "BillDes{" +
                "_id=" + _id +
                ", billNo='" + billNo + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
