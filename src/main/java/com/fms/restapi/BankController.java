
package com.fms.restapi;

import com.fms.entities.Bank;
import com.fms.models.BankModel;
import com.fms.models.TotalBankBalanceModel;
import com.fms.security.SecurityUser;
import com.fms.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequestMapping(value = "/banks")
@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TotalBankBalanceModel> fetchAll(@RequestParam(required = false) String bankName,
                                                          @RequestParam(required = false) Bank.BankStatus status,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
                                                          @RequestParam(required = false) String search,
                                                          Pageable pageable) {


       Page<BankModel> bankModels = bankService.getAllBanks(bankName,status,startDate,endDate, search, pageable);
        BigDecimal totalBankBalance = bankModels.stream()
                .map(BankModel::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        TotalBankBalanceModel totalBankBalanceModel = new TotalBankBalanceModel();
        totalBankBalanceModel.setBanks(bankModels);
        totalBankBalanceModel.setTotalBalance(totalBankBalance);
        return ResponseEntity.ok(totalBankBalanceModel);
    }

    @GetMapping(value = "/{bankId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankModel> fetch(@PathVariable(value = "bankId") final Long bankId) {
        return ResponseEntity.ok(bankService.getBank(bankId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankModel> save(@RequestBody BankModel bankModel, @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(bankService.createOrUpdate(bankModel,null,securityUser));
    }

    @PutMapping(value = "/{bankId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankModel> update(@PathVariable(value = "bankId") final Long bankId,
                                             @RequestBody BankModel bankModel,
                                            @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(bankService.createOrUpdate(bankModel,bankId,securityUser));
    }

}


