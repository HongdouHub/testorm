package com.example.chivas.dbres.db.objectbox.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Administrator on 005 9月5日.
 */
@Entity(useNoArgConstructor = true)
public class BillDes {

    @Id(assignable = true)
    public long _id;        // 主键id

    public String billNo;   // 运单号
    public String des;      // 目的地
}
