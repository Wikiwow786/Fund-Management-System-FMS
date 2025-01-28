package com.fms.repositories;

import com.fms.entities.CommissionSummaryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommissionSummaryReportRepository extends JpaRepository<CommissionSummaryReport,Long>, QuerydslPredicateExecutor<CommissionSummaryReport> {
}
