package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBank is a Querydsl query type for Bank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBank extends EntityPathBase<Bank> {

    private static final long serialVersionUID = -1475313189L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBank bank = new QBank("bank");

    public final QBaseEntity _super;

    public final NumberPath<Double> balance = createNumber("balance", Double.class);

    public final NumberPath<Double> balanceLimit = createNumber("balanceLimit", Double.class);

    public final NumberPath<Long> bankId = createNumber("bankId", Long.class);

    public final StringPath bankName = createString("bankName");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt;

    public final QUser createdBy;

    public final StringPath remarks = createString("remarks");

    public final EnumPath<Bank.BankStatus> status = createEnum("status", Bank.BankStatus.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt;

    public final QUser updatedBy;

    public QBank(String variable) {
        this(Bank.class, forVariable(variable), INITS);
    }

    public QBank(Path<? extends Bank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBank(PathMetadata metadata, PathInits inits) {
        this(Bank.class, metadata, inits);
    }

    public QBank(Class<? extends Bank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = inits.isInitialized("createdBy") ? new QUser(forProperty("createdBy"), inits.get("createdBy")) : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = inits.isInitialized("updatedBy") ? new QUser(forProperty("updatedBy"), inits.get("updatedBy")) : null;
    }

}

