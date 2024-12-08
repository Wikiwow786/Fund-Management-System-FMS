
package com.fms.service;

import com.fms.entities.RevenueAccount;
import com.fms.models.RevenueAccountModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RevenueAccountService {

    RevenueAccountModel getRevenueAccount(Long revenueAccountId);

    Page<RevenueAccountModel> getAllRevenueAccounts(String revenueAccountName, RevenueAccount.RevenueAccountStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    RevenueAccountModel createOrUpdate(RevenueAccountModel revenueAccountModel, Long revenueAccountId);

}

