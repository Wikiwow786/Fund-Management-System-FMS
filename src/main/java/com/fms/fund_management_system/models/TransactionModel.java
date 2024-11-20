package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransactionModel extends BaseModel{

    private Long transactionId;
    private Transaction.TransactionType transactionType;
    private Long bankId;
    private Long customerId;
    private BigDecimal amount;
    private Date transactionDate;
    private Time transactionTime;
    private Transaction.TransactionStatus status;
    private String remark;
    private String externalId;


    public TransactionModel(Transaction transaction) {
        super(transaction);
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

    }

}
