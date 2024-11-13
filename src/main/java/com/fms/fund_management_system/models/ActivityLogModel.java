package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.ActivityLog;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ActivityLogModel {
    private Long logId;
    private Long userId;
    private String action;
    private LocalDateTime dateTime;
    private String remarks;

    // Constructor that takes an ActivityLog entity and initializes the model
    public ActivityLogModel(ActivityLog activityLog) {
        this.logId = activityLog.getLogId();
        this.userId = activityLog.getUser() != null ? activityLog.getUser().getUserId() : null;
        this.action = activityLog.getAction();
        this.dateTime = activityLog.getDateTime();
        this.remarks = activityLog.getRemarks();
    }
}
