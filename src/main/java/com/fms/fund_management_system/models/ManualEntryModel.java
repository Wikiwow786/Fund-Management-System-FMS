package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.ManualEntry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ManualEntryModel extends BaseModel{

    private Long manualEntryId;
    private Long bankId;
    private Long transactionId;
    private Date entryDate;
    private Time entryTime;
    private BigDecimal amount;
    private ManualEntry.ManualEntryType entryType;
    private ManualEntry.ManualEntryStatus status;
    private String remark;

    public ManualEntryModel(ManualEntry manualEntry) {
        super(manualEntry);
        this.manualEntryId = manualEntry.getManualEntryId();
        this.bankId = manualEntry.getBank() != null ? manualEntry.getBank().getBankId() : null;
        this.entryDate = manualEntry.getEntryDate();
        this.entryTime = manualEntry.getEntryTime();
        this.amount = manualEntry.getAmount();
        this.entryType = manualEntry.getEntryType();
        this.status = manualEntry.getStatus();
        this.remark = manualEntry.getRemark();
    }

}

