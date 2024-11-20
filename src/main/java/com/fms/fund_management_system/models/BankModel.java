package com.fms.fund_management_system.models;

import com.fms.fund_management_system.entities.Bank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class BankModel extends BaseModel{
    private Long bankId;
    private String bankName;
    private Double balance;
    private Double balanceLimit;
    private Bank.BankStatus status;
    private String remarks;

    public BankModel(Bank bank){
        super(bank);
        this.bankId = bank.getBankId();
        this.bankName = bank.getBankName();
        this.balance = bank.getBalance();
        this.balanceLimit = bank.getBalanceLimit();
        this.status = bank.getStatus();
        this.remarks = bank.getRemarks();

    }
}
