package com.fms.models;

import com.fms.entities.MiddleManPayOut;
import com.fms.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MiddleManPayOutModel {

    private Long payoutId;
    private Long revenueAccountId;
    private Long bankId;
    private String revenueBankAccount;
    private BigDecimal payoutAmount;
    private OffsetDateTime createdAt;
    private User createdBy;
    private String fromBank;

    public MiddleManPayOutModel(MiddleManPayOut middleManPayOut){
        this.payoutId = middleManPayOut.getPayoutId();
        this.revenueAccountId = middleManPayOut.getRevenueAccount() != null ? middleManPayOut.getRevenueAccount().getRevenueAccountId() : null;
        this.bankId = middleManPayOut.getBank() != null ? middleManPayOut.getBank().getBankId() : null;
        this.payoutAmount = middleManPayOut.getPayoutAmount();
        this.createdAt = middleManPayOut.getCreatedAt();
        this.revenueBankAccount = middleManPayOut.getRevenueAccount() != null ? middleManPayOut.getRevenueAccount().getName() : null;
        this.fromBank = middleManPayOut.getBank() != null ? middleManPayOut.getBank().getBankName() : null;
    }
}
