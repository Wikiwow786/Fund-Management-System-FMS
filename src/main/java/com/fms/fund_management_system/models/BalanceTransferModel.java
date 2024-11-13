package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.BalanceTransfer;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Data
public class BalanceTransferModel {
    private Long transferId;
    private Date transferDate;
    private Time transferTime;
    private BigDecimal amount;
    private Long sourceCustomerId;
    private Long targetCustomerId;
    private Long sourceBankId;
    private Long targetBankId;
    private String remarks;

    // Constructor that takes a BalanceTransfer entity and initializes the model
    public BalanceTransferModel(BalanceTransfer balanceTransfer) {
        this.transferId = balanceTransfer.getTransferId();
        this.transferDate = balanceTransfer.getTransferDate();
        this.transferTime = balanceTransfer.getTransferTime();
        this.amount = balanceTransfer.getAmount();
        this.sourceCustomerId = balanceTransfer.getSourceCustomer() != null ? balanceTransfer.getSourceCustomer().getCustomerId() : null;
        this.targetCustomerId = balanceTransfer.getTargetCustomer() != null ? balanceTransfer.getTargetCustomer().getCustomerId() : null;
        this.sourceBankId = balanceTransfer.getSourceBank() != null ? balanceTransfer.getSourceBank().getBankId() : null;
        this.targetBankId = balanceTransfer.getTargetBank() != null ? balanceTransfer.getTargetBank().getBankId() : null;
        this.remarks = balanceTransfer.getRemarks();
    }
}
