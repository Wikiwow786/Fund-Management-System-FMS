
package com.fms.entities;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(schema ="fms", name = "bank")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bank_id")
    private Long bankId;

    @Column(name="bank_name")
    private String bankName;

    @Column(name="balance")
    private BigDecimal balance;

    @Column(name="balance_limit")
    private Double balanceLimit;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private BankStatus status;

    @Column(name="remarks")
    private String remarks;

    @Column(name = "start_date", nullable = false)
    private Date startDate;


    public enum BankStatus {
        ACTIVE,
        INACTIVE
    }

}

