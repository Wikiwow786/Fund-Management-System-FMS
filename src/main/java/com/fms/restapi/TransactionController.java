package com.fms.restapi;

import com.fms.entities.Transaction;
import com.fms.models.TransactionModel;
import com.fms.security.SecurityUser;
import com.fms.service.TransactionService;
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

@RequestMapping(value = "/transactions")
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<TransactionModel>> fetchAll(
            @RequestParam(required = false) String search, @RequestParam(required = false)String customerName, @RequestParam(required = false)String bankName,@RequestParam(required = false) Transaction.TransactionType transactionType, @RequestParam(required = false) Long transactionId, @RequestParam(required = false) BigDecimal amount, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo,
            @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss") LocalTime timeFrom, @RequestParam(required = false)@DateTimeFormat(pattern = "hh:mm:ss") LocalTime timeTo,
            Pageable pageable) {

        return ResponseEntity.ok(transactionService.getAllTransactions(search,customerName,bankName,transactionType,transactionId,amount,dateFrom,dateTo,timeFrom,timeTo, pageable));
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionModel> fetch(@PathVariable(value = "transactionId") final Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionModel> save(@RequestBody TransactionModel transactionModel, @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(transactionService.createOrUpdate(transactionModel,null,true,securityUser));
    }

    @PutMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionModel> update(@PathVariable(value = "transactionId") final Long transactionId,
                                                       @RequestBody TransactionModel transactionModel,
                                                       @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(transactionService.createOrUpdate(transactionModel,transactionId,true,securityUser));
    }
}
