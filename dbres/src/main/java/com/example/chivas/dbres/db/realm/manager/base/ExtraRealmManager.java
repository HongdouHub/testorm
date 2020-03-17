package com.example.chivas.dbres.db.realm.manager.base;

import android.content.Context;

import com.example.chivas.dbres.utils.ExtraFileUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ExtraRealmManager {

    private static final String DB_NAME = "realm_2.db";  // 数据库名称
    private Realm mRealm;
    private RealmConfiguration mConfiguration;

    private ExtraRealmManager() {
        //
    }

    public static ExtraRealmManager getInstance() {
        return ExtraRealmManagerHolder.instance;
    }

    public void init(Context context) {
        if (null == mRealm) {
            Context applicationContext = context.getApplicationContext();
            Realm.init(applicationContext);
            mConfiguration = new RealmConfiguration.Builder()
                    .name(DB_NAME)
                    .directory(ExtraFileUtils.getDefaultPathFile(DB_NAME, context))
                    .schemaVersion(1)
//                    .migration(new RealmMigrationImpl())
                    .deleteRealmIfMigrationNeeded()
//                    .inMemory()
                    .build();
            Realm.setDefaultConfiguration(mConfiguration);
            mRealm = Realm.getDefaultInstance();
        }
    }

    public Realm getRealm() {
        return mRealm;
    }

    public void closeDataBase() {
        if (null != mRealm) {
            mRealm.close();
            mRealm = null;
        }
    }

    public boolean deleteAllFiles() {
        boolean deleted = false;
        if (null != mConfiguration) {
            deleted = Realm.deleteRealm(mConfiguration);
            mConfiguration = null;
        }
        return deleted;
    }


    private static class ExtraRealmManagerHolder {
        private static ExtraRealmManager instance = new ExtraRealmManager();
    }
}
