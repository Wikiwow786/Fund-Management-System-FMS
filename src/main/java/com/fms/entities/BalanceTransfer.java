
package com.fms.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(schema ="fms", name = "balance_transfer")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BalanceTransfer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;

    @Column(name = "transfer_date", nullable = false)
    private Date transferDate;

    @Column(name = "transfer_time", nullable = false)
    private Time transferTime;

    @Column(name = "amount", nullable = false,precision = 15, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "source_customer_id")
    private Customer sourceCustomer;

    @ManyToOne
    @JoinColumn(name = "target_customer_id")
    private Customer targetCustomer;

    @ManyToOne
    @JoinColumn(name = "source_bank_id")
    private Bank sourceBank;

    @ManyToOne
    @JoinColumn(name = "target_bank_id")
    private Bank targetBank;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "input_date")
    private LocalDate inputDate;

    @Column(name = "input_time")
    private LocalTime inputTime;


}

