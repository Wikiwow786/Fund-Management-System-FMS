package com.fms.security;

import com.fms.configuration.JwtService;
import com.fms.entities.User;
import com.fms.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(user);

        User userEntity = userRepository.findByNameIgnoreCase(user.getUsername());

        String cookieHeader = String.format("jwt=%s; Path=/; HttpOnly; Secure; SameSite=Strict; Max-Age=%d",
                jwtToken, 60 * 60 * 24); // 24 hours
        response.addHeader("Set-Cookie", cookieHeader);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userName(user.getUsername())
                .language(userEntity.getLanguage())
                .build();
    }
}
