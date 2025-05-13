package com.fms.models;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

@Data
public class CustomerResponse {
    private Page<CustomerModel> customerModels;
    private Optional<OffsetDateTime> lastUpdatedAt;
    private BigDecimal totalBalance;
}
