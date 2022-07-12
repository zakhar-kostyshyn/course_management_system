package com.sombra.promotion.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final Algorithm algorithm;

    public String generateToken(String username) {
        Instant issuedAt = Instant.now();
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(username)
                .withIssuedAt(issuedAt)
                .withExpiresAt(issuedAt.plus(1, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public DecodedJWT decode(String token) {
        return JWT.require(algorithm)
                .withIssuer("auth0")
                .build()
                .verify(token);
    }

}
