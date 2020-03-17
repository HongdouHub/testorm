package com.example.chivas.dbres.db.objectbox.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * 运单数据表
 *
 * Created by Administrator on 003 9月3日.
 */
@Entity(useNoArgConstructor = true)
public class WaybillInfo {
    @Id(assignable = true)
    public long _id;       // 主键id

    public String goodsNo;         // 货物编号
    public String goodsType;       // 货物类型，2:单发件，3：包，4：笼
    public String containerNo;     // 容器编号
    public String carLogoNo;       // 车标号
    public String transitDepotNo;  // 中转场编号
    public String postNo;          // 岗位号
    public String stationNo;       // 工位号
    public String operatorNo;      // 操作员工号
    public Long opTime;            // 操作时间，毫秒数
    public String uploadFlag;      // 是否已上传{0:未上传； 1:已上传}

    public WaybillInfo() {
        opTime = System.currentTimeMillis();
    }

    public WaybillInfo(long _id, String goodsNo, String goodsType,
                       String containerNo, String carLogoNo,String transitDepotNo,
                       String postNo, String stationNo,String operatorNo, Long opTime,
                       String uploadFlag) {
        this._id = _id;
        this.goodsNo = goodsNo;
        this.goodsType = goodsType;
        this.containerNo = containerNo;
        this.carLogoNo = carLogoNo;
        this.transitDepotNo = transitDepotNo;
        this.postNo = postNo;
        this.stationNo = stationNo;
        this.operatorNo = operatorNo;
        this.opTime = opTime;
        this.uploadFlag = uploadFlag;
    }
}
