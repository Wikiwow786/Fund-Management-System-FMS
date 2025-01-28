package com.fms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.entities.RevenueAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RevenueAccountModel {

    private Long revenueAccountId;
    private String name;
    private BigDecimal balance;
    private RevenueAccount.RevenueAccountStatus status;

    @JsonFormat( shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    private String remarks;

    public RevenueAccountModel(RevenueAccount revenueAccount) {
        this.revenueAccountId = revenueAccount.getRevenueAccountId();
        this.name = revenueAccount.getName();
        this.balance = revenueAccount.getBalance();
        this.status = revenueAccount.getStatus();
        this.startDate = revenueAccount.getStartDate();
        this.remarks = revenueAccount.getRemarks();
    }
}
