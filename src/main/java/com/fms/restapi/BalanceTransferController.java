package com.fms.restapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fms.models.BalanceTransferModel;
import com.fms.security.SecurityUser;
import com.fms.service.BalanceTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@RestController
@RequestMapping(value = "/balance-transfers")
@RequiredArgsConstructor
public class BalanceTransferController {

    private final BalanceTransferService balanceTransferService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BalanceTransferModel>> fetchAll(
            @RequestParam(required = false) String search, @RequestParam(required = false) BigDecimal amount, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo,
            @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss") LocalTime timeFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss") LocalTime timeTo,
            Pageable pageable) {

        return ResponseEntity.ok(balanceTransferService.getAllBalanceTransfers(search,amount,dateFrom,dateTo,timeFrom,timeTo, pageable));
    }

    @GetMapping(value = "/{balanceTransferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceTransferModel> fetch(@PathVariable(value = "balanceTransferId") final Long balanceTransferId) {
        return ResponseEntity.ok(balanceTransferService.getBalanceTransfer(balanceTransferId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceTransferModel> save(@RequestBody BalanceTransferModel balanceTransferModel, @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(balanceTransferService.createOrUpdate(balanceTransferModel,null,securityUser));
    }

    @PutMapping(value = "/{balanceTransferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceTransferModel> update(@PathVariable(value = "balanceTransferId") final Long balanceTransferId,
                                                   @RequestBody BalanceTransferModel balanceTransferModel,
                                                   @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(balanceTransferService.createOrUpdate(balanceTransferModel,balanceTransferId,securityUser));
    }
}
