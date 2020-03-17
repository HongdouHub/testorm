package com.example.chivas.testorm.utils.orm;

import com.example.chivas.dbres.db.room.entity.SimpleEntity;
import com.example.chivas.dbres.db.room.entity.SimpleIndexedEntity;
import com.example.chivas.dbres.db.room.manager.SimpleEntityManager;
import com.example.chivas.dbres.db.room.utils.RoomUtils;
import com.example.chivas.dbres.utils.AppContext;
import com.example.chivas.dbres.utils.LogUtils;
import com.example.chivas.testorm.bean.OpModelType;
import com.example.chivas.testorm.bean.SQLOperate;
import com.example.chivas.testorm.utils.OrmRunner;

import java.util.ArrayList;
import java.util.List;

public class RoomOrm extends BaseOrm {

    private static final String DB_NAME = "room_1.db";  // 数据库名称
    private boolean onceLogged;
    
    @Override
    public String getName() {
        return "Room";
    }

    @Override
    public void setUp(OrmRunner runner) {
        super.setUp(runner);
        boolean deleted = AppContext.getAppContext().deleteDatabase(DB_NAME);
        if (deleted) {
            log("Room DB existed before start - deleted");
        }
        RoomUtils.init(AppContext.getAppContext());
        SimpleEntityManager manager = RoomUtils.getSimpleEntityManager();

        if (!onceLogged) {
            String sqLiteVersion = manager.getSQLiteVersion();
            log("SQLite Version " + sqLiteVersion);
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
        RoomUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        updateEntityList(list, onlyPrimitive);
        startBenchmark(SQLOperate.UPDATE);
        RoomUtils.getSimpleEntityManager().updateMultiObjects(list);
        stopBenchmark();

        list = null;

        startBenchmark(SQLOperate.QUERY);
        List<SimpleEntity> reloaded = RoomUtils.getSimpleEntityManager().queryAll();
        stopBenchmark();

        startBenchmark(SQLOperate.ACCESS);
        accessAll(reloaded);
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        RoomUtils.getSimpleEntityManager().deleteMultiObjects(reloaded);
        stopBenchmark();
    }

    private void runBatchIndexedCRUD() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();

        startBenchmark(SQLOperate.INSERT);
        RoomUtils.getSimpleIndexedEntityManager().insertMultiObjects(list);
        stopBenchmark();

        updateEntityList(list);
        startBenchmark(SQLOperate.UPDATE);
        RoomUtils.getSimpleIndexedEntityManager().updateMultiObjects(list);
        stopBenchmark();

        list = null;

        startBenchmark(SQLOperate.QUERY);
        List<SimpleIndexedEntity> reloaded = RoomUtils.getSimpleIndexedEntityManager().queryAll();
        stopBenchmark();

        startBenchmark(SQLOperate.ACCESS);
        accessAllIndexed(reloaded);
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        RoomUtils.getSimpleIndexedEntityManager().deleteMultiObjects(reloaded);
        stopBenchmark();
    }

    private void runQueryByString() {
        if (mCount > 10000) {
            log("为避免等待时间过长，请减少数据量");
            return;
        }

        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        RoomUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        String[] lookupStrings = getLookupStrings(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleEntity> result = RoomUtils.getSimpleEntityManager().queryBySimpleString(lookupStrings[i]);
            accessAll(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RoomUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByIndexedString() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();
        startBenchmark(SQLOperate.INSERT);
        RoomUtils.getSimpleIndexedEntityManager().insertMultiObjects(list);
        stopBenchmark();

        String[] lookupStrings = getLookupIndexedStrings(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleIndexedEntity> result = RoomUtils.getSimpleIndexedEntityManager().queryBySimpleString(lookupStrings[i]);
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RoomUtils.getSimpleIndexedEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByInteger() {
        if (mCount > 10000) {
            log("为避免等待时间过长，请减少数据量");
            return;
        }

        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        RoomUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        int[] lookupInts = getLookupInts(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleEntity> result = RoomUtils.getSimpleEntityManager().queryBySimpleInt(lookupInts[i]);
            accessAll(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RoomUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByIndexedInteger() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();
        startBenchmark(SQLOperate.INSERT);
        RoomUtils.getSimpleIndexedEntityManager().insertMultiObjects(list);
        stopBenchmark();

        int[] lookupIndexedInts = getLookupIndexedInts(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleIndexedEntity> result = RoomUtils.getSimpleIndexedEntityManager().queryBySimpleInt(lookupIndexedInts[i]);
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        RoomUtils.getSimpleIndexedEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryById(boolean randomIds) {
        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        RoomUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        assertEntityCount(RoomUtils.getSimpleEntityManager().getAllCount());

        long[] lookupIds = getLookupIds(randomIds);
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            SimpleEntity entity = RoomUtils.getSimpleEntityManager().queryById(lookupIds[i]);
            if (entity != null) {
                access(entity);
            } else {
                LogUtils.e(getName() + "queryEntityById empty");
            }
        }
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        RoomUtils.getSimpleEntityManager().deleteMultiObjects(list);
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
            entity.set_id(_id);
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
        entity.get_id();
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
            entity.set_id(_id);
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
            entity.get_id();
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
        RoomUtils.closeAllDataBase();
        boolean deleted = AppContext.getAppContext().deleteDatabase(DB_NAME);
        log(getName() + " DB deleted: " + deleted + "\n");
    }
}
