package com.fms.restapi;

import com.fms.models.ActivityLogModel;
import com.fms.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = "/activity-logs")
@RequiredArgsConstructor
public class ActivityLogController {
    private final ActivityLogService activityLogService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ActivityLogModel>> fetchAll(@RequestParam(required = false) String user, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
                                                           @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss")LocalTime timeFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss") LocalTime timeTo, Pageable pageable) {

        return ResponseEntity.ok(activityLogService.getAllActivityLogs(user, dateFrom, dateTo, timeFrom, timeTo, pageable));
    }

    @GetMapping(value = "/{activityLogId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityLogModel> fetch(@PathVariable(value = "activityLogId") final Long activityLogId) {
        return ResponseEntity.ok(activityLogService.getActivityLog(activityLogId));
    }
}
