package com.example.chivas.dbres.db.objectbox.manager;

import android.content.Context;

import com.example.chivas.dbres.db.objectbox.base.ExtraBaseDao;
import com.example.chivas.dbres.db.objectbox.entity.BillDes;
import com.example.chivas.dbres.db.objectbox.entity.BillDes_;

import java.util.List;

public class BillDesManager extends ExtraBaseDao<BillDes>{

    public BillDesManager(Context context) {
        super(context, BillDes.class);
    }

    /**
     * 根据主键ID来查询
     */
    public BillDes queryById(long id) {
        List<BillDes> list = mBox.query().contains(BillDes_._id, String.valueOf(id)).build().find();
        return list.isEmpty() ? null : list.get(0);
    }
}
