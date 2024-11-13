package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.RevenueAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueAccountRepository extends JpaRepository<RevenueAccount,Long>, QuerydslPredicateExecutor<RevenueAccount> {
    List<RevenueAccount>findAllByRevenueAccountIdIn(List<Long> revenueAccountIds);
}
