package com.fms.service;

import com.fms.models.ActivityLogModel;
import com.fms.models.MiddleManPayOutModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public interface MiddleManPayOutService {
    Page<MiddleManPayOutModel> getAllPayOuts(String revenueAccount, String status, LocalDate date, Pageable pageable);

    void handleMiddleManPayOut(MiddleManPayOutModel middleManPayOutModel, SecurityUser securityUser);
}
