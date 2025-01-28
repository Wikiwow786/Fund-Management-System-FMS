package com.fms.service.impl;

import com.fms.entities.*;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.BalanceTransferMapper;
import com.fms.models.BalanceTransferModel;
import com.fms.models.TransactionModel;
import com.fms.repositories.BalanceTransferRepository;
import com.fms.repositories.BankRepository;
import com.fms.repositories.CustomerRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.BalanceTransferService;
import com.fms.service.TransactionService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BalanceTransferServiceImpl implements BalanceTransferService {

    private final BalanceTransferRepository balanceTransferRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final BalanceTransferMapper balanceTransferMapper;
    private final TransactionService transactionService;


    @Override
    public BalanceTransferModel getBalanceTransfer(Long balanceTransferId) {
        return new BalanceTransferModel(balanceTransferRepository.findById(balanceTransferId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<BalanceTransferModel> getAllBalanceTransfers(String search, BigDecimal amount, Date dateFrom, Date dateTo, LocalTime timeFrom, LocalTime timeTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QBalanceTransfer.balanceTransfer.sourceBank.bankName.equalsIgnoreCase(search))
                    .or(QBalanceTransfer.balanceTransfer.targetBank.bankName.equalsIgnoreCase(search))
                    .or(QBalanceTransfer.balanceTransfer.createdBy.name.equalsIgnoreCase(search));
        }
        if(amount != null){
            filter.and(QBalanceTransfer.balanceTransfer.amount.eq(amount));
        }
        if(!ObjectUtils.isEmpty(dateFrom) && !ObjectUtils.isEmpty(dateTo)){
            filter.and(QBalanceTransfer.balanceTransfer.transferDate.goe(dateFrom))
                    .and(QBalanceTransfer.balanceTransfer.transferDate.loe(dateTo));
        }
        if(!ObjectUtils.isEmpty(timeFrom) && !ObjectUtils.isEmpty(timeTo)){
            filter.and(QBalanceTransfer.balanceTransfer.transferTime.after(Time.valueOf(timeFrom)))
                    .and(QBalanceTransfer.balanceTransfer.transferTime.before(Time.valueOf(timeTo)));
        }

        return balanceTransferRepository.findAll(filter, pageable).map(BalanceTransferModel::new);
    }

    @Override
    public BalanceTransferModel createOrUpdate(BalanceTransferModel balanceTransferModel, Long balanceTransferId, SecurityUser securityUser) {
        return new BalanceTransferModel(balanceTransferRepository.save(assemble(balanceTransferModel, balanceTransferId, securityUser)));

    }

    public BalanceTransfer assemble(BalanceTransferModel balanceTransferModel, Long balanceTransferId, SecurityUser securityUser) {
        BalanceTransfer balanceTransfer;
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != balanceTransferId) {
            balanceTransfer = balanceTransferRepository.findById(balanceTransferId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            balanceTransfer.setUpdatedBy(user);
        } else {
            balanceTransfer = new BalanceTransfer();
            balanceTransfer.setCreatedBy(user);
            balanceTransfer.setTransferDate(new Date());
            balanceTransfer.setTransferTime(new Time(System.currentTimeMillis()));
        }
        balanceTransferMapper.toEntity(balanceTransferModel, balanceTransfer);
        if (null != balanceTransferModel.getSourceBankId()) {
            Bank bank = findBank(balanceTransferModel.getSourceBankId());
            balanceTransfer.setSourceBank(bank);
            createTransactionForBalanceTransfer(bank.getBankId(),null,balanceTransfer, Transaction.TransactionType.FUND_OUT, securityUser);
        }
        if (null != balanceTransferModel.getTargetBankId()) {
            Bank bank = findBank(balanceTransferModel.getTargetBankId());
            balanceTransfer.setTargetBank(bank);
            createTransactionForBalanceTransfer(bank.getBankId(),null,balanceTransfer, Transaction.TransactionType.FUND_IN, securityUser);
        }
        if (null != balanceTransferModel.getSourceCustomerId()) {
           Customer customer = findCustomer(balanceTransferModel.getSourceCustomerId());
            balanceTransfer.setSourceCustomer(customer);
            createTransactionForBalanceTransfer(null,customer.getCustomerId(),balanceTransfer, Transaction.TransactionType.FUND_OUT, securityUser);
        }
        if (null != balanceTransferModel.getTargetCustomerId()) {
            Customer customer = findCustomer(balanceTransferModel.getTargetCustomerId());
            balanceTransfer.setTargetCustomer(customer);
            createTransactionForBalanceTransfer(null,customer.getCustomerId(),balanceTransfer, Transaction.TransactionType.FUND_IN, securityUser);
        }

        return balanceTransfer;
    }

    private void createTransactionForBalanceTransfer(Long bankId,Long customerId,BalanceTransfer balanceTransfer, Transaction.TransactionType transactionType, SecurityUser securityUser) {
        TransactionModel transactionModel = buildTransactionModelForBalanceTransfer(bankId,customerId,balanceTransfer,transactionType);
        transactionService.createOrUpdate(transactionModel, null, securityUser);
    }

    private TransactionModel buildTransactionModelForBalanceTransfer(Long bankId,Long customerId,BalanceTransfer balanceTransfer,Transaction.TransactionType transactionType) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransactionDate(new Date());
        transactionModel.setTransactionTime(new Time(System.currentTimeMillis()));
        if(bankId != null) {
            transactionModel.setBankId(bankId);
        }
        if(customerId != null){
            transactionModel.setCustomerId(customerId);
        }
        transactionModel.setAmount(balanceTransfer.getAmount());
        transactionModel.setStatus(Transaction.TransactionStatus.COMPLETED);
        transactionModel.setTransactionType(transactionType);
        return transactionModel;
    }

    private Bank findBank(Long bankId) {
        return bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }

}
