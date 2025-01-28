package com.fms.models;

import com.fms.entities.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CustomerModel extends BaseModel{

    private Long customerId;
    private String customerName;
    private BigDecimal balance;
    private Double fundInFeePct;
    private Double fundOutFeePct;
    private Double fundInCommissionPct;
    private Double fundOutCommissionPct;

    private Customer.CustomerStatus status;
    private Long middleManId;
    private String remarks;

    public CustomerModel(Customer customer){
        super(customer);
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getCustomerName();
        this.status = customer.getStatus();
        this.balance = customer.getBalance();
        this.fundInFeePct = customer.getFundInFeePct();
        this.fundOutFeePct = customer.getFundOutFeePct();
        this.fundInCommissionPct = customer.getFundInCommissionPct();
        this.fundOutCommissionPct = customer.getFundOutCommissionPct();
        this.middleManId = customer.getUser() != null ? customer.getUser().getUserId() : null;
        this.remarks = customer.getRemarks();
    }


}
