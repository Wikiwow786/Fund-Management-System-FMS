package com.fms.restapi;

import com.fms.models.ManualEntryModel;
import com.fms.security.SecurityUser;
import com.fms.service.ManualEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/manual-entries")
@RestController
@RequiredArgsConstructor
public class ManualEntryController {
    private final ManualEntryService manualEntryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ManualEntryModel>> fetchAll(
                                                    @RequestParam(required = false) String search,
                                                    Pageable pageable) {

        return ResponseEntity.ok(manualEntryService.getAllManualEntries(search, pageable));
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
