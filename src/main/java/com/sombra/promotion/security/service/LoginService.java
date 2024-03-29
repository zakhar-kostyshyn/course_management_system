package com.sombra.promotion.security.service;

import com.sombra.promotion.exception.IncorrectCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public String login(String username, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (RuntimeException e) {
            throw new IncorrectCredentialsException(e);
        }
        if (!authentication.isAuthenticated()) throw new IncorrectCredentialsException();
        return jwtTokenService.generateToken(username);
    }

}
