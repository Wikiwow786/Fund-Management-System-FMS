package com.fms.service;

import com.fms.entities.Transaction;
import com.fms.models.TransactionModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

public interface TransactionService {

    TransactionModel getTransaction(Long transactionId);

    Page<TransactionModel> getAllTransactions(String search, String customerName, String bankName, Transaction.TransactionType transactionType, Long transactionId, BigDecimal amount, Date dateFrom, Date dateTo, LocalTime timeFrom, LocalTime timeTo, Pageable pageable);

    TransactionModel createOrUpdate(TransactionModel transactionModel, Long transactionId,boolean isStandard, SecurityUser securityUser);
}
