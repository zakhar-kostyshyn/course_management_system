package com.sombra.promotion.controller.admin;

import abstraction.E2ETest;
import com.sombra.promotion.dto.request.RegistrationUserRequest;
import com.sombra.promotion.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import static helpers.Constants.TEST_PASSWORD;
import static helpers.Constants.TEST_USERNAME;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RegistrationE2ETest extends E2ETest {

    @Test
    void must_register_user() {

        RegistrationUserRequest body = new RegistrationUserRequest(TEST_USERNAME, TEST_PASSWORD);

        given().contentType("application/json")
                .body(body)
                .when()
                .post(testURL("/register"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("user.username", is(TEST_USERNAME))
                .body("role.role", is(RoleEnum.student.getLiteral()));

    }

}
