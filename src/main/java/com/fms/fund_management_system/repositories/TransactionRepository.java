package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
