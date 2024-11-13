package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long>, QuerydslPredicateExecutor<Bank> {

    List<Bank> findAllByBankIdIn(List<Long> bankIds);
}
