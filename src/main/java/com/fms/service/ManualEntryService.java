package com.fms.service;

import com.fms.entities.Bank;
import com.fms.entities.ManualEntry;
import com.fms.models.ManualEntryModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface ManualEntryService {
    ManualEntryModel getManualEntry(Long manualEntryId);

    Page<ManualEntryModel> getAllManualEntries(String search, String entryType, String createdBy,Date dateFrom, Date dateTo, LocalTime timeFrom, LocalTime timeTo, Pageable pageable);

    ManualEntryModel createOrUpdate(ManualEntryModel manualEntryModel, Long manualEntryId, SecurityUser securityUser);
}
