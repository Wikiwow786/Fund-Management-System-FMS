package com.fms.repositories;

import com.fms.entities.ExceptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExceptionListRepository extends JpaRepository<ExceptionList,Long>, QuerydslPredicateExecutor<ExceptionList> {
}
