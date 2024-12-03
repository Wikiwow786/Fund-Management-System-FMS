package com.fms.fund_management_system.restapi;

import com.fms.fund_management_system.security.AuthenticationRequest;
import com.fms.fund_management_system.security.AuthenticationResponse;
import com.fms.fund_management_system.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/oauth/token",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody
    AuthenticationRequest request){
      return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
