package com.fms.repositories;

import com.fms.entities.BankSummaryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BankSummaryReportRepository extends JpaRepository<BankSummaryReport,Long>, QuerydslPredicateExecutor<BankSummaryReport> {
}
