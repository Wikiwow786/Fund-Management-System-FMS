package com.fms.models;

import com.fms.entities.ExceptionList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionListModel {

    private Long exceptionId;
    private Long bankId;

    private String bankName;
    private String cause;
    private String solution;
    private OffsetDateTime createdAt;
    private String createdBy;
    private ExceptionList.ExceptionStatus status;

    private Double systemBalance;
    private Double inputBalance;
    private Double imbalanceAmount;

    public ExceptionListModel(ExceptionList exceptionList){
        this.exceptionId = exceptionList.getExceptionId();
        this.bankId = exceptionList.getBank() != null ? exceptionList.getBank().getBankId() : null;
        this.cause = exceptionList.getCause();
        this.solution = exceptionList.getSolution();
        this.status = exceptionList.getStatus();
        this.createdBy = exceptionList.getCreatedBy().getName();
        this.systemBalance = exceptionList.getSystemBalance();
        this.inputBalance = exceptionList.getInputBalance();
        this.imbalanceAmount = exceptionList.getImbalanceAmount();
        this.createdAt = exceptionList.getCreatedAt();
        this.bankName = exceptionList.getBank() != null ? exceptionList.getBank().getBankName() : null;
    }
}
