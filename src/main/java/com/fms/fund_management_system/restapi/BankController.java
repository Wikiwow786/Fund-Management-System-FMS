
package com.fms.fund_management_system.restapi;

import com.fms.fund_management_system.entities.Bank;
import com.fms.fund_management_system.models.BankModel;
import com.fms.fund_management_system.security.SecurityUser;
import com.fms.fund_management_system.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "/banks")
@RestController
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BankModel>> fetchAll(@RequestParam(required = false) String bankName,
                                                    @RequestParam(required = false) Bank.BankStatus status,
                                                    @RequestParam(required = false) LocalDate startDate,
                                                    @RequestParam(required = false) LocalDate endDate,
                                                    @RequestParam(required = false) String search,@AuthenticationPrincipal SecurityUser securityUser,
                                                    Pageable pageable) {

        return ResponseEntity.ok(bankService.getAllBanks(bankName,status,startDate,endDate, search, pageable));
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

    @DeleteMapping(value = "/{bankId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable(value = "bankId") final Long bankId) {
        bankService.delete(bankId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteInBulk(@RequestBody List<Long> bankIds) {
        bankService.deleteInBulk(bankIds);
        return ResponseEntity.ok().build();
    }

}


