package com.fms.restapi;

import com.fms.security.AuthenticationRequest;
import com.fms.security.AuthenticationResponse;
import com.fms.security.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
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
    AuthenticationRequest request, HttpServletResponse response){
      return ResponseEntity.ok(authenticationService.authenticate(request,response));
    }
}
