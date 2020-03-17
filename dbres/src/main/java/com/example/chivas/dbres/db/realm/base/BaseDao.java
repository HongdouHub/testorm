package com.example.chivas.dbres.db.realm.base;

import android.content.Context;

import com.example.chivas.dbres.db.realm.manager.base.RealmManager;
import com.example.chivas.dbres.utils.LogUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class BaseDao<T extends RealmObject> {

    protected RealmManager mManager;
    protected Realm mRealm;

    public BaseDao(Context context) {
        mManager = RealmManager.getInstance();
        mManager.init(context);
        mRealm = mManager.getRealm();
    }

    /**************************数据库插入操作***********************/

    /**
     * 插入单个对象
     *
     * @param object
     * @return
     */
    public boolean insertObject(T object) {
        try {
            mRealm.insert(object);
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    /**
     * 异步插入单个对象
     *
     * @param object
     */
    public void insertObjectAsync(final T object) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(object);
            }
        });
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
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(objects);
                }
            });
            LogUtils.d("realm insert" + objects.size() + "success");
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
            mRealm.beginTransaction();
            mRealm.insert(objects);
            mRealm.commitTransaction();
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
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(objects);
                }
            });
            LogUtils.d("realm insert" + objects.size() + "success");
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /**************************数据库更新操作***********************/
    /**
     * 以对象形式进行数据修改
     * 其中必须要知道对象的主键ID
     *
     * @param object
     * @return
     */
    public void updateObject(final T object) {
        if (null == object) {
            return;
        }
        try {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(object);
                }
            });
            LogUtils.d("realm update object success");
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
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(object);
            }
        });
    }

    /**
     * 异步批量更新
     *
     * @param objects
     */
    public void updateMultiAsync(final List<T> objects) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(objects);
            }
        });
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
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(objects);
            }
        });
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
            mRealm.delete(cla);
            LogUtils.d("realm delete all data success");
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
    public boolean deleteObject(final T object) {
        if (null == object) {
            return false;
        }

        try {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    object.deleteFromRealm();
                }
            });
            LogUtils.d("realm delete object success");
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
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                object.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                LogUtils.d("realm delete object success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogUtils.e(new Exception(error));
            }
        });
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
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (objects instanceof RealmResults) {
                        ((RealmResults) objects).deleteAllFromRealm();
                    } else {
                        for (T object : objects) {
                            object.deleteFromRealm();
                        }
                    }
                }
            });
            LogUtils.d("realm delete " + objects.size() + " success");
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
            mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for (T object : objects) {
                        object.deleteFromRealm();
                    }
                }
            });
            LogUtils.d("realm delete Async" + objects.size() + " success");
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
            return false;
        }
    }

    /**
     * 批量删除数据
     *
     * @param objects
     * @return
     */
    public boolean deleteMultiObjects(final RealmResults<T> objects) {
        if (null == objects || objects.isEmpty()) {
            return false;
        }
        try {
            objects.deleteAllFromRealm();
            LogUtils.d("realm delete " + objects.size() + " success");
            return true;
        } catch (Exception e) {
            LogUtils.e(e);
            return false;
        }
    }

    /**************************数据库查询操作***********************/

    public long getVersion() {
        return mRealm.getVersion();
    }

    /**
     * 获得某个表名
     *
     * @return
     */
    public String getTableName(Class<T> object) {
        return object.getSimpleName();
    }

    /**
     * 根据主键ID来查询
     *
     * @param id
     * @return
     */
    public T queryById(long id, Class<T> object) {
        return mRealm.where(object).equalTo("id", id).findFirst();
    }

    /**
     * 查询所有对象
     *
     * @param object
     * @return
     */
    public RealmResults<T> queryAll(Class<T> object) {
        RealmResults<T> objects = null;
        try {
            objects = mRealm.where(object).findAll();
            LogUtils.d("realm query success, total " + objects.size());
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

    public void beginTransaction() {
        mRealm.beginTransaction();
    }

    public void endTransaction() {
        mRealm.commitTransaction();
    }
}
