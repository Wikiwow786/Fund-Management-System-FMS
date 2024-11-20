package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBalanceTransfer is a Querydsl query type for BalanceTransfer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBalanceTransfer extends EntityPathBase<BalanceTransfer> {

    private static final long serialVersionUID = -551204376L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBalanceTransfer balanceTransfer = new QBalanceTransfer("balanceTransfer");

    public final QBaseEntity _super;

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt;

    // inherited
    public final QUser createdBy;

    public final StringPath remarks = createString("remarks");

    public final QBank sourceBank;

    public final QCustomer sourceCustomer;

    public final QBank targetBank;

    public final QCustomer targetCustomer;

    public final DateTimePath<java.util.Date> transferDate = createDateTime("transferDate", java.util.Date.class);

    public final NumberPath<Long> transferId = createNumber("transferId", Long.class);

    public final TimePath<java.sql.Time> transferTime = createTime("transferTime", java.sql.Time.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt;

    // inherited
    public final QUser updatedBy;

    public QBalanceTransfer(String variable) {
        this(BalanceTransfer.class, forVariable(variable), INITS);
    }

    public QBalanceTransfer(Path<? extends BalanceTransfer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBalanceTransfer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBalanceTransfer(PathMetadata metadata, PathInits inits) {
        this(BalanceTransfer.class, metadata, inits);
    }

    public QBalanceTransfer(Class<? extends BalanceTransfer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.sourceBank = inits.isInitialized("sourceBank") ? new QBank(forProperty("sourceBank"), inits.get("sourceBank")) : null;
        this.sourceCustomer = inits.isInitialized("sourceCustomer") ? new QCustomer(forProperty("sourceCustomer"), inits.get("sourceCustomer")) : null;
        this.targetBank = inits.isInitialized("targetBank") ? new QBank(forProperty("targetBank"), inits.get("targetBank")) : null;
        this.targetCustomer = inits.isInitialized("targetCustomer") ? new QCustomer(forProperty("targetCustomer"), inits.get("targetCustomer")) : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }

}

