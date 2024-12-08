package com.fms.repositories;

import com.fms.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog,Long> {
}
