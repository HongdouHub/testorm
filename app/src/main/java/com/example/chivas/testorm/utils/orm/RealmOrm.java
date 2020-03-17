package com.example.chivas.testorm.utils.orm;

import com.example.chivas.dbres.db.realm.entity.SimpleEntity;
import com.example.chivas.dbres.db.realm.entity.SimpleIndexedEntity;
import com.example.chivas.dbres.db.realm.manager.SimpleEntityManager;
import com.example.chivas.dbres.db.realm.utils.RealmUtils;
import com.example.chivas.dbres.utils.AppContext;
import com.example.chivas.dbres.utils.LogUtils;
import com.example.chivas.testorm.bean.OpModelType;
import com.example.chivas.testorm.bean.SQLOperate;
import com.example.chivas.testorm.utils.OrmRunner;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class RealmOrm extends BaseOrm {

    private boolean onceLogged;

    @Override
    public String getName() {
        return "Realm";
    }

    @Override
    public void setUp(OrmRunner runner) {
        super.setUp(runner);
        RealmUtils.init(AppContext.getAppContext());
        RealmUtils.closeAllDataBase();
        RealmUtils.deleteAllFiles();

        RealmUtils.init(AppContext.getAppContext());
        SimpleEntityManager manager = RealmUtils.getSimpleEntityManager();
        if (!onceLogged) {
            log("Realm Version " + manager.getVersion());
            onceLogged = true;
        }
    }

    @Override
    public void run(OpModelType type) {
        switch (type.type) {
            case OpModelType.CRUD_NORMAL:
                runBatchCRUD(false);
                break;
            case OpModelType.CRUD_PRIMITIVE_TYPE:
                runBatchCRUD(true);
                break;
            case OpModelType.CRUD_INDEXED:
                runBatchIndexedCRUD();
                break;
            case OpModelType.QUERY_STRING:
                runQueryByString();
                break;
            case OpModelType.QUERY_STRING_INDEX:
                runQueryByIndexedString();
                break;
            case OpModelType.QUERY_INTEGER:
                runQueryByInteger();
                break;
            case OpModelType.QUERY_INTEGER_INDEX:
                runQueryByIndexedInteger();
                break;
            case OpModelType.QUERY_ID:
                runQueryById(false);
                break;
            case OpModelType.QUERY_ID_RANDOM:
                runQueryById(true);
                break;
            default:
        }
    }

    private void runBatchCRUD(boolean onlyPrimitive) {
        List<SimpleEntity> list = createEntityList(onlyPrimitive);
        startBenchmark(SQLOperate.INSERT);
        RealmUtils.getSimpleEntityManager().insertMultiObject(list);
        stopBenchmark();

        updateEntityList(list, onlyPrimitive);
        startBenchmark(SQLOperate.UPDATE);
        RealmUtils.getSimpleEntityManager().updateMultiObjects(list);
        stopBenchmark();

        list = null;

        startBenchmark(SQLOperate.QUERY);
        RealmResults<SimpleEntity> reloaded = RealmUtils.getSimpleEntityManager().queryAll(SimpleEntity.class);
        stopBenchmark();

        startBenchmark(SQLOperate.ACCESS);
        accessAll(reloaded);
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        RealmUtils.getSimpleEntityManager().deleteMultiObjects(reloaded);
        stopBenchmark();
    }

    private void runBatchIndexedCRUD() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();

        startBenchmark(SQLOperate.INSERT);
        RealmUtils.getSimpleIndexedEntityManager().insertMultiObject(list);
        stopBenchmark();

        updateEntityList(list);
        startBenchmark(SQLOperate.UPDATE);
        RealmUtils.getSimpleIndexedEntityManager().updateMultiObjects(list);
        stopBenchmark();

        list = null;

        startBenchmark(SQLOperate.QUERY);
        RealmResults<SimpleIndexedEntity> reloaded = RealmUtils.getSimpleIndexedEntityManager().queryAll(SimpleIndexedEntity.class);
        stopBenchmark();

        startBenchmark(SQLOperate.ACCESS);
        accessAllIndexed(reloaded);
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        RealmUtils.getSimpleIndexedEntityManager().deleteMultiObjects(reloaded);
        stopBenchmark();
    }

    private void runQueryByString() {
        if (mCount > 10000) {
            log("为避免等待时间过长，请减少数据量");
            return;
        }

        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        RealmUtils.getSimpleEntityManager().insertMultiObject(list);
        stopBenchmark();

        String[] lookupStrings = getLookupStrings(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        RealmUtils.getSimpleEntityManager().beginTransaction();
        for (int i = 0; i < mCount; i++) {
            List<SimpleEntity> result = RealmUtils.getSimpleEntityManager().queryBySimpleString(lookupStrings[i]);
            accessAll(result);
            entitiesFound += result.size();
        }
        RealmUtils.getSimpleEntityManager().endTransaction();
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RealmUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByIndexedString() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();
        startBenchmark(SQLOperate.INSERT);
        RealmUtils.getSimpleIndexedEntityManager().insertMultiObject(list);
        stopBenchmark();

        String[] lookupStrings = getLookupIndexedStrings(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        RealmUtils.getSimpleIndexedEntityManager().beginTransaction();
        for (int i = 0; i < mCount; i++) {
            List<SimpleIndexedEntity> result = RealmUtils.getSimpleIndexedEntityManager().queryBySimpleString(lookupStrings[i]);
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        RealmUtils.getSimpleIndexedEntityManager().endTransaction();
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RealmUtils.getSimpleIndexedEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByInteger() {
        if (mCount > 10000) {
            log("为避免等待时间过长，请减少数据量");
            return;
        }

        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        RealmUtils.getSimpleEntityManager().insertMultiObject(list);
        stopBenchmark();

        int[] lookupInts = getLookupInts(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        RealmUtils.getSimpleEntityManager().beginTransaction();
        for (int i = 0; i < mCount; i++) {
            List<SimpleEntity> result = RealmUtils.getSimpleEntityManager().queryBySimpleInt(lookupInts[i]);
            accessAll(result);
            entitiesFound += result.size();
        }
        RealmUtils.getSimpleEntityManager().endTransaction();
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RealmUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByIndexedInteger() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();
        startBenchmark(SQLOperate.INSERT);
        RealmUtils.getSimpleIndexedEntityManager().insertMultiObject(list);
        stopBenchmark();

        int[] lookupIndexedInts = getLookupIndexedInts(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        RealmUtils.getSimpleIndexedEntityManager().beginTransaction();
        for (int i = 0; i < mCount; i++) {
            List<SimpleIndexedEntity> result = RealmUtils.getSimpleIndexedEntityManager().queryBySimpleInt(lookupIndexedInts[i]);
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        RealmUtils.getSimpleIndexedEntityManager().endTransaction();
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RealmUtils.getSimpleIndexedEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryById(boolean randomIds) {
        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        RealmUtils.getSimpleEntityManager().insertMultiObject(list);
        stopBenchmark();

        assertEntityCount(RealmUtils.getSimpleEntityManager().getAllCount());

        long[] lookupIds = getLookupIds(randomIds);
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            SimpleEntity entity = RealmUtils.getSimpleEntityManager().queryById(lookupIds[i]);
            if (entity != null) {
                access(entity);
            } else {
                LogUtils.e(getName() + "queryEntityById empty");
            }
        }
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        RealmUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    /**************************没有索引的表****************************/

    private List<SimpleEntity> createEntityList(boolean onlyPrimitive) {
        List<SimpleEntity> list = new ArrayList<>((int) mCount);
        for (int i = 0; i < mCount; i++) {
            list.add(createEntity((long) i, onlyPrimitive));
        }
        return list;
    }

    private SimpleEntity createEntity(Long _id, boolean onlyPrimitive) {
        SimpleEntity entity = new SimpleEntity();
        if (_id != null) {
            entity.setId(_id);
        }
        if (onlyPrimitive) {
            setRandomPrimitive(entity);
        } else {
            setRandomPrimitiveAndWrapper(entity);
        }
        return entity;
    }

    private void setRandomPrimitive(SimpleEntity entity) {
        entity.setSimpleBoolean(randomBoolean());
        entity.setSimpleByte(randomByte());
        entity.setSimpleShort(randomShort());
        entity.setSimpleInt(randomInt());
        entity.setSimpleLong(randomLong());
        entity.setSimpleFloat(randomFloat());
        entity.setSimpleDouble(randomDouble());
    }

    private void setRandomPrimitiveAndWrapper(SimpleEntity entity) {
        setRandomPrimitive(entity);
        entity.setSimpleString(randomString());
        entity.setSimpleByteArray(randomByteArrays());
    }

    private void updateEntityList(List<SimpleEntity> list, boolean onlyPrimitive) {
        for (SimpleEntity entity : list) {
            if (onlyPrimitive) {
                setRandomPrimitive(entity);
            } else {
                setRandomPrimitiveAndWrapper(entity);
            }
        }
    }

    private void access(SimpleEntity entity) {
        entity.getId();
        entity.getSimpleBoolean();
        entity.getSimpleByte();
        entity.getSimpleShort();
        entity.getSimpleInt();
        entity.getSimpleLong();
        entity.getSimpleFloat();
        entity.getSimpleDouble();
        entity.getSimpleString();
        entity.getSimpleByteArray();
    }

    private void accessAll(List<SimpleEntity> list) {
        for (SimpleEntity entity : list) {
            access(entity);
        }
    }

    private String[] getLookupStrings(List<SimpleEntity> list) {
        String[] strings = new String[(int) mCount];
        for (int i = 0; i < mCount; i++) {
            String text = "";
            while (text.length() < 2) {
                text = list.get(randomInt((int) mCount)).getSimpleString();
            }
            strings[i] = text;
        }
        return strings;
    }

    private int[] getLookupInts(List<SimpleEntity> list) {
        int[] ints = new int[(int) mCount];
        for (int i = 0; i < mCount; i++) {
            ints[i] = list.get(randomInt((int) mCount)).getSimpleInt();
        }
        return ints;
    }

    private long[] getLookupIds(boolean randomIds) {
        long[] ids = new long[(int) mCount];
        for (int i = 0; i < mCount; i++) {
            ids[i] = randomIds ? randomInt((int) mCount) : i;
        }
        return ids;
    }

    /**************************有索引的表****************************/

    private List<SimpleIndexedEntity> createIndexedEntityList() {
        List<SimpleIndexedEntity> list = new ArrayList<>((int) mCount);
        for (int i = 0; i < mCount; i++) {
            list.add(createIndexedEntity((long) i));
        }
        return list;
    }

    private SimpleIndexedEntity createIndexedEntity(Long _id) {
        SimpleIndexedEntity entity = new SimpleIndexedEntity();
        if (_id != null) {
            entity.setId(_id);
        }
        setRandomPrimitiveAndWrapper(entity);
        return entity;
    }

    private void setRandomPrimitiveAndWrapper(SimpleIndexedEntity entity) {
        entity.setSimpleBoolean(randomBoolean());
        entity.setSimpleByte(randomByte());
        entity.setSimpleShort(randomShort());
        entity.setSimpleInt(randomInt());
        entity.setSimpleLong(randomLong());
        entity.setSimpleFloat(randomFloat());
        entity.setSimpleDouble(randomDouble());
        entity.setSimpleString(randomString());
        entity.setSimpleByteArray(randomByteArrays());
    }

    private void updateEntityList(List<SimpleIndexedEntity> list) {
        for (SimpleIndexedEntity entity : list) {
            setRandomPrimitiveAndWrapper(entity);
        }
    }

    private void accessAllIndexed(List<SimpleIndexedEntity> list) {
        for (SimpleIndexedEntity entity : list) {
            entity.getId();
            entity.getSimpleBoolean();
            entity.getSimpleByte();
            entity.getSimpleShort();
            entity.getSimpleInt();
            entity.getSimpleLong();
            entity.getSimpleFloat();
            entity.getSimpleDouble();
            entity.getSimpleString();
            entity.getSimpleByteArray();
        }
    }

    private String[] getLookupIndexedStrings(List<SimpleIndexedEntity> list) {
        String[] strings = new String[(int) mCount];
        for (int i = 0; i < mCount; i++) {
            String text = "";
            while (text.length() < 2) {
                text = list.get(randomInt((int) mCount)).getSimpleString();
            }
            strings[i] = text;
        }
        return strings;
    }

    private int[] getLookupIndexedInts(List<SimpleIndexedEntity> list) {
        int[] ints = new int[(int) mCount];
        for (int i = 0; i < mCount; i++) {
            ints[i] = list.get(randomInt((int) mCount)).getSimpleInt();
        }
        return ints;
    }

    @Override
    public void gc() {
        super.gc();
        RealmUtils.closeAllDataBase();
        boolean deleted = RealmUtils.deleteAllFiles();
        log(getName() + " DB deleted: " + deleted + "\n");
    }
}
