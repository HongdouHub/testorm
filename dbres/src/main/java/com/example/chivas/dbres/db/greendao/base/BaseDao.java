package com.example.chivas.dbres.db.greendao.base;

import android.content.Context;
import android.database.Cursor;

import com.example.chivas.dbres.db.greendao.entity.DaoSession;
import com.example.chivas.dbres.db.greendao.manager.base.DaoManager;
import com.example.chivas.dbres.utils.LogUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseDao<T> {
    public static final boolean DEBUG = true;
    protected DaoManager mManager;
    protected DaoSession mSession;

    public BaseDao(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
        mSession = mManager.getDaoSession();
        mManager.setDebug(DEBUG);
    }

    /**************************数据库插入操作***********************/

    /**
     * 插入单个对象
     *
     * @param object
     * @return
     */
    public boolean insertObject(T object) {
        boolean flag = false;
        try {
            flag = mManager.getDaoSession().insert(object) != -1;
            LogUtils.d("greendao insert object success flag = " + flag);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return flag;
    }

    /**
     * 异步插入单个对象
     *
     * @param object
     */
    public void insertObjectAsync(final T object) {
        DaoSession mSession = mManager.getDaoSession();
        mSession.startAsyncSession().insert(object);
    }

    /**
     * 插入多个对象，并开启新的线程
     *
     * @param objects
     * @return
     */
    public boolean insertMultiObject(final List<T> objects) {
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        mManager.getDaoSession().insertOrReplace(object);
                    }
                }
            });
            LogUtils.d("greendao insert" + objects.size() + "success");
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
            return false;
        }
    }

    /**
     * 插入多个对象，在当前的线程
     *
     * @param objects
     * @return
     */
    public boolean insertMultiObjects(List<T> objects) {
        try {
            mManager.getDaoSession().insert(objects);
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    /**
     * 异步批量插入
     *
     * @param objects
     * @param cla
     */
    public void insertMultiAsync(List<T> objects, Class<T> cla) {
        mSession.startAsyncSession().insertInTx(cla, objects);
    }

    /**************************数据库更新操作***********************/
    /**
     * 以对象形式进行数据修改
     * 其中必须要知道对象的主键ID
     *
     * @param object
     * @return
     */
    public void updateObject(T object) {

        if (null == object) {
            return;
        }
        try {
            mManager.getDaoSession().update(object);
            LogUtils.d("greendao update object success");
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /**
     * 异步更新单个对象
     *
     * @param object
     */
    public void updateObjectAsync(final T object) {
        if (null == object) {
            return;
        }
        DaoSession mSession = mManager.getDaoSession();
        mSession.startAsyncSession().update(object);
    }

    /**
     * 异步批量更新
     *
     * @param objects
     * @param claz
     */
    public void updateMultiAsync(List<T> objects, Class<T> claz) {
        mSession.startAsyncSession().updateInTx(claz, objects);
    }

    /**
     * 批量更新数据
     *
     * @param objects
     * @return
     */
    public void updateMultiObjects(final List<T> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            mSession.getDao(clss).updateInTx(objects);
            LogUtils.d("greendao update " + objects.size() + " success");
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /**************************数据库删除操作***********************/
    /**
     * 删除某个数据库表
     *
     * @param cla
     * @return
     */
    public boolean deleteAll(Class cla) {
        try {
            mManager.getDaoSession().deleteAll(cla);
            LogUtils.d("greendao delete all data success");
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
            return false;
        }
    }

    /**
     * 删除某个对象
     *
     * @param object
     * @return
     */
    public boolean deleteObject(T object) {
        try {
            mSession.delete(object);
            LogUtils.d("greendao delete object success");
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
            return false;
        }
    }

    /**
     * 异步删除某个对象
     *
     * @param object
     */
    public void deleteObjectAsync(T object) {
        if (null == object) {
            return;
        }
        DaoSession mSession = mManager.getDaoSession();
        mSession.startAsyncSession().delete(object);
    }

    /**
     * 批量删除数据
     *
     * @param objects
     * @return
     */
    public boolean deleteMultiObjects(final List<T> objects, Class cla) {
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            mSession.getDao(cla).deleteInTx(objects);
            LogUtils.d("greendao delete " + objects.size() + " success");
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
            return false;
        }
    }

    /**
     * 异步批量删除
     *
     * @param objects
     * @param cla
     */
    public boolean deleteMultiAsync(List<T> objects, Class<T> cla) {
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            mSession.startAsyncSession().deleteInTx(cla, objects);
            LogUtils.d("greendao delete Async" + objects.size() + " success");
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
            return false;
        }
    }

    /**************************数据库查询操作***********************/

    public String getSQLiteVersion() {
        String sql = "SELECT sqlite_version() AS sqlite_version";
        Cursor cursor = null;
        try {
            cursor = mSession.getSimpleEntityDao().getDatabase().rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } finally {
            close(cursor);
        }
        return null;
    }

    /**
     * 获得某个表名
     *
     * @return
     */
    public String getTableName(Class object) {
        return mSession.getDao(object).getTablename();
    }

    /**
     * 根据主键ID来查询
     *
     * @param id
     * @return
     */
    public T queryById(long id, Class object) {
        return (T) mSession.getDao(object).loadByRowId(id);
    }

    /**
     * 查询某条件下的对象
     *
     * @param object
     * @return
     */
    public List<T> queryObject(Class object, String where, String... params) {
        Object obj = null;
        List<T> objects = null;
        try {
            obj = mSession.getDao(object);
            if (null == obj) {
                return new ArrayList<>();
            }
            objects = mSession.getDao(object).queryRaw(where, params);
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return objects;
    }

    /**
     * 查询所有对象
     *
     * @param object
     * @return
     */
    public List<T> queryAll(Class object) {
        List<T> objects = null;
        try {
            objects = (List<T>) mSession.getDao(object).loadAll();
            LogUtils.d("greendao query success, total " + objects.size());
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return objects;
    }

    /***************************关闭数据库*************************/
    /**
     * 关闭数据库一般在onDestroy中使用
     */
    public void closeDataBase() {
        mManager.closeDataBase();
    }

    protected void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtils.e("BaseDao close exception: " + e);
                closeable = null;
            }
        }
    }
}
