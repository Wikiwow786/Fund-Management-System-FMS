package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManualEntry is a Querydsl query type for ManualEntry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManualEntry extends EntityPathBase<ManualEntry> {

    private static final long serialVersionUID = -1219158067L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManualEntry manualEntry = new QManualEntry("manualEntry");

    public final QBaseEntity _super;

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final QBank bank;

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt;

    // inherited
    public final QUser createdBy;

    public final QCustomer customer;

    public final DateTimePath<java.util.Date> entryDate = createDateTime("entryDate", java.util.Date.class);

    public final TimePath<java.sql.Time> entryTime = createTime("entryTime", java.sql.Time.class);

    public final EnumPath<ManualEntry.ManualEntryType> entryType = createEnum("entryType", ManualEntry.ManualEntryType.class);

    public final NumberPath<Long> manualEntryId = createNumber("manualEntryId", Long.class);

    public final StringPath remark = createString("remark");

    public final EnumPath<ManualEntry.ManualEntryStatus> status = createEnum("status", ManualEntry.ManualEntryStatus.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt;

    // inherited
    public final QUser updatedBy;

    public QManualEntry(String variable) {
        this(ManualEntry.class, forVariable(variable), INITS);
    }

    public QManualEntry(Path<? extends ManualEntry> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManualEntry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManualEntry(PathMetadata metadata, PathInits inits) {
        this(ManualEntry.class, metadata, inits);
    }

    public QManualEntry(Class<? extends ManualEntry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseEntity(type, metadata, inits);
        this.bank = inits.isInitialized("bank") ? new QBank(forProperty("bank"), inits.get("bank")) : null;
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }

}

