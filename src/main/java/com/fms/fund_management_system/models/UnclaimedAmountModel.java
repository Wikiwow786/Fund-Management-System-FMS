package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.UnclaimedAmount;
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
public class UnclaimedAmountModel {
    private Long unclaimedId;
    private Long bankId;
    private BigDecimal amount;
    private Date transactionDate;
    private Time transactionTime;
    private UnclaimedAmount.UnclaimedAmountStatus status;
    private Long customerId;
    private String remark;
    private String voidRemark;
    private Long createdBy;
    private Long updatedBy;
    private Long claimedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UnclaimedAmountModel(UnclaimedAmount unclaimedAmount) {
        this.unclaimedId = unclaimedAmount.getUnclaimedId();
        this.bankId = unclaimedAmount.getBank() != null ? unclaimedAmount.getBank().getBankId() : null;
        this.amount = unclaimedAmount.getAmount();
        this.transactionDate = unclaimedAmount.getTransactionDate();
        this.transactionTime = unclaimedAmount.getTransactionTime();
        this.status = unclaimedAmount.getStatus();
        this.customerId = unclaimedAmount.getCustomer() != null ? unclaimedAmount.getCustomer().getCustomerId() : null;
        this.remark = unclaimedAmount.getRemark();
        this.voidRemark = unclaimedAmount.getVoidRemark();
        this.createdBy = unclaimedAmount.getCreatedBy() != null ? unclaimedAmount.getCreatedBy().getUserId() : null;
        this.updatedBy = unclaimedAmount.getUpdatedBy() != null ? unclaimedAmount.getUpdatedBy().getUserId() : null;
        this.claimedBy = unclaimedAmount.getClaimedBy() != null ? unclaimedAmount.getClaimedBy().getUserId() : null;
        this.createdAt = unclaimedAmount.getCreatedAt();
        this.updatedAt = unclaimedAmount.getUpdatedAt();
    }

}
