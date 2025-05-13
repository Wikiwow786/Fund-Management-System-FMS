package com.fms.repositories;

import com.fms.entities.RevenueAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface RevenueAccountRepository extends JpaRepository<RevenueAccount,Long>, QuerydslPredicateExecutor<RevenueAccount> {
    List<RevenueAccount>findAllByRevenueAccountIdIn(List<Long> revenueAccountIds);
    RevenueAccount findByName(String name);
}
