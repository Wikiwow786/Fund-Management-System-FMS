package com.fms.repositories;

import com.fms.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TransactionRepository extends JpaRepository<Transaction,Long>, QuerydslPredicateExecutor<Transaction> {
}
