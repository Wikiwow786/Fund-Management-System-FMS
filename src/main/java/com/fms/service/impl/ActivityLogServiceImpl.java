package com.fms.service.impl;

import com.fms.entities.QActivityLog;
import com.fms.exception.ResourceNotFoundException;
import com.fms.models.ActivityLogModel;
import com.fms.repositories.ActivityLogRepository;
import com.fms.service.ActivityLogService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    @Override
    public ActivityLogModel getActivityLog(Long activityLogId) {
        return new ActivityLogModel(activityLogRepository.findById(activityLogId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<ActivityLogModel> getAllActivityLogs(String user, LocalDate dateFrom, LocalDate dateTo,
                                                     LocalTime timeFrom, LocalTime timeTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if (StringUtils.isNotBlank(user)) {
            filter.and(QActivityLog.activityLog.user.name.containsIgnoreCase(user));
        }

        if (dateFrom != null) {
            filter.and(QActivityLog.activityLog.dateTime.goe(dateFrom.atStartOfDay().atOffset(ZoneOffset.UTC)));
        }

        if (dateTo != null) {
            filter.and(QActivityLog.activityLog.dateTime.loe(dateTo.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC)));
        }

        if (timeFrom != null) {
            OffsetDateTime timeFromOffset = OffsetDateTime.now().withHour(timeFrom.getHour())
                    .withMinute(timeFrom.getMinute())
                    .withSecond(timeFrom.getSecond())
                    .withNano(0)
                    .withOffsetSameInstant(ZoneOffset.UTC);
            filter.and(QActivityLog.activityLog.dateTime.goe(timeFromOffset));
        }
        if (timeTo != null) {
            OffsetDateTime timeToOffset = OffsetDateTime.now().withHour(timeTo.getHour())
                    .withMinute(timeTo.getMinute())
                    .withSecond(timeTo.getSecond())
                    .withNano(0)
                    .withOffsetSameInstant(ZoneOffset.UTC);
            filter.and(QActivityLog.activityLog.dateTime.loe(timeToOffset));
        }
        return activityLogRepository.findAll(filter,pageable).map(ActivityLogModel::new);
    }
}
