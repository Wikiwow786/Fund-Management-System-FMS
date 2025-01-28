package com.fms.service;

import com.fms.models.BalanceTransferModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

public interface BalanceTransferService {
    BalanceTransferModel getBalanceTransfer(Long balanceTransferId);

    Page<BalanceTransferModel> getAllBalanceTransfers(String search, BigDecimal amount, Date dateFrom, Date dateTo, LocalTime timeFrom, LocalTime timeTo, Pageable pageable);

    BalanceTransferModel createOrUpdate(BalanceTransferModel balanceTransferModel, Long balanceTransferId, SecurityUser securityUser);
}
