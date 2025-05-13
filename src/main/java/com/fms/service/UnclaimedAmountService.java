package com.fms.service;

import com.fms.models.TotalUnclaimedAmountModel;
import com.fms.models.UnclaimedAmountModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

public interface UnclaimedAmountService {
    UnclaimedAmountModel getUnclaimedAmount(Long unclaimedAmountId);

    TotalUnclaimedAmountModel getAllUnclaimedAmounts(String search, BigDecimal amount, Date dateFrom, Date dateTo, LocalTime timeFrom, LocalTime timeTo, String status, String createdBy, String updatedBy, Pageable pageable);

    UnclaimedAmountModel createOrUpdate(UnclaimedAmountModel unclaimedAmountModel, Long unclaimedAmountId, SecurityUser securityUser);
}
