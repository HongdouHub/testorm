package com.example.chivas.dbres.db.greendao.base;

import android.database.Cursor;
import android.text.TextUtils;

import com.example.chivas.dbres.db.greendao.entity.BillDesDao;
import com.example.chivas.dbres.db.greendao.entity.SimpleEntityDao;
import com.example.chivas.dbres.db.greendao.entity.SimpleIndexedEntityDao;
import com.example.chivas.dbres.db.greendao.entity.WaybillInfoDao;
import com.example.chivas.dbres.utils.LogUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GreenDao的数据库迁移工具类
 */
public class GreenDaoMigrationHelper {

    public static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";

    private GreenDaoMigrationHelper() {
        //
    }

    public static GreenDaoMigrationHelper getInstance() {
        return GreenDaoMigrationHelperHolder.instance;
    }

    public final void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        generateTempTables(db, daoClasses);
        dropAllTables(db, true);
        createAllTables(db, true);
        restoreData(db, daoClasses);
    }

    private static void dropAllTables(Database db, boolean ifExists) {
        WaybillInfoDao.dropTable(db, ifExists);
        BillDesDao.dropTable(db, ifExists);
        SimpleEntityDao.dropTable(db, ifExists);
        SimpleIndexedEntityDao.dropTable(db, ifExists);
    }

    private static void createAllTables(Database db, boolean ifNotExists) {
        WaybillInfoDao.createTable(db, ifNotExists);
        BillDesDao.createTable(db, ifNotExists);
        SimpleEntityDao.createTable(db, ifNotExists);
        SimpleIndexedEntityDao.createTable(db, ifNotExists);
    }

    @SafeVarargs
    private final void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);

            String divider = "";
            String tableName = daoConfig.tablename;
            if (!checkTable(db, tableName)) continue;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            ArrayList<String> properties = new ArrayList<>();

            StringBuilder createTableStringBuilder = new StringBuilder();

            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");

            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tableName).contains(columnName)) {
                    properties.add(columnName);

                    String type = null;

                    try {
                        type = getTypeByClass(daoConfig.properties[j].type);
                    } catch (Exception exception) {
                        LogUtils.e("generateTempTables - exception:" + exception);
                    }

                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);

                    if (daoConfig.properties[j].primaryKey) {
                        createTableStringBuilder.append(" PRIMARY KEY");
                    }

                    divider = ",";
                }
            }
            createTableStringBuilder.append(");");

            db.execSQL(createTableStringBuilder.toString());

            String stringBuilder = "INSERT INTO " + tempTableName + " (" +
                    TextUtils.join(",", properties) +
                    ") SELECT " +
                    TextUtils.join(",", properties) +
                    " FROM " + tableName + ";";

            db.execSQL(stringBuilder);
        }
    }

    /**
     * 存储新的数据库表 以及数据
     *
     * @param db
     * @param daoClasses
     */
    private void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");
            if (!checkTable(db, tempTableName)) continue;
            ArrayList<String> properties = new ArrayList();

            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;

                if (getColumns(db, tempTableName).contains(columnName)) {
                    properties.add(columnName);
                }
            }

            StringBuilder insertTableStringBuilder = new StringBuilder();

            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");

            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
            db.execSQL(insertTableStringBuilder.toString());
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    private static String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class) || type.equals(int.class)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class)) {
            return "BOOLEAN";
        }

        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
        LogUtils.e("exception: " + exception);
        throw exception;
    }

    private List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            LogUtils.v(tableName, e.getMessage(), e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return columns;
    }

    /**
     * 检测table是否存在
     *
     * @param db
     * @param tableName
     */
    private static Boolean checkTable(Database db, String tableName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='").append(tableName).append("'");
        Cursor c = db.rawQuery(query.toString(), null);
        if (c.moveToNext()) {
            int count = c.getInt(0);
            if (count > 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    private static class GreenDaoMigrationHelperHolder {
        private static GreenDaoMigrationHelper instance = new GreenDaoMigrationHelper();
    }
}
