package com.example.chivas.dbres.db.greendao.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chivas.dbres.db.greendao.base.ExtraBaseDao;
import com.example.chivas.dbres.db.greendao.entity.BillDes;
import com.example.chivas.dbres.utils.LogUtils;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class BillDesManager extends ExtraBaseDao<BillDes>{
    
    public BillDesManager(Context context) {
        super(context);
    }

    /**
     * 插入数据
     */
    public void insertBySql(List<BillDes> list) {
        mSession.getBillDesDao().insertInTx(list);
        mSession.clear();
    }

    public BillDes getDestByWaybill(String waybill, boolean isOut) {
        BillDes billDes = null;
        Cursor cursor = null;
        try {
            String sql = isOut ? "select * from bill_des_out where bill_no = ?" :
                    "select * from bill_des_in where bill_no = ?";
            cursor = mSession.getBillDesDao().getDatabase().rawQuery(sql, new String[]{waybill});
            while (cursor.moveToNext()) {
                billDes = new BillDes();
                billDes.setDes(cursor.getString(cursor.getColumnIndex("DES")));
                billDes.setBillNo(cursor.getString(cursor.getColumnIndex("BILL_NO")));
            }

        } catch (Exception e) {
            LogUtils.e("getDestByWaybill:" + e);
            return billDes;
        } finally {
            mSession.clear();
            if (null != cursor)
                cursor.close();
        }
        return billDes;
    }

    /**
     * 添加索引
     */
    public void addIndexForTable() {
        Database database = mSession.getDatabase();
        String sqlBillNo = "create index if not exists idx_bill_billno on BILL_DES_TB ( bill_no )";
        database.execSQL(sqlBillNo);
        mSession.clear();
    }

    /**
     * 删除索引
     */
    private void delIndexForTable() {
        Database database = mSession.getDatabase();
        String sqlBillNo = "drop index if exists idx_bill_billno";
        database.execSQL(sqlBillNo);
        mSession.clear();
    }

    /**
     * 删除两天前的数据
     */
    public void delBeforeTowDays() {
        mSession.getBillDesDao().deleteAll();
        mSession.clear();
        delIndexForTable();
    }

    /**
     * 重建视图
     */
    public void reCreateView(String[] args) {
        Database database = mSession.getDatabase();
        String dropView = "drop view if exists bill_des_out";
        database.execSQL(dropView);
        String createView = "create view if not exists bill_des_out as ";
        for (int i = 0; i < args.length; i++) {
            if (i == args.length - 1) {
                createView = createView + args[i];
            } else {
                createView = createView + args[i] + " union all ";
            }
        }
        LogUtils.e(createView);
        database.execSQL(createView);
        mSession.clear();
    }

    public void setEnableWriteAheadLogging(boolean enable) {
        try {
            SQLiteDatabase sqLiteDatabase = (SQLiteDatabase) mSession.getDatabase().getRawDatabase();
            if (enable) {
                sqLiteDatabase.enableWriteAheadLogging();
            } else {
                sqLiteDatabase.disableWriteAheadLogging();
            }
        } catch (Exception e) {
            LogUtils.d("setEnableWriteAheadLogging " + e);
        }
    }
}
