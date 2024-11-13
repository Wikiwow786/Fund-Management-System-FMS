package com.fms.fund_management_system.restapi;

import com.fms.fund_management_system.models.AuthModel;
import com.fms.fund_management_system.models.RevenueAccountModel;
import com.fms.fund_management_system.resolver.AuthPrincipal;
import com.fms.fund_management_system.service.RevenueAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping(value = "/revenueAccounts")
@RestController
@RequiredArgsConstructor
public class RevenueAccountController {

    private final RevenueAccountService revenueAccountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RevenueAccountModel>> fetchAll(@RequestParam(required = false) String revenueAccountName,
                                                        @RequestParam(required = false) String status,
                                                        @RequestParam(required = false) LocalDate startDate,
                                                        @RequestParam(required = false) LocalDate endDate, @RequestParam(required = false) String search,
                                                        Pageable pageable) {

        return ResponseEntity.ok(revenueAccountService.getAllRevenueAccounts(revenueAccountName,status,startDate,endDate, search, pageable).map(RevenueAccountModel::new));
    }

    @GetMapping(value = "/{revenueAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RevenueAccountModel> fetch(@PathVariable(value = "revenueAccountId") final Long revenueAccountId) {
        return ResponseEntity.ok(new RevenueAccountModel(revenueAccountService.getRevenueAccount(revenueAccountId)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RevenueAccountModel> save(@RequestBody RevenueAccountModel revenueAccountModel) {
        return ResponseEntity.ok(new RevenueAccountModel(revenueAccountService.createOrUpdate(revenueAccountModel.assemble())));
    }

    @PutMapping(value = "/{revenueAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RevenueAccountModel> update(@PathVariable(value = "revenueAccountId") final Long revenueAccountId,
                                                @RequestBody RevenueAccountModel revenueAccountModel) {
        return ResponseEntity.ok(new RevenueAccountModel(revenueAccountService.createOrUpdate(revenueAccountModel.assemble(revenueAccountId))));
    }

    @DeleteMapping(value = "/{revenueAccountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable(value = "revenueAccountId") final Long revenueAccountId) {
        revenueAccountService.delete(revenueAccountId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteInBulk(@RequestBody List<Long> revenueAccountIds) {
        revenueAccountService.deleteInBulk(revenueAccountIds);
        return ResponseEntity.ok().build();
    }
}
