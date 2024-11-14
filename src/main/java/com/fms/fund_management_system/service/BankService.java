package com.fms.fund_management_system.service;

import com.fms.fund_management_system.entities.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface BankService {
    Bank getBank(Long bankId);

    Page<Bank> getAllBanks(String bankName, String status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable);

    Bank createOrUpdate(Bank bank);

    void delete(Long bankId);

    void deleteInBulk(List<Long> BankIds);
}
