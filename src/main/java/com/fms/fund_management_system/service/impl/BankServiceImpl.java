package com.fms.fund_management_system.service.impl;

import com.fms.fund_management_system.entities.Bank;
import com.fms.fund_management_system.entities.QBank;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.repositories.BankRepository;
import com.fms.fund_management_system.service.BankService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Override
    public Bank getBank(Long bankId) {
        return bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    @Override
    public Page<Bank> getAllBanks(String bankName, String status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QBank.bank.bankName.containsIgnoreCase(search))
                    .or(QBank.bank.status.containsIgnoreCase(search));
        }
        if (startDate != null) {
            filter.or(QBank.bank.startDate.goe(startDate));
        }

        if (endDate != null) {
            filter.or(QBank.bank.endDate.loe(endDate));
        }
        return bankRepository.findAll(filter, pageable);
    }

    @Override
    public Bank createOrUpdate(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public void delete(Long bankId) {
        bankRepository.delete(bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public void deleteInBulk(List<Long> bankIds) {
        List<Bank> list = bankRepository.findAllByBankIdIn(bankIds);

        for (Bank bank : list) {
            bankRepository.delete(bank);
        }
    }
}
