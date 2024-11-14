package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUnclaimedAmount is a Querydsl query type for UnclaimedAmount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUnclaimedAmount extends EntityPathBase<UnclaimedAmount> {

    private static final long serialVersionUID = -1951838437L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUnclaimedAmount unclaimedAmount = new QUnclaimedAmount("unclaimedAmount");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final QBank bank;

    public final QUser claimedBy;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QUser createdBy;

    public final QCustomer customer;

    public final StringPath remark = createString("remark");

    public final EnumPath<UnclaimedAmount.UnclaimedAmountStatus> status = createEnum("status", UnclaimedAmount.UnclaimedAmountStatus.class);

    public final DateTimePath<java.util.Date> transactionDate = createDateTime("transactionDate", java.util.Date.class);

    public final TimePath<java.sql.Time> transactionTime = createTime("transactionTime", java.sql.Time.class);

    public final NumberPath<Long> unclaimedId = createNumber("unclaimedId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QUser updatedBy;

    public final StringPath voidRemark = createString("voidRemark");

    public QUnclaimedAmount(String variable) {
        this(UnclaimedAmount.class, forVariable(variable), INITS);
    }

    public QUnclaimedAmount(Path<? extends UnclaimedAmount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUnclaimedAmount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUnclaimedAmount(PathMetadata metadata, PathInits inits) {
        this(UnclaimedAmount.class, metadata, inits);
    }

    public QUnclaimedAmount(Class<? extends UnclaimedAmount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bank = inits.isInitialized("bank") ? new QBank(forProperty("bank"), inits.get("bank")) : null;
        this.claimedBy = inits.isInitialized("claimedBy") ? new QUser(forProperty("claimedBy"), inits.get("claimedBy")) : null;
        this.createdBy = inits.isInitialized("createdBy") ? new QUser(forProperty("createdBy"), inits.get("createdBy")) : null;
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer"), inits.get("customer")) : null;
        this.updatedBy = inits.isInitialized("updatedBy") ? new QUser(forProperty("updatedBy"), inits.get("updatedBy")) : null;
    }

}

