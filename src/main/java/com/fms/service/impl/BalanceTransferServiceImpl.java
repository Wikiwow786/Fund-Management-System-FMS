package com.fms.service.impl;

import com.fms.entities.*;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.BalanceTransferMapper;
import com.fms.models.BalanceTransferModel;
import com.fms.models.BalanceTransferRequestModel;
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

import java.sql.Time;
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
    public Page<BalanceTransferModel> getAllBalanceTransfers(BalanceTransferRequestModel balanceTransferRequestModel, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(balanceTransferRequestModel.getSearch())){
            filter.and(QBalanceTransfer.balanceTransfer.createdBy.name.equalsIgnoreCase(balanceTransferRequestModel.getSearch()));
        }
        if(StringUtils.isNotBlank(balanceTransferRequestModel.getBankName())){
            filter.and(QBalanceTransfer.balanceTransfer.sourceBank.bankName.equalsIgnoreCase(balanceTransferRequestModel.getBankName()));
        }
        if(StringUtils.isNotBlank(balanceTransferRequestModel.getTargetBank())){
            filter.and(QBalanceTransfer.balanceTransfer.targetBank.bankName.equalsIgnoreCase(balanceTransferRequestModel.getTargetBank()));
        }
        if(balanceTransferRequestModel.getAmount() != null){
            filter.and(QBalanceTransfer.balanceTransfer.amount.goe(balanceTransferRequestModel.getAmount()));
        }
        if(!ObjectUtils.isEmpty(balanceTransferRequestModel.getDateFrom()) && !ObjectUtils.isEmpty(balanceTransferRequestModel.getDateTo())){
            filter.and(QBalanceTransfer.balanceTransfer.transferDate.goe(balanceTransferRequestModel.getDateFrom()))
                    .and(QBalanceTransfer.balanceTransfer.transferDate.loe(balanceTransferRequestModel.getDateTo()));
        }
        if(!ObjectUtils.isEmpty(balanceTransferRequestModel.getTimeFrom()) && !ObjectUtils.isEmpty(balanceTransferRequestModel.getTimeTo())){
            filter.and(QBalanceTransfer.balanceTransfer.transferTime.after(Time.valueOf(balanceTransferRequestModel.getTimeFrom())))
                    .and(QBalanceTransfer.balanceTransfer.transferTime.before(Time.valueOf(balanceTransferRequestModel.getTimeTo())));
        }

        if(null != balanceTransferRequestModel.getCustomerId()){
            filter.and(QBalanceTransfer.balanceTransfer.sourceCustomer.customerId.eq(balanceTransferRequestModel.getCustomerId()));
        }

        if(StringUtils.isNotBlank(balanceTransferRequestModel.getCustomerName())){
            filter.and(QBalanceTransfer.balanceTransfer.sourceCustomer.customerName.equalsIgnoreCase(balanceTransferRequestModel.getCustomerName()));

        }

        if(StringUtils.isNotBlank(balanceTransferRequestModel.getTargetCustomer())){
            filter.and(QBalanceTransfer.balanceTransfer.targetCustomer.customerName.equalsIgnoreCase(balanceTransferRequestModel.getTargetCustomer()));

        }

        if(null != balanceTransferRequestModel.getTargetCustomerId()){
            filter.and(QBalanceTransfer.balanceTransfer.targetCustomer.customerId.eq(balanceTransferRequestModel.getTargetCustomerId()))
                    .or(QBalanceTransfer.balanceTransfer.sourceCustomer.customerId.eq(balanceTransferRequestModel.getTargetCustomerId()));
        }

        if(null != balanceTransferRequestModel.getBankId()){
            filter.and(QBalanceTransfer.balanceTransfer.sourceBank.bankId.eq(balanceTransferRequestModel.getBankId()));
        }

        if(null != balanceTransferRequestModel.getTargetBankId()){
            filter.and(QBalanceTransfer.balanceTransfer.targetBank.bankId.eq(balanceTransferRequestModel.getTargetBankId()))
                    .or(QBalanceTransfer.balanceTransfer.sourceBank.bankId.eq(balanceTransferRequestModel.getTargetBankId()));
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
        transactionService.createOrUpdate(transactionModel, null,false, securityUser);
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
