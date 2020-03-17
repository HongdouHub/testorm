package com.example.chivas.dbres.db.objectbox.base;

import android.content.Context;

import com.example.chivas.dbres.db.objectbox.manager.base.BoxStoreManager;
import com.example.chivas.dbres.utils.LogUtils;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * 封装通用基础功能
 */
public class BaseDao<T> {
    public static final boolean DEBUG = true;
    protected BoxStoreManager mBoxStoreManager;
    protected BoxStore mBoxStore;
    protected Box<T> mBox;

    public BaseDao(Context context, Class<T> cla) {
        mBoxStoreManager = BoxStoreManager.getInstance();
        mBoxStoreManager.init(context, DEBUG);
        mBoxStore = mBoxStoreManager.getBoxStore();
        mBox = mBoxStore.boxFor(cla);
    }

//    runInTx	    在给定的 runnable 中运行的事务。
//    runInReadTx	只读事务，不同于 runInTx，允许并发读取
//    runInTxAsync	运行在一个单独的线程中执行，执行完成后，返回 callback。
//    callInTx      与 runInTx 相似，不同的是可以有返回值。

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
            flag = mBox.put(object) != -1;
            LogUtils.d("objectbox insert object success flag = " + flag);
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
        mBoxStore.runInTxAsync(new Runnable() {
            @Override
            public void run() {
               mBox.put(object);
            }
        }, null);
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
            mBoxStore.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects) {
                        if (object != null) {
                            mBox.put(object);
                        }
                    }
                }
            });
            LogUtils.d("objectbox insert" + objects.size() + "success");
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
            mBox.put(objects);
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
     */
    public void insertMultiAsync(final List<T> objects) {
        mBoxStore.runInTxAsync(new Runnable() {
            @Override
            public void run() {
                mBox.put(objects);
            }
        }, null);
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
            mBox.put(object);
            LogUtils.d("objectbox update object success");
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
        mBoxStore.runInTxAsync(new Runnable() {
            @Override
            public void run() {
                mBox.put(object);
            }
        }, null);
    }

    /**
     * 异步批量更新
     *
     * @param objects
     */
    public void updateMultiAsync(final List<T> objects) {
        mBoxStore.runInTxAsync(new Runnable() {
            @Override
            public void run() {
                mBox.put(objects);
            }
        }, null);
    }

    /**
     * 批量更新数据
     *
     * @param objects
     * @return
     */
    public void updateMultiObjects(final List<T> objects) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            mBox.put(objects);
            LogUtils.d("objectbox update " + objects.size() + " success");
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /**************************数据库删除操作***********************/
    /**
     * 删除某个数据库表
     *
     * @return
     */
    public boolean deleteAll() {
        try {
            mBox.removeAll();
            LogUtils.d("objectbox delete all data success");
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
            mBox.remove(object);
            LogUtils.d("objectbox delete object success");
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
    public void deleteObjectAsync(final T object) {
        if (null == object) {
            return;
        }

        mBoxStore.runInTxAsync(new Runnable() {
            @Override
            public void run() {
                mBox.remove(object);
            }
        }, null);
    }

    /**
     * 批量删除数据
     *
     * @param objects
     * @return
     */
    public boolean deleteMultiObjects(final List<T> objects) {
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            mBox.remove(objects);
            LogUtils.d("objectbox delete " + objects.size() + " success");
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
     */
    public boolean deleteMultiAsync(final List<T> objects) {
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            mBoxStore.runInTxAsync(new Runnable() {
                @Override
                public void run() {
                    mBox.remove(objects);
                    LogUtils.d("objectbox delete Async" + objects.size() + " success");
                }
            }, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**************************数据库查询操作***********************/

    public String getVersionNative() {
        return BoxStore.getVersionNative();
    }

    public String getVersion() {
        return BoxStore.getVersion();
    }

    /**
     * 获得某个表名
     *
     * @return
     */
    public String getTableName(Class object) {
        return object.getSimpleName();
    }

    /**
     * 查询所有对象
     *
     * @return
     */
    public List<T> queryAll() {
        List<T> objects = null;
        try {
            objects = mBox.getAll();
//            objects = mBox.query().build().find();
            LogUtils.d("objectbox query success, total " + objects.size());
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
        mBox.closeThreadResources();
    }
}
