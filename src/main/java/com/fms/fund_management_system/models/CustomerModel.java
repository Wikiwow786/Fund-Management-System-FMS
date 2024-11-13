package com.fms.fund_management_system.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.fund_management_system.entities.*;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.repositories.CustomerRepository;
import com.fms.fund_management_system.repositories.UserRepository;
import com.fms.fund_management_system.util.BeanUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerModel {

    private Long customerId;
    private String customerName;
    private Double balance;
    private Double fundInFeePercentage;
    private Double fundOutFeePercentage;
    private Double commissionInPercentage;
    private Double commissionOutPercentage;

    private String status;

    private Long middleManId;
    @JsonFormat( shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    private LocalDate endDate;
    private String remarks;
    private List<Transaction> transactions;
    private List<BalanceTransfer> outgoingTransfers;
    private List<BalanceTransfer> incomingTransfers;

    public CustomerModel(Customer customer){
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getCustomerName();
        this.status = customer.getStatus();
        this.balance = customer.getBalance();
        this.fundInFeePercentage = customer.getFundInFeePercentage();
        this.fundOutFeePercentage = customer.getFundOutFeePercentage();
        this.commissionInPercentage = customer.getCommissionInPercentage();
        this.commissionOutPercentage = customer.getCommissionOutPercentage();
        this.startDate = customer.getStartDate();
        this.middleManId = customer.getUser() != null ? customer.getUser().getUserId() : null;
        this.endDate = customer.getEndDate();
        this.remarks = customer.getRemarks();
        this.transactions = customer.getTransactions();
        this.outgoingTransfers = customer.getOutgoingTransfers();
        this.incomingTransfers = customer.getIncomingTransfers();
    }

    public Customer assemble(Long customerId, AuthModel authModel) {
        this.customerId = customerId;
        return assemble(authModel);
    }

    public Customer assemble(AuthModel authModel){

        Customer customer;

        User user = BeanUtil.getBean(UserRepository.class).findById(authModel.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != this.getCustomerId()) {
            customer = BeanUtil.getBean(CustomerRepository.class).findById(this.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            customer.setUpdatedBy(user);
        }
        else {
            customer = new Customer();
            customer.setCreatedBy(user);
        }
        BeanUtils.copyProperties(this, customer,"balance", "endDate", "incomingTransfers", "outgoingTransfers", "transactions");
        if(null != middleManId){
            customer.setUser(BeanUtil.getBean(UserRepository.class).findById(middleManId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
        return customer;
    }
}
