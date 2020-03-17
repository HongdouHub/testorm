package com.example.chivas.dbres.db.greendao.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "waybill_info".
*/
public class WaybillInfoDao extends AbstractDao<WaybillInfo, Long> {

    public static final String TABLENAME = "waybill_info";

    /**
     * Properties of entity WaybillInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property GoodsNo = new Property(1, String.class, "goodsNo", false, "GOODS_NO");
        public final static Property GoodsType = new Property(2, String.class, "goodsType", false, "GOODS_TYPE");
        public final static Property ContainerNo = new Property(3, String.class, "containerNo", false, "CONTAINER_NO");
        public final static Property CarLogoNo = new Property(4, String.class, "carLogoNo", false, "CAR_LOGO_NO");
        public final static Property TransitDepotNo = new Property(5, String.class, "transitDepotNo", false, "TRANSIT_DEPOT_NO");
        public final static Property PostNo = new Property(6, String.class, "postNo", false, "POST_NO");
        public final static Property StationNo = new Property(7, String.class, "stationNo", false, "STATION_NO");
        public final static Property OperatorNo = new Property(8, String.class, "operatorNo", false, "OPERATOR_NO");
        public final static Property OpTime = new Property(9, Long.class, "opTime", false, "OP_TIME");
        public final static Property UploadFlag = new Property(10, String.class, "uploadFlag", false, "UPLOAD_FLAG");
    }


    public WaybillInfoDao(DaoConfig config) {
        super(config);
    }
    
    public WaybillInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"waybill_info\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"GOODS_NO\" TEXT," + // 1: goodsNo
                "\"GOODS_TYPE\" TEXT," + // 2: goodsType
                "\"CONTAINER_NO\" TEXT," + // 3: containerNo
                "\"CAR_LOGO_NO\" TEXT," + // 4: carLogoNo
                "\"TRANSIT_DEPOT_NO\" TEXT," + // 5: transitDepotNo
                "\"POST_NO\" TEXT," + // 6: postNo
                "\"STATION_NO\" TEXT," + // 7: stationNo
                "\"OPERATOR_NO\" TEXT," + // 8: operatorNo
                "\"OP_TIME\" INTEGER," + // 9: opTime
                "\"UPLOAD_FLAG\" TEXT);"); // 10: uploadFlag
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"waybill_info\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WaybillInfo entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String goodsNo = entity.getGoodsNo();
        if (goodsNo != null) {
            stmt.bindString(2, goodsNo);
        }
 
        String goodsType = entity.getGoodsType();
        if (goodsType != null) {
            stmt.bindString(3, goodsType);
        }
 
        String containerNo = entity.getContainerNo();
        if (containerNo != null) {
            stmt.bindString(4, containerNo);
        }
 
        String carLogoNo = entity.getCarLogoNo();
        if (carLogoNo != null) {
            stmt.bindString(5, carLogoNo);
        }
 
        String transitDepotNo = entity.getTransitDepotNo();
        if (transitDepotNo != null) {
            stmt.bindString(6, transitDepotNo);
        }
 
        String postNo = entity.getPostNo();
        if (postNo != null) {
            stmt.bindString(7, postNo);
        }
 
        String stationNo = entity.getStationNo();
        if (stationNo != null) {
            stmt.bindString(8, stationNo);
        }
 
        String operatorNo = entity.getOperatorNo();
        if (operatorNo != null) {
            stmt.bindString(9, operatorNo);
        }
 
        Long opTime = entity.getOpTime();
        if (opTime != null) {
            stmt.bindLong(10, opTime);
        }
 
        String uploadFlag = entity.getUploadFlag();
        if (uploadFlag != null) {
            stmt.bindString(11, uploadFlag);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WaybillInfo entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String goodsNo = entity.getGoodsNo();
        if (goodsNo != null) {
            stmt.bindString(2, goodsNo);
        }
 
        String goodsType = entity.getGoodsType();
        if (goodsType != null) {
            stmt.bindString(3, goodsType);
        }
 
        String containerNo = entity.getContainerNo();
        if (containerNo != null) {
            stmt.bindString(4, containerNo);
        }
 
        String carLogoNo = entity.getCarLogoNo();
        if (carLogoNo != null) {
            stmt.bindString(5, carLogoNo);
        }
 
        String transitDepotNo = entity.getTransitDepotNo();
        if (transitDepotNo != null) {
            stmt.bindString(6, transitDepotNo);
        }
 
        String postNo = entity.getPostNo();
        if (postNo != null) {
            stmt.bindString(7, postNo);
        }
 
        String stationNo = entity.getStationNo();
        if (stationNo != null) {
            stmt.bindString(8, stationNo);
        }
 
        String operatorNo = entity.getOperatorNo();
        if (operatorNo != null) {
            stmt.bindString(9, operatorNo);
        }
 
        Long opTime = entity.getOpTime();
        if (opTime != null) {
            stmt.bindLong(10, opTime);
        }
 
        String uploadFlag = entity.getUploadFlag();
        if (uploadFlag != null) {
            stmt.bindString(11, uploadFlag);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public WaybillInfo readEntity(Cursor cursor, int offset) {
        WaybillInfo entity = new WaybillInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // goodsNo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // goodsType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // containerNo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // carLogoNo
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // transitDepotNo
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // postNo
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // stationNo
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // operatorNo
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // opTime
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // uploadFlag
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WaybillInfo entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGoodsNo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGoodsType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContainerNo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCarLogoNo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTransitDepotNo(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPostNo(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setStationNo(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setOperatorNo(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setOpTime(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setUploadFlag(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WaybillInfo entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WaybillInfo entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WaybillInfo entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
