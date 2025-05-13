package com.fms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.entities.Transaction;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date transactionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
    private Time transactionTime;
    private Transaction.TransactionStatus status;
    private String remark;
    private String externalId;

    private String bankName;
    private String customerName;
    private String createdByUser;



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
        this.createdByUser = transaction.getCreatedBy().getName();
        this.bankName = transaction.getBank() != null ? transaction.getBank().getBankName() : null;
        this.customerName = transaction.getCustomer() != null ? transaction.getCustomer().getCustomerName() : null;

    }

}
