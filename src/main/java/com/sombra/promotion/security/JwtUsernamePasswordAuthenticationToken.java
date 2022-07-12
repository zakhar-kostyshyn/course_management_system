package com.sombra.promotion.security;

import com.sombra.promotion.tables.pojos.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Base64;

public class JwtUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final User user;
    private final String password;

    public JwtUsernamePasswordAuthenticationToken(User user, String password) {
        super(user.getUsername(), user.getPassword());
        this.user = user;
        this.password = password;
    }

    @Override
    public boolean isAuthenticated() {
        return Base64.getEncoder().encodeToString((password + user.getSalt()).getBytes())
                .equals(user.getPassword());
    }
}
