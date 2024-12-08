
package com.fms.service;

import com.fms.entities.Bank;
import com.fms.models.BankModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface BankService {
    BankModel getBank(Long bankId);

    Page<BankModel> getAllBanks(String bankName, Bank.BankStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    BankModel createOrUpdate(BankModel bankModel, Long bankId, SecurityUser securityUser);

}

