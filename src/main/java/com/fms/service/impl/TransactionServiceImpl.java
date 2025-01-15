package com.fms.service.impl;

import com.fms.entities.QTransaction;
import com.fms.entities.Transaction;
import com.fms.entities.User;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.TransactionMapper;
import com.fms.models.TransactionModel;
import com.fms.repositories.BankRepository;
import com.fms.repositories.CustomerRepository;
import com.fms.repositories.TransactionRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
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
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;

    @Override
    public TransactionModel getTransaction(Long transactionId) {
        return new TransactionModel(transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<TransactionModel> getAllTransactions(String search, Date dateFrom, Date dateTo, Time timeFrom, Time timeTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QTransaction.transaction.customer.customerName.equalsIgnoreCase(search))
                    .or(QTransaction.transaction.bank.bankName.equalsIgnoreCase(search));
        }
        if(!ObjectUtils.isEmpty(dateFrom) && !ObjectUtils.isEmpty(dateTo)){
            filter.and(QTransaction.transaction.transactionDate.goe(dateFrom))
                    .and(QTransaction.transaction.transactionDate.loe(dateTo));
        }
        if(!ObjectUtils.isEmpty(timeFrom) && !ObjectUtils.isEmpty(timeTo)){
            filter.and(QTransaction.transaction.transactionTime.after(timeFrom))
                    .and(QTransaction.transaction.transactionTime.before(timeTo));
        }
        return transactionRepository.findAll(filter, pageable).map(TransactionModel::new);
    }

    @Override
    public TransactionModel createOrUpdate(TransactionModel transactionModel, Long transactionId, SecurityUser securityUser) {
        return new TransactionModel(transactionRepository.save(assemble(transactionModel,transactionId,securityUser)));
    }

    public Transaction assemble(TransactionModel transactionModel, Long transactionId, SecurityUser securityUser){
        Transaction transaction;
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != transactionId) {
            transaction = transactionRepository.findById(transactionId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            transaction.setUpdatedBy(user);
        }
        else {
            transaction = new Transaction();
            transaction.setCreatedBy(user);
        }
        transactionMapper.toEntity(transactionModel,transaction);
        if(null != transactionModel.getBankId()){
            transaction.setBank(bankRepository.findById(transactionModel.getBankId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
        if(null != transactionModel.getCustomerId()){
            transaction.setCustomer(customerRepository.findById(transactionModel.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));

        }
        return transaction;
    }
}
