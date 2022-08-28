package helpers.testConfigs;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sombra.promotion.configuration.SecurityAlgorithmConfiguration;
import helpers.testHelpers.*;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import( { JacksonTestConfiguration.class } )
public class TestHelpersConfiguration {

    @Bean public InsertUtils insertUtils(@Autowired DSLContext ctx) {
        return new InsertUtils(ctx, selectUtils(ctx));
    }
    @Bean public SelectUtils selectUtils(@Autowired DSLContext ctx) {
        return new SelectUtils(ctx);
    }
    @Bean public DeleteUtils deleteUtils(@Autowired DSLContext ctx) {
        return new DeleteUtils(ctx);
    }
    @Bean public JsonBuilderUtils jsonBuilder(@Autowired ObjectMapper mapper) {
        return new JsonBuilderUtils(mapper);
    }
    @Bean public JwtTokenUtil jwtTokenUtil(@Autowired Algorithm algorithm) {
        return new JwtTokenUtil(algorithm);
    }

}
