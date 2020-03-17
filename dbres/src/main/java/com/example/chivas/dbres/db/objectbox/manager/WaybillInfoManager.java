package com.example.chivas.dbres.db.objectbox.manager;

import android.content.Context;

import com.example.chivas.dbres.db.objectbox.base.BaseDao;
import com.example.chivas.dbres.db.objectbox.entity.WaybillInfo;
import com.example.chivas.dbres.db.objectbox.entity.WaybillInfo_;

import java.util.List;

public class WaybillInfoManager extends BaseDao<WaybillInfo> {

    public WaybillInfoManager(Context context) {
        super(context, WaybillInfo.class);
    }

    /**
     * 根据主键ID来查询
     */
    public WaybillInfo queryById(long _id) {
        List<WaybillInfo> list = mBox.query().equal(WaybillInfo_._id, _id).build().find();
        return list.isEmpty() ? null : list.get(0);
    }

    public List<WaybillInfo> getWaybillInfoListById(int minValue, int maxValue) {
        return mBox.query().between(WaybillInfo_._id, minValue, maxValue).build().find();
    }
}
