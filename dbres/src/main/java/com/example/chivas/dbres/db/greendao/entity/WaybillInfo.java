package com.example.chivas.dbres.db.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "waybill_info")
public class WaybillInfo {

    @Id(autoincrement = true)
    private Long _id;       // 主键id

    private String goodsNo;         // 货物编号
    private String goodsType;       // 货物类型，2:单发件，3：包，4：笼
    private String containerNo;     // 容器编号
    private String carLogoNo;       // 车标号
    private String transitDepotNo;  // 中转场编号
    private String postNo;          // 岗位号
    private String stationNo;       // 工位号
    private String operatorNo;      // 操作员工号
    private Long opTime;         // 操作时间，毫秒数
    private String uploadFlag;      // 是否已上传{0:未上传； 1:已上传}

    @Keep
    public WaybillInfo() {
        this.uploadFlag = "0"; // 未上传
        this.opTime = System.currentTimeMillis();
    }

    @Generated(hash = 777521630)
    public WaybillInfo(Long _id, String goodsNo, String goodsType,
            String containerNo, String carLogoNo, String transitDepotNo,
            String postNo, String stationNo, String operatorNo, Long opTime,
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

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getGoodsNo() {
        return this.goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsType() {
        return this.goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getContainerNo() {
        return this.containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getCarLogoNo() {
        return this.carLogoNo;
    }

    public void setCarLogoNo(String carLogoNo) {
        this.carLogoNo = carLogoNo;
    }

    public String getTransitDepotNo() {
        return this.transitDepotNo;
    }

    public void setTransitDepotNo(String transitDepotNo) {
        this.transitDepotNo = transitDepotNo;
    }

    public String getPostNo() {
        return this.postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getStationNo() {
        return this.stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getOperatorNo() {
        return this.operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public Long getOpTime() {
        return this.opTime;
    }

    public void setOpTime(Long opTime) {
        this.opTime = opTime;
    }

    public String getUploadFlag() {
        return this.uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }
}
