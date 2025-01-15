package com.fms.service;

import com.fms.entities.Bank;
import com.fms.models.ManualEntryModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ManualEntryService {
    ManualEntryModel getManualEntry(Long manualEntryId);

    Page<ManualEntryModel> getAllManualEntries(String search, Pageable pageable);

    ManualEntryModel createOrUpdate(ManualEntryModel manualEntryModel, Long manualEntryId, SecurityUser securityUser);
}
