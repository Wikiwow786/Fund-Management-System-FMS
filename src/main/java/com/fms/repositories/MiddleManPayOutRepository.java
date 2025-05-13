package com.fms.repositories;

import com.fms.entities.MiddleManPayOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MiddleManPayOutRepository extends JpaRepository<MiddleManPayOut,Long>, QuerydslPredicateExecutor<MiddleManPayOut> {
}
