package com.fms.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@RequiredArgsConstructor
public class CustomerDashboardModel {
    private Long customerId;
    private String customerName;
    private BigDecimal balance;
    private BigDecimal transactionAmount;

    public CustomerDashboardModel(String customerName, BigDecimal balance, BigDecimal transactionAmount) {
        this.customerName = customerName;
        this.balance = balance;
        this.transactionAmount = transactionAmount;
    }
}
