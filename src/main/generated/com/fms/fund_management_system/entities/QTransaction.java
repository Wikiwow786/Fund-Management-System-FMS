package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransaction is a Querydsl query type for Transaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransaction extends EntityPathBase<Transaction> {

    private static final long serialVersionUID = 1256773215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransaction transaction = new QTransaction("transaction");

    public final QBaseEntity _super;

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final QBank bank;

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt;

    // inherited
    public final QUser createdBy;

    public final QCustomer customer;

    public final StringPath externalId = createString("externalId");

    public final StringPath remark = createString("remark");

    public final EnumPath<Transaction.TransactionStatus> status = createEnum("status", Transaction.TransactionStatus.class);

    public final DateTimePath<java.util.Date> transactionDate = createDateTime("transactionDate", java.util.Date.class);

    public final NumberPath<Long> transactionId = createNumber("transactionId", Long.class);

    public final TimePath<java.sql.Time> transactionTime = createTime("transactionTime", java.sql.Time.class);

    public final EnumPath<Transaction.TransactionType> transactionType = createEnum("transactionType", Transaction.TransactionType.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt;

    // inherited
    public final QUser updatedBy;

    public QTransaction(String variable) {
        this(Transaction.class, forVariable(variable), INITS);
    }

    public QTransaction(Path<? extends Transaction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransaction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransaction(PathMetadata metadata, PathInits inits) {
        this(Transaction.class, metadata, inits);
    }

    public QTransaction(Class<? extends Transaction> type, PathMetadata metadata, PathInits inits) {
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

