package com.example.chivas.testorm.bean;

public class OpModelType {

    public static final int CRUD_NORMAL = 1;
    public static final int CRUD_PRIMITIVE_TYPE = 2;
    public static final int CRUD_INDEXED = 3;
    public static final int QUERY_STRING = 4;
    public static final int QUERY_STRING_INDEX = 5;
    public static final int QUERY_INTEGER = 6;
    public static final int QUERY_INTEGER_INDEX = 7;
    public static final int QUERY_ID = 8;
    public static final int QUERY_ID_RANDOM = 9;

    public final String name;
    public final int type;

    public OpModelType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    public static OpModelType[] CRUD = {
            new OpModelType("普通", CRUD_NORMAL),
            new OpModelType("只有基本数据类型", CRUD_PRIMITIVE_TYPE),
            new OpModelType("包含索引", CRUD_INDEXED),
    };

    public static OpModelType[] QUERY_STR = {
            new OpModelType("没有索引", QUERY_STRING),
            new OpModelType("有索引", QUERY_STRING_INDEX),
    };

    public static OpModelType[] QUERY_INT = {
            new OpModelType("没有索引", QUERY_INTEGER),
            new OpModelType("有索引", QUERY_INTEGER_INDEX),
    };

    public static OpModelType[] QUERY_PRIMARY_ID = {
            new OpModelType("全量查询", QUERY_ID),
            new OpModelType("随机查询", QUERY_ID_RANDOM),
    };
}
