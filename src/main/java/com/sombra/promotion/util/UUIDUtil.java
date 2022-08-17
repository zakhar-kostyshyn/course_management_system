package com.sombra.promotion.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {

    public UUID randomUUID() {
        return UUID.randomUUID();
    }

}
