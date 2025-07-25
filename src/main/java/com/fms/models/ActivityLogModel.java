package com.fms.models;

import com.fms.entities.ActivityLog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ActivityLogModel {
    private Long logId;
    private Long userId;
    private String user;
    private String userName;
    private String action;
    private OffsetDateTime dateTime;
    public ActivityLogModel(ActivityLog activityLog) {
        this.logId = activityLog.getLogId();
        this.userId = activityLog.getUserId();
        this.action = activityLog.getAction();
        this.dateTime = activityLog.getDateTime();
        this.userName = activityLog.getUserName();
    }
}
