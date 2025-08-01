package com.fms.service.impl;

import com.fms.entities.*;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.MiddleManPayOutMapper;
import com.fms.models.MiddleManPayOutModel;
import com.fms.models.TransactionModel;
import com.fms.repositories.BankRepository;
import com.fms.repositories.MiddleManPayOutRepository;
import com.fms.repositories.RevenueAccountRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.MiddleManPayOutService;
import com.fms.service.TransactionService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class MiddleManPayOutServiceImpl implements MiddleManPayOutService {
    private final MiddleManPayOutRepository middleManPayOutRepository;
    private final RevenueAccountRepository revenueAccountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final MiddleManPayOutMapper middleManPayOutMapper;
    private final TransactionService transactionService;

    @Override
    public Page<MiddleManPayOutModel> getAllPayOuts(String revenueAccount, String status, LocalDate date, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if (StringUtils.isNotBlank(revenueAccount)) {
            filter.and(QMiddleManPayOut.middleManPayOut.revenueAccount.name.containsIgnoreCase(revenueAccount));
        }
        if (date != null) {
            filter.and(QMiddleManPayOut.middleManPayOut.createdAt.goe(date.atStartOfDay().atOffset(ZoneOffset.UTC)));
        }
        if (StringUtils.isNotBlank(revenueAccount)) {
            filter.and(QMiddleManPayOut.middleManPayOut.revenueAccount.name.containsIgnoreCase(revenueAccount));
        }

        return middleManPayOutRepository.findAll(filter,pageable).map(MiddleManPayOutModel::new);
    }

    @Override
    public void handleMiddleManPayOut(MiddleManPayOutModel middleManPayOutModel, SecurityUser securityUser) {
        middleManPayOutRepository.save(assemble(middleManPayOutModel, securityUser));
    }

    public MiddleManPayOut assemble(MiddleManPayOutModel middleManPayOutModel, SecurityUser securityUser) {
        MiddleManPayOut middleManPayOut;
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        middleManPayOut = new MiddleManPayOut();
        middleManPayOut.setCreatedBy(user);
        middleManPayOut.setCreatedAt(OffsetDateTime.now());
        middleManPayOutMapper.toEntity(middleManPayOutModel, middleManPayOut);
        if (null != middleManPayOutModel.getBankId()) {
            middleManPayOut.setBank(bankRepository.findById(middleManPayOutModel.getBankId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
        if (null != middleManPayOutModel.getRevenueAccountId()) {
            RevenueAccount revenueAccount = revenueAccountRepository.findById(middleManPayOutModel.getRevenueAccountId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            revenueAccount.setBalance(revenueAccount.getBalance().subtract(middleManPayOutModel.getPayoutAmount()));
            revenueAccountRepository.save(revenueAccount);
            middleManPayOut.setRevenueAccount(revenueAccount);
        }
        createTransactionForMiddleManPayOut(middleManPayOutModel, securityUser);
        return middleManPayOut;
    }

    private void createTransactionForMiddleManPayOut(MiddleManPayOutModel middleManPayOutModel, SecurityUser securityUser) {
        TransactionModel transactionModel = buildTransactionModelForMiddleManPayOut(middleManPayOutModel);
        transactionService.createOrUpdate(transactionModel, null,false, securityUser);
    }

    private TransactionModel buildTransactionModelForMiddleManPayOut(MiddleManPayOutModel middleManPayOutModel) {
        TransactionModel transactionModel = new TransactionModel();

        transactionModel.setAmount(middleManPayOutModel.getPayoutAmount());
        transactionModel.setTransactionDate(new Date());
        transactionModel.setTransactionTime(new Time(System.currentTimeMillis()));

        if (middleManPayOutModel.getBankId() != null) {
            transactionModel.setTransactionType(Transaction.TransactionType.FUND_IN);
            transactionModel.setBankId(middleManPayOutModel.getBankId());
        }
        transactionModel.setStatus(Transaction.TransactionStatus.COMPLETED);
        return transactionModel;
    }
}
