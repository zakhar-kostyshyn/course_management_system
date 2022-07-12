package com.sombra.promotion.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDHelper {

    public UUID randomUUID() {
        return UUID.randomUUID();
    }

}
