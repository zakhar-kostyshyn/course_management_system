package com.sombra.promotion.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final SecurityUserDetailsService securityUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SecurityUser securityUserDetails = securityUserDetailsService.loadUserByUsername(username);

        boolean authenticated = Base64.getEncoder()
                .encodeToString((password + securityUserDetails.getSalt()).getBytes())
                .equals(securityUserDetails.getPassword());

        if (!authenticated) throw new RuntimeException("Incorrect password");

        return new UsernamePasswordAuthenticationToken(
                securityUserDetails.getUsername(),
                securityUserDetails.getPassword(),
                securityUserDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
