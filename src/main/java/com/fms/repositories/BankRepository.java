package com.fms.repositories;

import com.fms.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank,Long>, QuerydslPredicateExecutor<Bank> {

    List<Bank> findAllByBankIdIn(List<Long> bankIds);
}
