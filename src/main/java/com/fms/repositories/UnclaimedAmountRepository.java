package com.fms.repositories;

import com.fms.entities.UnclaimedAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UnclaimedAmountRepository extends JpaRepository<UnclaimedAmount,Long>, QuerydslPredicateExecutor<UnclaimedAmount> {
}
