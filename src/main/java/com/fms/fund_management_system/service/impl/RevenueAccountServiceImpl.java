
package com.fms.fund_management_system.service.impl;

import com.fms.fund_management_system.entities.QRevenueAccount;
import com.fms.fund_management_system.entities.RevenueAccount;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.repositories.RevenueAccountRepository;
import com.fms.fund_management_system.service.RevenueAccountService;
import com.querydsl.core.BooleanBuilder;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RevenueAccountServiceImpl implements RevenueAccountService {
    private final RevenueAccountRepository revenueAccountRepository;

    @Override
    public RevenueAccount getRevenueAccount(Long revenueAccountId) {
        return revenueAccountRepository.findById(revenueAccountId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    @Override
    public Page<RevenueAccount> getAllRevenueAccounts(String revenueAccountName, String status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        return revenueAccountRepository.findAll(filter, pageable);
    }

    @Override
    public RevenueAccount createOrUpdate(RevenueAccount revenueAccount) {
        return revenueAccountRepository.save(revenueAccount);
    }

    @Override
    public void delete(Long revenueAccountId) {
        revenueAccountRepository.delete(revenueAccountRepository.findById(revenueAccountId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public void deleteInBulk(List<Long> revenueAccountIds) {
        List<RevenueAccount> list = revenueAccountRepository.findAllByRevenueAccountIdIn(revenueAccountIds);

        for (RevenueAccount revenueAccount : list) {
            revenueAccountRepository.delete(revenueAccount);
        }
    }
}

