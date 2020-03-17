
package com.example.chivas.dbres.db.objectbox.entity;

import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.ModelBuilder;
import io.objectbox.ModelBuilder.EntityBuilder;
import io.objectbox.model.PropertyFlags;
import io.objectbox.model.PropertyType;

// THIS CODE IS GENERATED BY ObjectBox, DO NOT EDIT.
/**
 * Starting point for working with your ObjectBox. All boxes are set up for your objects here.
 * <p>
 * First steps (Android): get a builder using {@link #builder()}, call {@link BoxStoreBuilder#androidContext(Object)},
 * and {@link BoxStoreBuilder#build()} to get a {@link BoxStore} to work with.
 */
public class MyObjectBox {

    public static BoxStoreBuilder builder() {
        BoxStoreBuilder builder = new BoxStoreBuilder(getModel());
        builder.entity(BillDes_.__INSTANCE);
        builder.entity(SimpleEntity_.__INSTANCE);
        builder.entity(SimpleIndexedEntity_.__INSTANCE);
        builder.entity(WaybillInfo_.__INSTANCE);
        return builder;
    }

    private static byte[] getModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.lastEntityId(4, 4867364385627723574L);
        modelBuilder.lastIndexId(2, 1874201762450735380L);
        modelBuilder.lastRelationId(0, 0L);

        EntityBuilder entityBuilder;

        entityBuilder = modelBuilder.entity("BillDes");
        entityBuilder.id(1, 7732132061680547093L).lastPropertyId(3, 1081975766053351452L);
        entityBuilder.flags(io.objectbox.model.EntityFlags.USE_NO_ARG_CONSTRUCTOR);
        entityBuilder.property("_id", PropertyType.Long).id(1, 3628983390935796456L)
            .flags(PropertyFlags.ID | PropertyFlags.ID_SELF_ASSIGNABLE | PropertyFlags.NOT_NULL);
        entityBuilder.property("billNo", PropertyType.String).id(2, 1401814377926620228L);
        entityBuilder.property("des", PropertyType.String).id(3, 1081975766053351452L);
        entityBuilder.entityDone();

        entityBuilder = modelBuilder.entity("SimpleEntity");
        entityBuilder.id(2, 1824315075072712021L).lastPropertyId(10, 2382216360172763600L);
        entityBuilder.property("_id", PropertyType.Long).id(1, 1863028349707167504L)
            .flags(PropertyFlags.ID | PropertyFlags.ID_SELF_ASSIGNABLE | PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleBoolean", PropertyType.Bool).id(2, 5829634777374711642L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleByte", PropertyType.Byte).id(3, 2713955822195291763L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleShort", PropertyType.Short).id(4, 4741341032733186063L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleInt", PropertyType.Int).id(5, 6202912444833733898L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleLong", PropertyType.Long).id(6, 4739518201528894959L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleFloat", PropertyType.Float).id(7, 957768191161752567L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleDouble", PropertyType.Double).id(8, 4072779183374511296L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleString", PropertyType.String).id(9, 6477821119731201882L);
        entityBuilder.property("simpleByteArray", PropertyType.ByteVector).id(10, 2382216360172763600L);
        entityBuilder.entityDone();

        entityBuilder = modelBuilder.entity("SimpleIndexedEntity");
        entityBuilder.id(3, 7607540918783140473L).lastPropertyId(10, 890929718936388641L);
        entityBuilder.flags(io.objectbox.model.EntityFlags.USE_NO_ARG_CONSTRUCTOR);
        entityBuilder.property("_id", PropertyType.Long).id(1, 9169649071090802751L)
            .flags(PropertyFlags.ID | PropertyFlags.ID_SELF_ASSIGNABLE | PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleBoolean", PropertyType.Bool).id(2, 4683844002572061012L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleByte", PropertyType.Byte).id(3, 808948629886421633L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleShort", PropertyType.Short).id(4, 8896258300261490143L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleInt", PropertyType.Int).id(5, 776028114379516819L)
            .flags(PropertyFlags.NOT_NULL | PropertyFlags.INDEXED).indexId(1, 8928434996462856548L);
        entityBuilder.property("simpleLong", PropertyType.Long).id(6, 7049618287096511271L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleFloat", PropertyType.Float).id(7, 5928581864664089577L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleDouble", PropertyType.Double).id(8, 2724684181854643701L)
            .flags(PropertyFlags.NOT_NULL);
        entityBuilder.property("simpleString", PropertyType.String).id(9, 4145711786858133684L)
            .flags(PropertyFlags.INDEX_HASH).indexId(2, 1874201762450735380L);
        entityBuilder.property("simpleByteArray", PropertyType.ByteVector).id(10, 890929718936388641L);
        entityBuilder.entityDone();

        entityBuilder = modelBuilder.entity("WaybillInfo");
        entityBuilder.id(4, 4867364385627723574L).lastPropertyId(11, 8594117113223780956L);
        entityBuilder.property("_id", PropertyType.Long).id(1, 4655897158812247693L)
            .flags(PropertyFlags.ID | PropertyFlags.ID_SELF_ASSIGNABLE | PropertyFlags.NOT_NULL);
        entityBuilder.property("goodsNo", PropertyType.String).id(2, 7986809494236516661L);
        entityBuilder.property("goodsType", PropertyType.String).id(3, 6627330553535161036L);
        entityBuilder.property("containerNo", PropertyType.String).id(4, 768143383674853263L);
        entityBuilder.property("carLogoNo", PropertyType.String).id(5, 3256272949318493582L);
        entityBuilder.property("transitDepotNo", PropertyType.String).id(6, 8076288792290618211L);
        entityBuilder.property("postNo", PropertyType.String).id(7, 88882482975335527L);
        entityBuilder.property("stationNo", PropertyType.String).id(8, 916439221558959845L);
        entityBuilder.property("operatorNo", PropertyType.String).id(9, 320001638379317375L);
        entityBuilder.property("opTime", PropertyType.Long).id(10, 1386451463538054736L)
            .flags(PropertyFlags.NON_PRIMITIVE_TYPE);
        entityBuilder.property("uploadFlag", PropertyType.String).id(11, 8594117113223780956L);
        entityBuilder.entityDone();

        return modelBuilder.build();
    }

}
