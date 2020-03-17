package com.example.chivas.dbres.db.objectbox.manager.base;

import android.content.Context;

import com.example.chivas.dbres.BuildConfig;
import com.example.chivas.dbres.db.objectbox.entity.MyObjectBox;
import com.example.chivas.dbres.utils.ExtraFileUtils;
import com.example.chivas.dbres.utils.LogUtils;

import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.DebugFlags;
import io.objectbox.android.AndroidObjectBrowser;

public class ExtraBoxStoreManager {

    private static final String DB_NAME = "objectbox_2.mdb";  // 数据库名称
    private static BoxStore mBoxStore;

    private ExtraBoxStoreManager() {
        //
    }

    public static ExtraBoxStoreManager getInstance() {
        return ExtraBoxStoreManagerHolder.instance;
    }

    public void init(Context context, boolean debug) {
        if (null == mBoxStore) {
            Context applicationContext = context.getApplicationContext();
            // 默认的数据库位置在： /data/data/包名/files/objectbox/data.mdb 下，执行.directory()可修改
            BoxStoreBuilder boxStoreBuilder = MyObjectBox.builder()
                    .androidContext(applicationContext)
                    .directory(ExtraFileUtils.getDefaultPathFile(DB_NAME, context));
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

    private static class ExtraBoxStoreManagerHolder {
        private static ExtraBoxStoreManager instance = new ExtraBoxStoreManager();
    }

}
