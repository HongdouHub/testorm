package com.example.chivas.dbres.db.realm.manager.base;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmManager {

    private static final String DB_NAME = "realm_1.db";  // 数据库名称
    private Realm mRealm;
    private RealmConfiguration mConfiguration;

    private RealmManager() {
        //
    }

    public static RealmManager getInstance() {
        return RealmManagerHolder.instance;
    }

    public void init(Context context) {
        if (null == mRealm) {
            Realm.init(context.getApplicationContext());
            mConfiguration = new RealmConfiguration.Builder()
                    .name(DB_NAME)
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

    private static class RealmManagerHolder {
        private static RealmManager instance = new RealmManager();
    }
}
