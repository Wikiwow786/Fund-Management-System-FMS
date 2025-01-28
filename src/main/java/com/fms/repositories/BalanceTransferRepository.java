package com.fms.repositories;

import com.fms.entities.BalanceTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BalanceTransferRepository extends JpaRepository<BalanceTransfer,Long>, QuerydslPredicateExecutor<BalanceTransfer> {
}
