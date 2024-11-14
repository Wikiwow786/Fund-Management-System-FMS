package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomer is a Querydsl query type for Customer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomer extends EntityPathBase<Customer> {

    private static final long serialVersionUID = -653323267L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomer customer = new QCustomer("customer");

    public final NumberPath<Double> balance = createNumber("balance", Double.class);

    public final NumberPath<Double> commissionInPercentage = createNumber("commissionInPercentage", Double.class);

    public final NumberPath<Double> commissionOutPercentage = createNumber("commissionOutPercentage", Double.class);

    public final QUser createdBy;

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final StringPath customerName = createString("customerName");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Double> fundInFeePercentage = createNumber("fundInFeePercentage", Double.class);

    public final NumberPath<Double> fundOutFeePercentage = createNumber("fundOutFeePercentage", Double.class);

    public final ListPath<BalanceTransfer, QBalanceTransfer> incomingTransfers = this.<BalanceTransfer, QBalanceTransfer>createList("incomingTransfers", BalanceTransfer.class, QBalanceTransfer.class, PathInits.DIRECT2);

    public final ListPath<BalanceTransfer, QBalanceTransfer> outgoingTransfers = this.<BalanceTransfer, QBalanceTransfer>createList("outgoingTransfers", BalanceTransfer.class, QBalanceTransfer.class, PathInits.DIRECT2);

    public final StringPath remarks = createString("remarks");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath status = createString("status");

    public final ListPath<Transaction, QTransaction> transactions = this.<Transaction, QTransaction>createList("transactions", Transaction.class, QTransaction.class, PathInits.DIRECT2);

    public final QUser updatedBy;

    public final QUser user;

    public QCustomer(String variable) {
        this(Customer.class, forVariable(variable), INITS);
    }

    public QCustomer(Path<? extends Customer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomer(PathMetadata metadata, PathInits inits) {
        this(Customer.class, metadata, inits);
    }

    public QCustomer(Class<? extends Customer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createdBy = inits.isInitialized("createdBy") ? new QUser(forProperty("createdBy"), inits.get("createdBy")) : null;
        this.updatedBy = inits.isInitialized("updatedBy") ? new QUser(forProperty("updatedBy"), inits.get("updatedBy")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

