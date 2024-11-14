package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.Transaction;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransactionModel {

    private Long transactionId;
    private Transaction.TransactionType transactionType;
    private Long bankId;
    private Long customerId;
    private BigDecimal amount;
    private Date transactionDate;
    private Time transactionTime;
    private Transaction.TransactionStatus status;
    private String remark;
    private Long externalId;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TransactionModel(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.transactionType = transaction.getTransactionType();
        this.bankId = transaction.getBank() != null ? transaction.getBank().getBankId() : null;
        this.customerId = transaction.getCustomer() != null ? transaction.getCustomer().getCustomerId() : null;
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
        this.transactionTime = transaction.getTransactionTime();
        this.status = transaction.getStatus();
        this.remark = transaction.getRemark();
        this.externalId = transaction.getExternalId();
        this.createdBy = transaction.getCreatedBy() != null ? transaction.getCreatedBy().getUserId() : null;
        this.updatedBy = transaction.getUpdatedBy() != null ? transaction.getUpdatedBy().getUserId() : null;
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
    }

}
