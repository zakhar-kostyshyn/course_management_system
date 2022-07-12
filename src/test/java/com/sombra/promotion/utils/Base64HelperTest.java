package com.sombra.promotion.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class Base64HelperTest {

    private final Base64Helper base64Helper = new Base64Helper();

    @Nested
    class EncodeToString {

        @Test
        void must_return_encoded_string() {
            String text = "test-text";
            String hashedText = Base64.getEncoder().encodeToString(text.getBytes());
            String result = base64Helper.encodeToString(text);
            assertThat(result, equalTo(hashedText));
        }

    }

}