package configs;

import com.auth0.jwt.algorithms.Algorithm;
import helpers.testHelpers.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class JwtTokenTestConfiguration {

    @Bean
    public JwtTokenUtil jwtTokenUtil(@Autowired Algorithm algorithm) {
        return new JwtTokenUtil(algorithm);
    }

}
