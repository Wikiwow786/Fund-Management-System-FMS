package com.fms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.entities.Bank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class BankModel extends BaseModel{

    private Long bankId;
    private String bankName;
    private BigDecimal balance;
    private Double balanceLimit;
    private Bank.BankStatus status;

    private BigDecimal transactionAmount;
    private String remarks;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;

    public BankModel(Bank bank){
        super(bank);
        this.bankId = bank.getBankId();
        this.bankName = bank.getBankName();
        this.balance = bank.getBalance();
        this.balanceLimit = bank.getBalanceLimit();
        this.status = bank.getStatus();
        this.remarks = bank.getRemarks();
        this.startDate = bank.getStartDate();
    }
}
