package com.sombra.promotion.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class UUIDHelperTest {

    private final UUIDHelper uuidHelper = new UUIDHelper();

    @Nested
    class RandomUUID {

        @Test
        void must_return_random_UUID() {
            UUID uuid1 = uuidHelper.randomUUID();
            UUID uuid2 = uuidHelper.randomUUID();
            assertThat(uuid1, not(equalTo(uuid2)));
        }

    }

}