package com.example.chivas.testorm.utils.orm;

import com.example.chivas.dbres.db.objectbox.entity.SimpleEntity;
import com.example.chivas.dbres.db.objectbox.entity.SimpleIndexedEntity;
import com.example.chivas.dbres.db.objectbox.manager.SimpleEntityManager;
import com.example.chivas.dbres.db.objectbox.utils.ObjectBoxUtils;
import com.example.chivas.dbres.utils.AppContext;
import com.example.chivas.dbres.utils.LogUtils;
import com.example.chivas.testorm.bean.OpModelType;
import com.example.chivas.testorm.bean.SQLOperate;
import com.example.chivas.testorm.utils.OrmRunner;

import java.util.ArrayList;
import java.util.List;

public class ObjectBoxOrm extends BaseOrm {

    private boolean onceLogged;

    @Override
    public String getName() {
        return "ObjectBox";
    }

    @Override
    public void setUp(OrmRunner runner) {
        super.setUp(runner);
        ObjectBoxUtils.init(AppContext.getAppContext());
        ObjectBoxUtils.closeAllDataBase();
        ObjectBoxUtils.deleteAllFiles();

        ObjectBoxUtils.init(AppContext.getAppContext());
        SimpleEntityManager manager = ObjectBoxUtils.getSimpleEntityManager();
        if (!onceLogged) {
            String versionNative = manager.getVersionNative();
            String versionJava = manager.getVersion();
            log("ObjectBox Version " + versionNative + "(Java:" + versionJava + ")");
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
                runQueryById(false, false);
                break;
            case OpModelType.QUERY_ID_RANDOM:
                runQueryById(true, false);
                break;
            default:
        }
    }

    private void runBatchCRUD(boolean onlyPrimitive) {
        List<SimpleEntity> list = createEntityList(onlyPrimitive);
        startBenchmark(SQLOperate.INSERT);
        ObjectBoxUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        updateEntityList(list, onlyPrimitive);
        startBenchmark(SQLOperate.UPDATE);
        ObjectBoxUtils.getSimpleEntityManager().updateMultiObjects(list);
        stopBenchmark();

        list = null;

        startBenchmark(SQLOperate.QUERY);
        List<SimpleEntity> reloaded = ObjectBoxUtils.getSimpleEntityManager().queryAll();
        stopBenchmark();

        startBenchmark(SQLOperate.ACCESS);
        accessAll(reloaded);
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        ObjectBoxUtils.getSimpleEntityManager().deleteMultiObjects(reloaded);
        stopBenchmark();
    }

    private void runBatchIndexedCRUD() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();
        startBenchmark(SQLOperate.INSERT);
        ObjectBoxUtils.getSimpleIndexedEntityManager().insertMultiObjects(list);
        stopBenchmark();

        updateEntityList(list);
        startBenchmark(SQLOperate.UPDATE);
        ObjectBoxUtils.getSimpleIndexedEntityManager().updateMultiObjects(list);
        stopBenchmark();

        list = null;

        startBenchmark(SQLOperate.QUERY);
        List<SimpleIndexedEntity> reloaded = ObjectBoxUtils.getSimpleIndexedEntityManager().queryAll();
        stopBenchmark();

        startBenchmark(SQLOperate.ACCESS);
        accessAllIndexed(reloaded);
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        ObjectBoxUtils.getSimpleIndexedEntityManager().deleteMultiObjects(reloaded);
        stopBenchmark();
    }

    private void runQueryByString() {
        if (mCount > 10000) {
            log("为避免等待时间过长，请减少数据量");
            return;
        }

        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        ObjectBoxUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        String[] lookupStrings = getLookupStrings(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleEntity> result = ObjectBoxUtils.getSimpleEntityManager().queryBySimpleString(lookupStrings[i]);
            accessAll(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        ObjectBoxUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByIndexedString() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();
        startBenchmark(SQLOperate.INSERT);
        ObjectBoxUtils.getSimpleIndexedEntityManager().insertMultiObjects(list);
        stopBenchmark();

        String[] lookupStrings = getLookupIndexedStrings(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleIndexedEntity> result = ObjectBoxUtils.getSimpleIndexedEntityManager().queryBySimpleString(lookupStrings[i]);
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        ObjectBoxUtils.getSimpleIndexedEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByInteger() {
        if (mCount > 10000) {
            log("为避免等待时间过长，请减少数据量");
            return;
        }

        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        ObjectBoxUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        int[] lookupInts = getLookupInts(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleEntity> result = ObjectBoxUtils.getSimpleEntityManager().queryBySimpleInt(lookupInts[i]);
            accessAll(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        ObjectBoxUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryByIndexedInteger() {
        List<SimpleIndexedEntity> list = createIndexedEntityList();
        startBenchmark(SQLOperate.INSERT);
        ObjectBoxUtils.getSimpleIndexedEntityManager().insertMultiObjects(list);
        stopBenchmark();

        int[] lookupIndexedInts = getLookupIndexedInts(list);
        long entitiesFound = 0;
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            List<SimpleIndexedEntity> result = ObjectBoxUtils.getSimpleIndexedEntityManager().queryBySimpleInt(lookupIndexedInts[i]);
            accessAllIndexed(result);
            entitiesFound += result.size();
        }
        stopBenchmark();
        log("Entities found : " + entitiesFound);

        startBenchmark(SQLOperate.DELETE);
        ObjectBoxUtils.getSimpleIndexedEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    private void runQueryById(boolean randomIds, boolean inReadTx) {
        List<SimpleEntity> list = createEntityList(false);
        startBenchmark(SQLOperate.INSERT);
        ObjectBoxUtils.getSimpleEntityManager().insertMultiObjects(list);
        stopBenchmark();

        assertEntityCount(ObjectBoxUtils.getSimpleEntityManager().getAllCount());

        long[] lookupIds = getLookupIds(randomIds);
        startBenchmark(SQLOperate.QUERY);
        for (int i = 0; i < mCount; i++) {
            SimpleEntity entity = inReadTx ? ObjectBoxUtils.getSimpleEntityManager().queryByIdInReadTx(lookupIds[i]) :
                    ObjectBoxUtils.getSimpleEntityManager().queryById(lookupIds[i]);
            if (entity != null) {
                access(entity);
            } else {
                LogUtils.e(getName() + "queryEntityById empty");
            }
        }
        stopBenchmark();

        startBenchmark(SQLOperate.DELETE);
        ObjectBoxUtils.getSimpleEntityManager().deleteMultiObjects(list);
        stopBenchmark();
    }

    /**************************没有索引的表****************************/

    private List<SimpleEntity> createEntityList(boolean onlyPrimitive) {
        List<SimpleEntity> list = new ArrayList<>((int) mCount);
        for (int i = 1; i < mCount + 1; i++) {
            list.add(createEntity((long) i, onlyPrimitive));
        }
        return list;
    }

    private SimpleEntity createEntity(Long _id, boolean onlyPrimitive) {
        SimpleEntity entity = new SimpleEntity();
        if (_id != null) {
            entity.set_id(_id);
        }
        LogUtils.d("ObjectBoxOrm : createEntity - _id = " + _id);
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
            ids[i] = randomIds ? randomInt((int) mCount - 1) + 1 : i + 1;
        }
        return ids;
    }

    /**************************有索引的表****************************/

    private List<SimpleIndexedEntity> createIndexedEntityList() {
        List<SimpleIndexedEntity> list = new ArrayList<>((int) mCount);
        for (int i = 1; i < mCount + 1; i++) {
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
        ObjectBoxUtils.closeAllDataBase();
        boolean deleted = ObjectBoxUtils.deleteAllFiles();
        log(getName() + " DB deleted: " + deleted + "\n");
    }
}
