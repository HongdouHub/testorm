
package com.example.chivas.dbres.db.objectbox.entity;

import com.example.chivas.dbres.db.objectbox.entity.BillDesCursor.Factory;
import io.objectbox.EntityInfo;
import io.objectbox.annotation.apihint.Internal;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;

// THIS CODE IS GENERATED BY ObjectBox, DO NOT EDIT.

/**
 * Properties for entity "BillDes". Can be used for QueryBuilder and for referencing DB names.
 */
public final class BillDes_ implements EntityInfo<BillDes> {

    // Leading underscores for static constants to avoid naming conflicts with property names

    public static final String __ENTITY_NAME = "BillDes";

    public static final int __ENTITY_ID = 1;

    public static final Class<BillDes> __ENTITY_CLASS = BillDes.class;

    public static final String __DB_NAME = "BillDes";

    public static final CursorFactory<BillDes> __CURSOR_FACTORY = new Factory();

    @Internal
    static final BillDesIdGetter __ID_GETTER = new BillDesIdGetter();

    public final static BillDes_ __INSTANCE = new BillDes_();

    public final static io.objectbox.Property<BillDes> _id =
        new io.objectbox.Property<>(__INSTANCE, 0, 1, long.class, "_id", true, "_id");

    public final static io.objectbox.Property<BillDes> billNo =
        new io.objectbox.Property<>(__INSTANCE, 1, 2, String.class, "billNo");

    public final static io.objectbox.Property<BillDes> des =
        new io.objectbox.Property<>(__INSTANCE, 2, 3, String.class, "des");

    @SuppressWarnings("unchecked")
    public final static io.objectbox.Property<BillDes>[] __ALL_PROPERTIES = new io.objectbox.Property[]{
        _id,
        billNo,
        des
    };

    public final static io.objectbox.Property<BillDes> __ID_PROPERTY = _id;

    @Override
    public String getEntityName() {
        return __ENTITY_NAME;
    }

    @Override
    public int getEntityId() {
        return __ENTITY_ID;
    }

    @Override
    public Class<BillDes> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override
    public String getDbName() {
        return __DB_NAME;
    }

    @Override
    public io.objectbox.Property<BillDes>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override
    public io.objectbox.Property<BillDes> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override
    public IdGetter<BillDes> getIdGetter() {
        return __ID_GETTER;
    }

    @Override
    public CursorFactory<BillDes> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    @Internal
    static final class BillDesIdGetter implements IdGetter<BillDes> {
        @Override
        public long getId(BillDes object) {
            return object._id;
        }
    }

}
