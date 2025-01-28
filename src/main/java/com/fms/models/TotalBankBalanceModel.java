package com.fms.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;

@Getter
@Setter
public class TotalBankBalanceModel {

    Page<BankModel> banks;
    BigDecimal totalBalance;
}
