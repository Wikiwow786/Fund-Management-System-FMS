package com.fms.service;

import com.fms.models.ActivityLogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ActivityLogService {
    ActivityLogModel getActivityLog(Long activityLogId);

    Page<ActivityLogModel> getAllActivityLogs(String user, LocalDate dateFrom, LocalDate dateTo,
                                              LocalTime timeFrom, LocalTime timeTo, Pageable pageable);
}
