package com.fms.restapi;

import com.fms.models.ExceptionListModel;
import com.fms.security.SecurityUser;
import com.fms.service.ExceptionListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/daily-checkout")
@RestController
@RequiredArgsConstructor
public class DailyCheckOutController {

    private final ExceptionListService exceptionListService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> submitDailyCheckOut(@RequestBody ExceptionListModel exceptionListModel, @AuthenticationPrincipal SecurityUser securityUser) {
        exceptionListService.handleDailyCheckOut(exceptionListModel,securityUser);
        return ResponseEntity.ok("Daily check-out submitted successfully");
    }
}
