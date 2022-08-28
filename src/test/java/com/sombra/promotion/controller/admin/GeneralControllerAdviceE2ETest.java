package com.sombra.promotion.controller.admin;

import abstraction.E2ETest;
import com.sombra.promotion.dto.request.CreateLessonsRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class GeneralControllerAdviceE2ETest extends E2ETest {



    @Test
    void must_return_400_error_response() {

        UUID instructorId = insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        CreateLessonsRequest body = new CreateLessonsRequest(
                courseId, List.of(TEST_LESSON_1, TEST_LESSON_2)
        );

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(body)
                .when()
                .post(testURL("/instructor/lesson"))
                .then()
                .statusCode(400)
                .assertThat()
                .body("message", is("Instructor with ID: "+instructorId+" cannot create lessons in course with ID: ("+courseId+")"))
                .body("path", is("/instructor/lesson"));

    }

}
