package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.UnclaimedAmount;
import com.fms.fund_management_system.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UnclaimedAmountModel extends BaseModel{
    private Long unclaimedId;
    private Long bankId;
    private BigDecimal amount;
    private Date transactionDate;
    private Time transactionTime;
    private UnclaimedAmount.UnclaimedAmountStatus status;
    private Long claimedTransactionId;
    private String remark;
    private String voidRemark;
    private User claimedBy;


    public UnclaimedAmountModel(UnclaimedAmount unclaimedAmount) {
        super(unclaimedAmount);
        this.unclaimedId = unclaimedAmount.getUnclaimedId();
        this.bankId = unclaimedAmount.getBank() != null ? unclaimedAmount.getBank().getBankId() : null;
        this.amount = unclaimedAmount.getAmount();
        this.transactionDate = unclaimedAmount.getTransactionDate();
        this.transactionTime = unclaimedAmount.getTransactionTime();
        this.status = unclaimedAmount.getStatus();
        this.claimedTransactionId = unclaimedAmount.getTransaction() != null ? unclaimedAmount.getTransaction().getTransactionId() : null;
        this.remark = unclaimedAmount.getRemark();
        this.voidRemark = unclaimedAmount.getVoidRemark();
        this.claimedBy = unclaimedAmount.getClaimedBy() != null ? unclaimedAmount.getClaimedBy() : null;
    }

}
