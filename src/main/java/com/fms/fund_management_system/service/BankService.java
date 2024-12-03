
package com.fms.fund_management_system.service;

import com.fms.fund_management_system.entities.Bank;
import com.fms.fund_management_system.models.BankModel;
import com.fms.fund_management_system.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface BankService {
    BankModel getBank(Long bankId);

    Page<BankModel> getAllBanks(String bankName, Bank.BankStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    BankModel createOrUpdate(BankModel bankModel, Long bankId, SecurityUser securityUser);

    void delete(Long bankId);

    void deleteInBulk(List<Long> bankIds);
}

