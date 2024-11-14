package com.fms.fund_management_system.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.fund_management_system.entities.RevenueAccount;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.repositories.RevenueAccountRepository;
import com.fms.fund_management_system.util.BeanUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RevenueAccountModel {

    private Long revenueAccountId;
    private String accountName;
    private Double balance;
    private String status;

    @JsonFormat( shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    private LocalDate endDate;
    private String remarks;

    public RevenueAccountModel(RevenueAccount revenueAccount) {
        this.revenueAccountId = revenueAccount.getRevenueAccountId();
        this.accountName = revenueAccount.getAccountName();
        this.balance = revenueAccount.getBalance();
        this.status = revenueAccount.getStatus();
        this.startDate = revenueAccount.getStartDate();
        this.remarks = revenueAccount.getRemarks();
    }

    public RevenueAccount assemble(Long revenueAccountId) {
        this.revenueAccountId = revenueAccountId;
        return assemble();
    }

    public RevenueAccount assemble(){
        RevenueAccount revenueAccount;
        if (null != this.getRevenueAccountId()) {
            revenueAccount = BeanUtil.getBean(RevenueAccountRepository.class).findById(this.getRevenueAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        }
        else {
            revenueAccount = new RevenueAccount();
        }
        BeanUtils.copyProperties(this, revenueAccount,"balance", "endDate", "status");
        return revenueAccount;
    }
}
