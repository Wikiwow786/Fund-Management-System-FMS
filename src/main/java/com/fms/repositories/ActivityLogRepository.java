package com.fms.repositories;

import com.fms.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ActivityLogRepository extends JpaRepository<ActivityLog,Long>, QuerydslPredicateExecutor<ActivityLog> {
}
