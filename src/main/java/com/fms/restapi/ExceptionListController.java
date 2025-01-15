package com.fms.restapi;

import com.fms.models.ExceptionListModel;
import com.fms.security.SecurityUser;
import com.fms.service.ExceptionListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@RestController
@RequestMapping(value = "/exception-list")
@RequiredArgsConstructor
public class ExceptionListController {
    private final ExceptionListService exceptionListService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ExceptionListModel>> fetchAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
            Pageable pageable) {

        return ResponseEntity.ok(exceptionListService.getAllExceptionLists(search, dateFrom,dateTo,pageable));
    }

    @GetMapping(value = "/{exceptionListId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExceptionListModel> fetch(@PathVariable(value = "exceptionListId") final Long exceptionListId) {
        return ResponseEntity.ok(exceptionListService.getExceptionList(exceptionListId));
    }

    @PutMapping(value = "/{exceptionListId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExceptionListModel> update(@PathVariable(value = "exceptionListId") final Long exceptionListId,
                                                   @RequestBody ExceptionListModel exceptionListModel,
                                                   @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(exceptionListService.createOrUpdate(exceptionListModel,exceptionListId,securityUser));
    }
}
