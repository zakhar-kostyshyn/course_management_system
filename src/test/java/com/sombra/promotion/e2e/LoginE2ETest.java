package com.sombra.promotion.e2e;

import com.sombra.promotion.e2e.abstraction.E2ETest;
import com.sombra.promotion.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;

import static helpers.Constants.TEST_PASSWORD;
import static helpers.Constants.TEST_USERNAME;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginE2ETest extends E2ETest {

    @Test
    void must_login() {

        insert.user(TEST_USERNAME, TEST_PASSWORD);
        LoginRequest body = new LoginRequest(TEST_USERNAME, TEST_PASSWORD);

        given().contentType("application/json")
                .body(body)
                .when()
                .post(testURL("/login"))
                .then()
                .statusCode(200)
                .assertThat()
                .body(notNullValue());

    }

    @Test
    void must_throw_401() {

        LoginRequest body = new LoginRequest(TEST_USERNAME, TEST_PASSWORD);

        given().contentType("application/json")
                .body(body)
                .when()
                .post(testURL("/login"))
                .then()
                .statusCode(401)
                .assertThat()
                .body("message", is("Incorrect credentials"))
                .body("path", is("/login"));
    }

}
