package com.fms.fund_management_system.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.fund_management_system.entities.Bank;
import com.fms.fund_management_system.entities.User;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.repositories.BankRepository;
import com.fms.fund_management_system.repositories.UserRepository;
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
public class BankModel {
    private Long bankId;
    private String bankName;
    private Double balance;
    private Double balanceLimit;
    private String status;
    @JsonFormat( shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    private LocalDate endDate;
    private String remarks;

    public BankModel(Bank bank){
        this.bankId = bank.getBankId();
        this.bankName = bank.getBankName();
        this.balance = bank.getBalance();
        this.balanceLimit = bank.getBalanceLimit();
        this.status = bank.getStatus();
        this.startDate = bank.getStartDate();
        this.endDate = bank.getEndDate();
        this.remarks = bank.getRemarks();

    }

    public Bank assemble(Long bankId, AuthModel authModel) {
        this.bankId = bankId;
        return assemble(authModel);
    }

    public Bank assemble(AuthModel authModel){

        Bank bank;

        User user = BeanUtil.getBean(UserRepository.class).findById(authModel.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != this.getBankId()) {
            bank = BeanUtil.getBean(BankRepository.class).findById(this.getBankId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
           bank.setUpdatedBy(user);
        }
        else {
            bank = new Bank();
            bank.setCreatedBy(user);
        }
        BeanUtils.copyProperties(this, bank,"balance", "endDate");
        return bank;
    }
}
