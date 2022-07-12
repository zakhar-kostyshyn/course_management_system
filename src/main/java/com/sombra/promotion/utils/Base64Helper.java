package com.sombra.promotion.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Helper {

    private final Base64.Encoder encoder;

    public Base64Helper() {
        this.encoder = Base64.getEncoder();
    }

    public String encodeToString(String text) {
        return encoder.encodeToString(text.getBytes());
    }

}
