package com.fms.repositories;

import com.fms.entities.ManualEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManualEntryRepository extends JpaRepository<ManualEntry,Long> {
}
