package com.sombra.promotion.service.util;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DateUtil {

    public Instant nowInstant() {
        return Instant.now();
    }

}
