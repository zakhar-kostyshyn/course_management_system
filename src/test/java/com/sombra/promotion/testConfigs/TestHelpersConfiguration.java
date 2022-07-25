package com.sombra.promotion.testConfigs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sombra.promotion.testHelpers.InsertUtils;
import com.sombra.promotion.testHelpers.JsonBuilderUtils;
import com.sombra.promotion.testHelpers.SelectUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(JacksonTestConfiguration.class)
public class TestHelpersConfiguration {

    @Bean public InsertUtils insertUtils(@Autowired DSLContext ctx) {
        return new InsertUtils(ctx);
    }
    @Bean public SelectUtils selectUtils(@Autowired DSLContext ctx) {
        return new SelectUtils(ctx);
    }
    @Bean public JsonBuilderUtils jsonBuilder(@Autowired ObjectMapper mapper) {
        return new JsonBuilderUtils(mapper);
    }

}
