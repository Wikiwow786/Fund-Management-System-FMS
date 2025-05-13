
package com.fms.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Table(schema ="fms", name="customer")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Column(name = "fund_in_fee_pct")
    private Double fundInFeePct;

    @Column(name = "fund_out_fee_pct")
    private Double fundOutFeePct;

    @Column(name = "fund_in_commission_pct")
    private Double fundInCommissionPct;

    @Column(name = "fund_out_commission_pct")
    private Double fundOutCommissionPct;

    @Column(name = "remarks")
    private String remarks;

   /* @ManyToOne
    @JoinColumn(name = "middleman_id")
    private User user;*/

    @ManyToOne
    @JoinColumn(name = "revenue_account_id")
    private RevenueAccount revenueAccount;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "sourceCustomer")
    private List<BalanceTransfer> outgoingTransfers;

    @OneToMany(mappedBy = "targetCustomer")
    private List<BalanceTransfer> incomingTransfers;

    public enum CustomerStatus{
        ACTIVE,
        INACTIVE
    }
}

