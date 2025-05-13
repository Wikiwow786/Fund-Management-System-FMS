package com.fms.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

@Getter
@Setter
public class TotalBankBalanceModel {

    private Page<BankModel> banks;
    private BigDecimal totalBalance;
    private Optional<OffsetDateTime> lastUpdatedAt;

}
