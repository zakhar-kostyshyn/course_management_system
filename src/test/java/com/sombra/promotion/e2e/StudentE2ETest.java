package com.sombra.promotion.e2e;

import com.sombra.promotion.e2e.abstraction.E2ETest;
import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.tables.pojos.Homework;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static helpers.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

public class StudentE2ETest extends E2ETest {

    @Test
    void must_subscribe_on_course() {

        UUID studentId = insert.student(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        insert.studentCourse(studentId, courseId);
        CourseSubscriptionRequest body = new CourseSubscriptionRequest(courseId);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .body(body)
                .when()
                .patch(testURL("/student/subscribe"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("user.userId", is(studentId.toString()))
                .body("user.username", is(TEST_USERNAME))
                .body("course.courseId", is(courseId.toString()))
                .body("course.courseName", is(TEST_COURSE));

    }

    @Test
    void must_upload_homework() throws IOException {
        UUID studentId = insert.student(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        insert.studentCourse(studentId, courseId);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);

        MultiPartSpecification file = new MultiPartSpecBuilder(TEST_FILE_CONTENT.getBytes()).
                fileName("homework.txt").
                controlName("homework").
                mimeType("text/plain").
                build();


            given()
                .contentType("multipart/form-data")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .multiPart(file)
                .formParam("lessonId", lessonId)
                .when()
                .post(testURL("/student/homework"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("user.userId", is(studentId.toString()))
                .body("user.username", is(TEST_USERNAME))
                .body("lesson.lessonId", is(lessonId.toString()))
                .body("lesson.lessonName", is(TEST_LESSON_1))
                .body("lesson.course.courseId", is(courseId.toString()))
                .body("lesson.course.courseName", is(TEST_COURSE));

        Homework homework = select.homework();
        assertThat(homework.getFile(), is(TEST_FILE_CONTENT.getBytes()));

    }

    @Test
    void must_get_all_student_courses() {

        UUID studentId = insert.student(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId1 = insert.course("test-course-1");
        UUID courseId2 = insert.course("test-course-2");
        UUID courseId3 = insert.course("test-course-3");
        insert.studentCourse(studentId, courseId1);
        insert.studentCourse(studentId, courseId2);
        insert.studentCourse(studentId, courseId3);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .when()
                .get(testURL("/student/courses"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("user.userId", is(studentId.toString()))
                .body("user.username", is(TEST_USERNAME))
                .body("courses.courseName", hasItems(is("test-course-1"), is("test-course-2"), is("test-course-3")))
                .body("courses.courseId", hasItems(is(courseId1.toString()), is(courseId2.toString()), is(courseId3.toString())));

    }

    @Test
    void must_get_all_lessons_of_student_in_course() {

        UUID studentId = insert.student(TEST_USERNAME, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        insert.studentCourse(studentId, courseId);
        UUID lessonId1 = insert.lesson(TEST_LESSON_1, courseId);
        UUID lessonId2 = insert.lesson(TEST_LESSON_2, courseId);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_USERNAME))
                .when()
                .get(testURL("/student/lessons/"+courseId))
                .then()
                .statusCode(200)
                .assertThat()
                .body("user.userId", is(studentId.toString()))
                .body("user.username", is(TEST_USERNAME))
                .body("course.courseId", is(courseId.toString()))
                .body("course.courseName", is(TEST_COURSE))
                .body("lessons.lessonId", hasItems(is(lessonId1.toString()), is(lessonId2.toString())))
                .body("lessons.lessonName", hasItems(is(TEST_LESSON_1), is(TEST_LESSON_2)))
                .body("lessons.course.courseId", hasItems(is(courseId.toString()), is(courseId.toString())))
                .body("lessons.course.courseName", hasItems(is(TEST_COURSE), is(TEST_COURSE)));

    }

    @Test
    void must_finish_course() {

        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.instructor(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        insert.studentCourse(studentId, courseId);
        UUID lessonId1 = insert.lesson(TEST_LESSON_1, courseId);
        UUID lessonId2 = insert.lesson(TEST_LESSON_2, courseId);
        UUID markId1 = insert.mark(80, studentId, instructorId, lessonId1);
        UUID markId2 = insert.mark(60, studentId, instructorId, lessonId2);

        FinishCourseRequest body = new FinishCourseRequest(courseId);

        given().header(CONTENT_TYPE, "application/json")
                .header(AUTHORIZATION, jwtTokenUtil.bearerToken(TEST_STUDENT))
                .body(body)
                .when()
                .post(testURL("/student/course-finish"))
                .then()
                .statusCode(200)
                .assertThat()
                .body("courseMark.student.userId", is(studentId.toString()))
                .body("courseMark.student.username", is(TEST_STUDENT))
                .body("courseMark.course.courseId", is(courseId.toString()))
                .body("courseMark.course.courseName", is(TEST_COURSE))
                .body("marks.markValue", hasItems(80, 60))
                .body("marks.markId", hasItems(markId1.toString(), markId2.toString()))
                .body("marks.instructorWhoPut.userId", hasItems(instructorId.toString(), instructorId.toString()))
                .body("marks.studentWhoReceive.userId", hasItems(studentId.toString(), studentId.toString()))
                .body("marks.estimatedLesson.lessonId", hasItems(lessonId1.toString(), lessonId2.toString()))
                .body("marks.estimatedLesson.course.courseId", hasItems(courseId.toString(), courseId.toString()))
                .body("courseMark.courseMark", is(70.0F))
                .body("courseMark.isCoursePassed", is(true));

    }

}
