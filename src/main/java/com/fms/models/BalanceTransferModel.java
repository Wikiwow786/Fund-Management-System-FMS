package com.fms.models;

import com.fms.entities.BalanceTransfer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BalanceTransferModel extends BaseModel{
    private Long transferId;
    private Date transferDate;
    private Time transferTime;
    private BigDecimal amount;

    private String bank;
    private Long sourceCustomerId;
    private String sourceCustomerName;
    private Long targetCustomerId;
    private String targetCustomerName;
    private Long sourceBankId;
    private String sourceBankName;
    private Long targetBankId;
    private String targetBankName;
    private String remarks;
    private String createdByUser;


    public BalanceTransferModel(BalanceTransfer balanceTransfer) {
        super(balanceTransfer);
        this.transferId = balanceTransfer.getTransferId();
        this.transferDate = balanceTransfer.getTransferDate();
        this.transferTime = balanceTransfer.getTransferTime();
        this.bank = balanceTransfer.getSourceBank() != null ? balanceTransfer.getSourceBank().getBankName() : null;
        this.amount = balanceTransfer.getAmount();
        this.sourceCustomerId = balanceTransfer.getSourceCustomer() != null ? balanceTransfer.getSourceCustomer().getCustomerId() : null;
        this.targetCustomerId = balanceTransfer.getTargetCustomer() != null ? balanceTransfer.getTargetCustomer().getCustomerId() : null;
        this.sourceBankId = balanceTransfer.getSourceBank() != null ? balanceTransfer.getSourceBank().getBankId() : null;
        this.targetBankId = balanceTransfer.getTargetBank() != null ? balanceTransfer.getTargetBank().getBankId() : null;
        this.remarks = balanceTransfer.getRemarks();
        this.createdAt = balanceTransfer.getCreatedAt();
        this.createdBy = balanceTransfer.getCreatedBy() != null ? balanceTransfer.getCreatedBy() : null;
        this.updatedBy = balanceTransfer.getUpdatedBy() != null ? balanceTransfer.getUpdatedBy() : null;
        this.sourceCustomerName = balanceTransfer.getSourceCustomer() != null ? balanceTransfer.getSourceCustomer().getCustomerName() : null;
        this.targetCustomerName = balanceTransfer.getTargetCustomer() != null ? balanceTransfer.getTargetCustomer().getCustomerName(): null;
        this.sourceBankName = balanceTransfer.getSourceBank() != null ? balanceTransfer.getSourceBank().getBankName() : null;
        this.targetBankName = balanceTransfer.getTargetBank() != null ? balanceTransfer.getTargetBank().getBankName() : null;
        this.createdByUser = balanceTransfer.getCreatedBy().getName();
    }
}
