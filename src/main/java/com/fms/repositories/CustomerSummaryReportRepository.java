package com.fms.repositories;

import com.fms.entities.CustomerSummaryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CustomerSummaryReportRepository extends JpaRepository<CustomerSummaryReport,Long>, QuerydslPredicateExecutor<CustomerSummaryReport> {
}
