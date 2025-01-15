package com.fms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.entities.Customer;
import com.fms.entities.UnclaimedAmount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UnclaimedAmountModel extends BaseModel{
    private Long unclaimedId;
    private Long bankId;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date transactionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
    private Time transactionTime;
    private UnclaimedAmount.UnclaimedAmountStatus status;
    private Long claimedTransactionId;

    private Long customerId;
    private String remark;
    private String voidRemark;
    private String claimedBy;


    public UnclaimedAmountModel(UnclaimedAmount unclaimedAmount) {
        super(unclaimedAmount);
        this.unclaimedId = unclaimedAmount.getUnclaimedId();
        this.bankId = unclaimedAmount.getBank() != null ? unclaimedAmount.getBank().getBankId() : null;
        this.amount = unclaimedAmount.getAmount();
        this.transactionDate = unclaimedAmount.getTransactionDate();
        this.transactionTime = unclaimedAmount.getTransactionTime();
        this.status = unclaimedAmount.getStatus();
        this.claimedTransactionId = unclaimedAmount.getTransaction() != null ? unclaimedAmount.getTransaction().getTransactionId() : null;
        this.customerId = unclaimedAmount.getCustomer() != null ? unclaimedAmount.getCustomer().getCustomerId() : null;
        this.remark = unclaimedAmount.getRemark();
        this.voidRemark = unclaimedAmount.getVoidRemark();
        this.claimedBy = unclaimedAmount.getClaimedBy() != null ? unclaimedAmount.getClaimedBy().getCustomerName() : null;
    }

}
