package configs;

import helpers.testHelpers.*;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DatabaseTestConfiguration {

    @Bean public InsertUtils insertUtils(@Autowired DSLContext ctx) {
        return new InsertUtils(ctx, selectUtils(ctx));
    }
    @Bean public SelectUtils selectUtils(@Autowired DSLContext ctx) {
        return new SelectUtils(ctx);
    }
    @Bean public DeleteUtils deleteUtils(@Autowired DSLContext ctx) {
        return new DeleteUtils(ctx);
    }

}
