package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRevenueAccount is a Querydsl query type for RevenueAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRevenueAccount extends EntityPathBase<RevenueAccount> {

    private static final long serialVersionUID = -577108144L;

    public static final QRevenueAccount revenueAccount = new QRevenueAccount("revenueAccount");

    public final StringPath accountName = createString("accountName");

    public final NumberPath<Double> balance = createNumber("balance", Double.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final StringPath remarks = createString("remarks");

    public final NumberPath<Long> revenueAccountId = createNumber("revenueAccountId", Long.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath status = createString("status");

    public QRevenueAccount(String variable) {
        super(RevenueAccount.class, forVariable(variable));
    }

    public QRevenueAccount(Path<? extends RevenueAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRevenueAccount(PathMetadata metadata) {
        super(RevenueAccount.class, metadata);
    }

}

