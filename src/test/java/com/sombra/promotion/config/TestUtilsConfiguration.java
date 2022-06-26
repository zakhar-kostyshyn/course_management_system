package com.sombra.promotion.config;

import com.sombra.promotion.utils.InsertUtils;
import com.sombra.promotion.utils.SelectUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestUtilsConfiguration {

    @Bean public InsertUtils insertUtils(@Autowired DSLContext ctx) {
        return new InsertUtils(ctx);
    }
    @Bean public SelectUtils selectUtils(@Autowired DSLContext ctx) {
        return new SelectUtils(ctx);
    }

}
