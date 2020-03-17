package com.example.chivas.dbres.db.objectbox.manager.base;

import android.content.Context;

import com.example.chivas.dbres.BuildConfig;
import com.example.chivas.dbres.db.objectbox.entity.MyObjectBox;
import com.example.chivas.dbres.utils.LogUtils;

import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.DebugFlags;
import io.objectbox.android.AndroidObjectBrowser;

public class BoxStoreManager {
    private static BoxStore mBoxStore;

    private BoxStoreManager() {
        //
    }

    public static BoxStoreManager getInstance() {
        return BoxStoreManagerHolder.instance;
    }

    public void init(Context context, boolean debug) {
        if (null == mBoxStore) {
            Context applicationContext = context.getApplicationContext();
            // 默认的数据库位置在： /data/data/包名/files/objectbox/objectbox/data.mdb 下，执行.directory()可修改
            BoxStoreBuilder boxStoreBuilder = MyObjectBox.builder().androidContext(applicationContext);
            if (debug) {
                boxStoreBuilder.debugFlags(DebugFlags.LOG_TRANSACTIONS_READ | DebugFlags.LOG_TRANSACTIONS_WRITE);
            }
            mBoxStore = boxStoreBuilder.build();
            if (BuildConfig.DEBUG) {
                new AndroidObjectBrowser(mBoxStore).start(applicationContext);
            }
            LogUtils.e("Using ObjectBox :" + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
        }
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }

    public void close() {
        if (mBoxStore != null) {
            mBoxStore.close();
        }
    }

    public boolean deleteAllFiles() {
        if (mBoxStore != null) {
            boolean deleted = mBoxStore.deleteAllFiles();
            mBoxStore = null;
            return deleted;
        }
        return false;
    }

    private static class BoxStoreManagerHolder {
        private static BoxStoreManager instance = new BoxStoreManager();
    }
}
