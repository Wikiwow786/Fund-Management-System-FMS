package com.fms.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class BalanceTransferRequestModel {

    private String search;
    private String bankName;
    private String targetBank;
    private BigDecimal amount;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate inputDate;

    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime inputTime;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateFrom;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateTo;

    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime timeFrom;

    @DateTimeFormat(pattern = "hh:mm:ss")
    private LocalTime timeTo;

    private Long customerId;
    private Long targetCustomerId;
    private Long bankId;
    private Long targetBankId;
    private String customerName;
    private String targetCustomer;
}
