
package com.fms.restapi;

import com.fms.entities.RevenueAccount;
import com.fms.models.RevenueAccountModel;
import com.fms.service.RevenueAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "/revenue-accounts")
@RestController
@RequiredArgsConstructor
public class RevenueAccountController {

    private final RevenueAccountService revenueAccountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RevenueAccountModel>> fetchAll(@RequestParam(required = false) String revenueAccountName,
                                                              @RequestParam(required = false) RevenueAccount.RevenueAccountStatus status,
                                                              @RequestParam(required = false) LocalDate startDate,
                                                              @RequestParam(required = false) LocalDate endDate, @RequestParam(required = false) String search,
                                                              Pageable pageable) {

        return ResponseEntity.ok(revenueAccountService.getAllRevenueAccounts(revenueAccountName,status,startDate,endDate, search, pageable));
    }

    @GetMapping(value = "/{revenueAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RevenueAccountModel> fetch(@PathVariable(value = "revenueAccountId") final Long revenueAccountId) {
        return ResponseEntity.ok(revenueAccountService.getRevenueAccount(revenueAccountId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RevenueAccountModel> save(@RequestBody RevenueAccountModel revenueAccountModel) {
        return ResponseEntity.ok(revenueAccountService.createOrUpdate(revenueAccountModel,null));
    }

    @PutMapping(value = "/{revenueAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RevenueAccountModel> update(@PathVariable(value = "revenueAccountId") final Long revenueAccountId,
                                                @RequestBody RevenueAccountModel revenueAccountModel) {
        return ResponseEntity.ok(revenueAccountService.createOrUpdate(revenueAccountModel,revenueAccountId));
    }

}

