package com.sombra.promotion.e2e;


import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.dto.request.SaveMarkRequest;
import com.sombra.promotion.e2e.abstraction.E2ETest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class InstructorE2ETest extends E2ETest {

    @Test
    void must_put_mark() {

        UUID authUserId = insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        UUID studentId = insert.student(TEST_USERNAME_2, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        insert.instructorCourse(authUserId, courseId);
        insert.studentCourse(studentId, courseId);

        SaveMarkRequest body = new SaveMarkRequest(TEST_MARK, studentId, lessonId);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(body)
                .when()
                .post(testURL("/instructor/mark"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("markValue", is(TEST_MARK))
                .body("instructorWhoPut.userId", is(authUserId.toString()))
                .body("studentWhoReceive.userId", is(studentId.toString()))
                .body("estimatedLesson.lessonId", is(lessonId.toString()));

    }

    @Test
    void must_give_final_feedback() {

        UUID authUserId = insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID studentId = insert.student(TEST_USERNAME_2, TEST_PASSWORD);
        insert.instructorCourse(authUserId, courseId);
        insert.studentCourse(studentId, courseId);
        GiveFinalFeedbackRequest body = new GiveFinalFeedbackRequest(TEST_FEEDBACK, courseId, studentId);


        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(body)
                .when()
                .post(testURL("/instructor/feedback"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("feedback", is(TEST_FEEDBACK))
                .body("studentWhoReceive.userId", is(studentId.toString()))
                .body("instructorWhoLeft.userId", is(authUserId.toString()))
                .body("course.courseId", is(courseId.toString()));

    }

    @Test
    void must_create_course() {

        UUID authInstructorId = insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        UUID instructorId2 = insert.instructor(TEST_USERNAME_2, TEST_PASSWORD);
        CreateCourseRequest body = new CreateCourseRequest(
                List.of(authInstructorId, instructorId2), TEST_COURSE, List.of(TEST_LESSON_1, TEST_LESSON_2)
        );

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(body)
                .when()
                .post(testURL("/instructor/course"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("instructorsCourses.user.userId", hasItems(is(authInstructorId.toString()), is(instructorId2.toString())))
                .body("instructorsCourses.course.courseName", hasItems(is(TEST_COURSE), is(TEST_COURSE)))
                .body("course.courseName", is(TEST_COURSE))
                .body("lessons.lessonName", hasItems(is(TEST_LESSON_1), is(TEST_LESSON_2)))
                .body("lessons.course.courseName", hasItems(is(TEST_COURSE), is(TEST_COURSE)));


    }

    @Test
    void must_create_lesson() {

        UUID authInstructorId = insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        insert.instructorCourse(authInstructorId, courseId);
        CreateLessonsRequest body = new CreateLessonsRequest(
                courseId, List.of(TEST_LESSON_1, TEST_LESSON_2)
        );

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(body)
                .when()
                .post(testURL("/instructor/lesson"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("[0].lessonId", notNullValue())
                .body("[1].lessonId", notNullValue())
                .body("lessonName", hasItems(is(TEST_LESSON_1), is(TEST_LESSON_2)))
                .body("[0].course.courseId", is(courseId.toString()))
                .body("[1].course.courseId", is(courseId.toString()))
                .body("[1].course.courseName", is(TEST_COURSE))
                .body("[1].course.courseName", is(TEST_COURSE));

    }

    @Test
    void must_get_all_courses_for_instructor() {

        UUID authUserId = insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        UUID course1 = insert.course("test-course-1");
        UUID course2 = insert.course("test-course-2");
        insert.instructorCourse(authUserId, course1);
        insert.instructorCourse(authUserId, course2);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .when()
                .get(testURL("/instructor/courses"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("courses.courseId", hasItems(equalTo(course1.toString()), equalTo(course2.toString())))
                .body("courses.courseName", hasItems(equalTo("test-course-1"), equalTo("test-course-2")))
                .body("user.userId", equalTo(authUserId.toString()))
                .body("user.username", equalTo(TEST_USERNAME));

    }

    @Test
    void must_get_all_students_in_course() {

        UUID authUserId = insert.instructor(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        insert.instructorCourse(authUserId, courseId);
        UUID studentId1 = insert.student("test-student-1", TEST_PASSWORD);
        UUID studentId2 = insert.student("test-student-2", TEST_PASSWORD);
        insert.studentCourse(studentId1, courseId);
        insert.studentCourse(studentId2, courseId);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .when()
                .get(testURL("/instructor/students/"+courseId))
                .then()
                .statusCode(200)
                .assertThat()
                .body("students.userId", hasItems(is(studentId1.toString()), is(studentId2.toString())))
                .body("students.username", hasItems(is("test-student-1"), is("test-student-1")))
                .body("course.courseId", is(courseId.toString()))
                .body("course.courseName", is(TEST_COURSE))
                .body("instructor.userId", is(authUserId.toString()))
                .body("instructor.username", is(TEST_USERNAME));

    }

}
