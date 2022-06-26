package com.sombra.promotion.repository;

import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.tables.pojos.Feedback;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Feedback.FEEDBACK;
import static com.sombra.promotion.tables.User.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@ComponentScan(basePackageClasses = {
        CourseRepository.class,
        UserRepository.class,
        FeedbackRepository.class
})
class FeedbackRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private FeedbackRepository repository;

    @Nested
    @DisplayName("Insert feedback")
    class InsertFeedback {

        @Test
        void must_insert_feedback() {

            // given
            String givenFeedback = "test-feedback";
            String givenCourse = "test-course";
            String givenStudent = "test-student";
            String givenInstructor = "test-instructor";

            GiveFinalFeedbackRequest givenRequest = new GiveFinalFeedbackRequest(
                    givenFeedback,
                    givenCourse,
                    givenStudent,
                    givenInstructor
            );


            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            // act
            repository.insertFeedback(givenRequest);

            // fetch
            Feedback actual = ctx.select()
                    .from(FEEDBACK)
                    .fetchSingleInto(Feedback.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("feedback", is(givenFeedback)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("courseId", is(givenCourseId)),
                    hasProperty("id", notNullValue())
            ));

        }

    }

}
