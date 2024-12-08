package com.fms.models;

import com.fms.entities.BalanceTransfer;
import com.fms.entities.Customer;
import com.fms.entities.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerModel extends BaseModel{

    private Long customerId;
    private String customerName;
    private Double balance;
    private Double fundInFeePct;
    private Double fundOutFeePct;
    private Double commissionInPct;
    private Double commissionOutPct;

    private Customer.CustomerStatus status;
    private Long middleManId;

    private String remarks;
    private List<Transaction> transactions;
    private List<BalanceTransfer> outgoingTransfers;
    private List<BalanceTransfer> incomingTransfers;

    public CustomerModel(Customer customer){
        super(customer);
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getCustomerName();
        this.status = customer.getStatus();
        this.balance = customer.getBalance();
        this.fundInFeePct = customer.getFundInFeePct();
        this.fundOutFeePct = customer.getFundOutFeePct();
        this.commissionInPct = customer.getFundInCommissionPct();
        this.commissionOutPct = customer.getFundOutCommissionPct();
        this.middleManId = customer.getUser() != null ? customer.getUser().getUserId() : null;
        this.remarks = customer.getRemarks();
        this.transactions = customer.getTransactions();
        this.outgoingTransfers = customer.getOutgoingTransfers();
        this.incomingTransfers = customer.getIncomingTransfers();
    }


}
