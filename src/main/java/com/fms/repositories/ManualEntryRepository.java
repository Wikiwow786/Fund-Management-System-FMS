package com.fms.repositories;

import com.fms.entities.ManualEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ManualEntryRepository extends JpaRepository<ManualEntry,Long>, QuerydslPredicateExecutor<ManualEntry> {
}
