
package com.fms.service.impl;

import com.fms.entities.QRevenueAccount;
import com.fms.entities.RevenueAccount;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.RevenueAccountMapper;
import com.fms.models.RevenueAccountModel;
import com.fms.repositories.RevenueAccountRepository;
import com.fms.service.RevenueAccountService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

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
        if(StringUtils.isNotBlank(search)){
            BooleanExpression searchCondition =
                    QRevenueAccount.revenueAccount.name.containsIgnoreCase(search);
            filter.and(searchCondition);
        }

        if(status != null){
            filter.and(QRevenueAccount.revenueAccount.status.eq(status));

        }
        if(startDate != null){
            filter.and(QRevenueAccount.revenueAccount.startDate.goe(startDate));
        }
        if(endDate != null){
            filter.and(QRevenueAccount.revenueAccount.startDate.loe(startDate));
        }

        return revenueAccountRepository.findAll(filter, pageable).map(RevenueAccountModel::new);
    }

    @Override
    public RevenueAccountModel createOrUpdate(RevenueAccountModel revenueAccountModel,Long revenueAccountId) {
        return new RevenueAccountModel(revenueAccountRepository.save(assemble(revenueAccountModel,revenueAccountId)));
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

