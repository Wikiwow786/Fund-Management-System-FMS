package com.fms.restapi;

import com.fms.models.MiddleManPayOutModel;
import com.fms.security.SecurityUser;
import com.fms.service.MiddleManPayOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/payout")
@RestController
@RequiredArgsConstructor
public class MiddleManPayOutController {
    private final MiddleManPayOutService middleManPayOutService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handlePayOut(@RequestBody MiddleManPayOutModel middleManPayOutModel, @AuthenticationPrincipal SecurityUser securityUser) {
        middleManPayOutService.handleMiddleManPayOut(middleManPayOutModel,securityUser);
        return ResponseEntity.ok("PayOut successful");
    }
}
