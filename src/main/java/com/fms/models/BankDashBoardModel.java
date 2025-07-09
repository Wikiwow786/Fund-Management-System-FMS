package com.fms.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class BankDashBoardModel {
    private Long bankId;

    private String bankName;
    private BigDecimal transactionAmount;
    private BigDecimal balance;
    private Double balanceLimit;

    public BankDashBoardModel(String bankName, BigDecimal transactionAmount, BigDecimal balance,Double balanceLimit) {
        this.bankName = bankName;
        this.transactionAmount = transactionAmount;
        this.balance = balance;
        this.balanceLimit = balanceLimit;
    }
}
