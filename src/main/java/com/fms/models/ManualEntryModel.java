package com.fms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.entities.ManualEntry;
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
    private String bankName;
    private Long transactionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date entryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
    private Time entryTime;
    private BigDecimal amount;
    private ManualEntry.ManualEntryType entryType;
    private ManualEntry.ManualEntryStatus status;
    private String remark;

    private String createdByUser;

    public ManualEntryModel(ManualEntry manualEntry) {
        super(manualEntry);
        this.manualEntryId = manualEntry.getManualEntryId();
        this.bankId = manualEntry.getBank() != null ? manualEntry.getBank().getBankId() : null;
        this.transactionId = manualEntry.getTransaction() != null ? manualEntry.getTransaction().getTransactionId() : null;
        this.entryDate = manualEntry.getEntryDate();
        this.entryTime = manualEntry.getEntryTime();
        this.amount = manualEntry.getAmount();
        this.entryType = manualEntry.getEntryType();
        this.status = manualEntry.getStatus();
        this.remark = manualEntry.getRemark();
        this.createdByUser = manualEntry.getCreatedBy().getName();
        this.bankName = manualEntry.getBank() != null ? manualEntry.getBank().getBankName() : null;
    }

}

