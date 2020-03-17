package com.example.chivas.dbres.db.realm.base;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

public class RealmMigrationImpl implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmMigrationHelper.getInstance().migrate(realm);
    }
}
