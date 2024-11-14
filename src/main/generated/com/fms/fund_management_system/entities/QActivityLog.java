package com.fms.fund_management_system.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivityLog is a Querydsl query type for ActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivityLog extends EntityPathBase<ActivityLog> {

    private static final long serialVersionUID = -1801769674L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivityLog activityLog = new QActivityLog("activityLog");

    public final StringPath action = createString("action");

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> logId = createNumber("logId", Long.class);

    public final StringPath remarks = createString("remarks");

    public final QUser user;

    public QActivityLog(String variable) {
        this(ActivityLog.class, forVariable(variable), INITS);
    }

    public QActivityLog(Path<? extends ActivityLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActivityLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActivityLog(PathMetadata metadata, PathInits inits) {
        this(ActivityLog.class, metadata, inits);
    }

    public QActivityLog(Class<? extends ActivityLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

