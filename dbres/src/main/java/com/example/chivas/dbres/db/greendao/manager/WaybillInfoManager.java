package com.example.chivas.dbres.db.greendao.manager;

import android.content.Context;

import com.example.chivas.dbres.db.greendao.base.BaseDao;
import com.example.chivas.dbres.db.greendao.entity.WaybillInfo;
import com.example.chivas.dbres.db.greendao.entity.WaybillInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class WaybillInfoManager extends BaseDao<WaybillInfo> {

    public WaybillInfoManager(Context context) {
        super(context);
    }

    /******************************新增***********************************/



    /******************************删除***********************************/



    /******************************修改***********************************/

    /******************************查询***********************************/

    public List<WaybillInfo> getWaybillInfoListById(int minValue, int maxValue) {
        QueryBuilder<WaybillInfo> queryBuilder = mSession.getWaybillInfoDao().queryBuilder();
        queryBuilder.where(WaybillInfoDao.Properties._id.between(minValue, maxValue));
        return queryBuilder.list();
    }
}
