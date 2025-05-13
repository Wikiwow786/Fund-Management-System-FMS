package com.fms.restapi;

import com.fms.entities.ManualEntry;
import com.fms.models.ManualEntryModel;
import com.fms.security.SecurityUser;
import com.fms.service.ManualEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

@RequestMapping(value = "/manual-entries")
@RestController
@RequiredArgsConstructor
public class ManualEntryController {
    private final ManualEntryService manualEntryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ManualEntryModel>> fetchAll(
            @RequestParam(required = false) String search,@RequestParam(required = false) String entryType,@RequestParam(required = false) String createdBy, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo,
            @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss") LocalTime timeFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss") LocalTime timeTo, Pageable pageable) {

        return ResponseEntity.ok(manualEntryService.getAllManualEntries(search,entryType,createdBy,dateFrom,dateTo,timeFrom,timeTo, pageable));
    }

    @GetMapping(value = "/{manualEntryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualEntryModel> fetch(@PathVariable(value = "manualEntryId") final Long manualEntryId) {
        return ResponseEntity.ok(manualEntryService.getManualEntry(manualEntryId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualEntryModel> save(@RequestBody ManualEntryModel manualEntryModel, @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(manualEntryService.createOrUpdate(manualEntryModel,null,securityUser));
    }

    @PutMapping(value = "/{manualEntryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualEntryModel> update(@PathVariable(value = "manualEntryId") final Long manualEntryId,
                                            @RequestBody ManualEntryModel manualEntryModel,
                                            @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(manualEntryService.createOrUpdate(manualEntryModel,manualEntryId,securityUser));
    }
}
