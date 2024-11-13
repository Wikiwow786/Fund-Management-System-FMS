package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.ManualEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManualEntryRepository extends JpaRepository<ManualEntry,Long> {
}
