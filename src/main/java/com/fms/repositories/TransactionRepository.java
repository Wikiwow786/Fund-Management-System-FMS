package com.fms.repositories;

import com.fms.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<Transaction,Long>, QuerydslPredicateExecutor<Transaction> {
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.bank.id = :bankId AND t.status = 'COMPLETED'")
    BigDecimal sumAmountByBankId(@Param("bankId") Long bankId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.customer.id = :customerId AND t.status = 'COMPLETED'")
    BigDecimal sumAmountByCustomerId(@Param("customerId") Long customerId);
}
