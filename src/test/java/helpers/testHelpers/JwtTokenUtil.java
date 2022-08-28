package helpers.testHelpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JwtTokenUtil {

    private final Algorithm algorithm;

    public JwtTokenUtil(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String bearerToken(String username) {
        return "Bearer " + token(username);
    }

    public String token(String username) {
        Instant issuedAt = Instant.now();
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(username)
                .withIssuedAt(issuedAt)
                .withExpiresAt(issuedAt.plus(1, ChronoUnit.DAYS))
                .sign(algorithm);
    }

}
