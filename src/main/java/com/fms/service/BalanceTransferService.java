package com.fms.service;

import com.fms.models.BalanceTransferModel;
import com.fms.models.BalanceTransferRequestModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

public interface BalanceTransferService {
    BalanceTransferModel getBalanceTransfer(Long balanceTransferId);

    Page<BalanceTransferModel> getAllBalanceTransfers(BalanceTransferRequestModel balanceTransferRequestModel, Pageable pageable);

    BalanceTransferModel createOrUpdate(BalanceTransferModel balanceTransferModel, Long balanceTransferId, SecurityUser securityUser);
}
