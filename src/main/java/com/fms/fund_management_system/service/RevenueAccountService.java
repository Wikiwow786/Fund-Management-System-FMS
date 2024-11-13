package com.fms.fund_management_system.service;

import com.fms.fund_management_system.entities.RevenueAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RevenueAccountService {

    RevenueAccount getRevenueAccount(Long revenueAccountId);

    Page<RevenueAccount> getAllRevenueAccounts(String revenueAccountName, String status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    RevenueAccount createOrUpdate(RevenueAccount revenueAccount);

    void delete(Long revenueAccountId);

    void deleteInBulk(List<Long> revenueAccountIds);
}
