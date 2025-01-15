package com.fms.service;

import com.fms.models.TransactionModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Time;
import java.util.Date;

public interface TransactionService {

    TransactionModel getTransaction(Long transactionId);

    Page<TransactionModel> getAllTransactions(String search, Date dateFrom, Date dateTo, Time timeFrom, Time timeTo, Pageable pageable);

    TransactionModel createOrUpdate(TransactionModel transactionModel, Long transactionId, SecurityUser securityUser);
}
