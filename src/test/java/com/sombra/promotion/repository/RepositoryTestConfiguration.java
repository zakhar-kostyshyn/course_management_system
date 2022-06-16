package com.sombra.promotion.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RepositoryTestConfiguration {

    @Bean
    DomainRepository domainRepository(@Autowired DSLContext ctx) {
        return new DomainRepository(ctx);
    }

}
