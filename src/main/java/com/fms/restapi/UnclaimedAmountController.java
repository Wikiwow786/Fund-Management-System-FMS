package com.fms.restapi;

import com.fms.models.UnclaimedAmountModel;
import com.fms.security.SecurityUser;
import com.fms.service.UnclaimedAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/unclaimed-amounts")
@RestController
@RequiredArgsConstructor
public class UnclaimedAmountController {
    
    private final UnclaimedAmountService unclaimedAmountService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UnclaimedAmountModel>> fetchAll(
            @RequestParam(required = false) String search,
            Pageable pageable) {

        return ResponseEntity.ok(unclaimedAmountService.getAllUnclaimedAmounts(search, pageable));
    }

    @GetMapping(value = "/{unclaimedAmountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnclaimedAmountModel> fetch(@PathVariable(value = "unclaimedAmountId") final Long unclaimedAmountId) {
        return ResponseEntity.ok(unclaimedAmountService.getUnclaimedAmount(unclaimedAmountId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnclaimedAmountModel> save(@RequestBody UnclaimedAmountModel unclaimedAmountModel, @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(unclaimedAmountService.createOrUpdate(unclaimedAmountModel,null,securityUser));
    }

    @PutMapping(value = "/{unclaimedAmountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnclaimedAmountModel> update(@PathVariable(value = "unclaimedAmountId") final Long unclaimedAmountId,
                                                   @RequestBody UnclaimedAmountModel unclaimedAmountModel,
                                                   @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(unclaimedAmountService.createOrUpdate(unclaimedAmountModel,unclaimedAmountId,securityUser));
    }
}
