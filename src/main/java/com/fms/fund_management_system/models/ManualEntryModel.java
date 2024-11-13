package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.ManualEntry;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
public class ManualEntryModel {

    private Long manualEntryId;
    private Long bankId;
    private Long customerId;
    private Date entryDate;
    private Time entryTime;
    private BigDecimal amount;
    private ManualEntry.ManualEntryType entryType;
    private ManualEntry.ManualEntryStatus status;
    private String remark;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor that takes a ManualEntry entity and initializes the model
    public ManualEntryModel(ManualEntry manualEntry) {
        this.manualEntryId = manualEntry.getManualEntryId();
        this.bankId = manualEntry.getBank() != null ? manualEntry.getBank().getBankId() : null;
        this.customerId = manualEntry.getCustomer() != null ? manualEntry.getCustomer().getCustomerId() : null;
        this.entryDate = manualEntry.getEntryDate();
        this.entryTime = manualEntry.getEntryTime();
        this.amount = manualEntry.getAmount();
        this.entryType = manualEntry.getEntryType();
        this.status = manualEntry.getStatus();
        this.remark = manualEntry.getRemark();
        this.createdBy = manualEntry.getCreatedBy() != null ? manualEntry.getCreatedBy().getUserId() : null;
        this.updatedBy = manualEntry.getUpdatedBy() != null ? manualEntry.getUpdatedBy().getUserId() : null;
        this.createdAt = manualEntry.getCreatedAt();
        this.updatedAt = manualEntry.getUpdatedAt();
    }

}

