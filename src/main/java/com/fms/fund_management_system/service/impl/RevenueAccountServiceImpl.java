
package com.fms.fund_management_system.service.impl;

import com.fms.fund_management_system.entities.QRevenueAccount;
import com.fms.fund_management_system.entities.RevenueAccount;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.mapper.RevenueAccountMapper;
import com.fms.fund_management_system.models.RevenueAccountModel;
import com.fms.fund_management_system.repositories.RevenueAccountRepository;
import com.fms.fund_management_system.service.RevenueAccountService;
import com.querydsl.core.BooleanBuilder;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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
    private final RevenueAccountMapper revenueAccountMapper;

    @Override
    public RevenueAccountModel getRevenueAccount(Long revenueAccountId) {
        return new RevenueAccountModel(revenueAccountRepository.findById(revenueAccountId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<RevenueAccountModel> getAllRevenueAccounts(String revenueAccountName, RevenueAccount.RevenueAccountStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(revenueAccountName)){
            filter.and(QRevenueAccount.revenueAccount.name.equalsIgnoreCase(revenueAccountName));
        }
        if(!ObjectUtils.isEmpty(status)){
            filter.and(QRevenueAccount.revenueAccount.status.eq(status));
        }
        return revenueAccountRepository.findAll(filter, pageable).map(RevenueAccountModel::new);
    }

    @Override
    public RevenueAccountModel createOrUpdate(RevenueAccountModel revenueAccountModel,Long revenueAccountId) {
        return new RevenueAccountModel(revenueAccountRepository.save(assemble(revenueAccountModel,revenueAccountId)));
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

    public RevenueAccount assemble(RevenueAccountModel revenueAccountModel, Long revenueAccountId){

        RevenueAccount revenueAccount;

        if (null != revenueAccountId) {
            revenueAccount = revenueAccountRepository.findById(revenueAccountId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        }
        else {
            revenueAccount = new RevenueAccount();
        }
        revenueAccountMapper.toEntity(revenueAccountModel, revenueAccount);
        return revenueAccount;
    }
}

