package com.fms.repositories;

import com.querydsl.core.types.Predicate;

import java.math.BigDecimal;

public interface CustomUnclaimedAmountRepository{
    BigDecimal getTotalUnclaimedAmount();
}
