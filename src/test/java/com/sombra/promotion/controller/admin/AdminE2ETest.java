package com.sombra.promotion.controller.admin;

import com.sombra.promotion.dto.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.dto.request.AssignRoleRequest;
import com.sombra.promotion.enums.RoleEnum;
import abstraction.E2ETest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static helpers.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

class AdminE2ETest extends E2ETest {

    @Test
    void must_assign_role() {

        insert.admin(TEST_USERNAME, TEST_PASSWORD);
        UUID studentId = insert.user(TEST_USERNAME_2, TEST_PASSWORD);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(new AssignRoleRequest(studentId, RoleEnum.instructor))
                .when()
                .patch(testURL("/admin/user-role"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("user.userId", equalTo(studentId.toString()))
                .body("user.username", equalTo(TEST_USERNAME_2))
                .body("role.role", equalTo("instructor"));

    }

    @Test
    void instructor_can_not_assign_role() {
        insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(new AssignRoleRequest(UUID.randomUUID(), RoleEnum.instructor))
                .when()
                .patch(testURL("/admin/assign/role"))
                .then()
                .statusCode(403);
    }


    @Test
    void must_get_all_users() {

        insert.admin(TEST_USERNAME, TEST_PASSWORD);
        insert.user("test-username-1", TEST_PASSWORD);
        insert.user("test-username-2", TEST_PASSWORD);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .when()
                .get(testURL("/admin/users"))
                .then()
                .statusCode(200);

    }

    @Test
    void must_assign_instructor() {

        insert.admin(TEST_USERNAME, TEST_PASSWORD);
        UUID instructorId = insert.instructor(TEST_USERNAME_2, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(new AssignInstructorForCourseRequest(instructorId, courseId))
                .when()
                .patch(testURL("/admin/instructor-course"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("user.userId", equalTo(instructorId.toString()))
                .body("user.username", equalTo(TEST_USERNAME_2))
                .body("course.courseId", equalTo(courseId.toString()))
                .body("course.courseName", equalTo(TEST_COURSE));
    }

}
