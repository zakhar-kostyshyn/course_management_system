package com.sombra.promotion.repository;

import com.sombra.promotion.testConfigs.TestHelpersConfiguration;
import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.tables.pojos.Feedback;
import com.sombra.promotion.testHelpers.InsertUtils;
import com.sombra.promotion.testHelpers.SelectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static com.sombra.promotion.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(TestHelpersConfiguration.class)
@ComponentScan(basePackageClasses = {
        CourseRepository.class,
        UserRepository.class,
        FeedbackRepository.class
})
class FeedbackRepositoryTest {

    @Autowired private FeedbackRepository repository;
    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Insert feedback")
    class InsertFeedback {

        @Test
        void must_insert_feedback() {

            // given
            GiveFinalFeedbackRequest givenRequest = new GiveFinalFeedbackRequest(TEST_FEEDBACK, TEST_COURSE, TEST_STUDENT, TEST_INSTRUCTOR);
            UUID givenStudentId = insert.user(TEST_STUDENT, TEST_PASSWORD);
            UUID givenInstructorId = insert.user(TEST_INSTRUCTOR, TEST_PASSWORD);
            UUID givenCourseId = insert.course(TEST_COURSE);

            // act
            repository.insertFeedback(givenRequest);

            // verify
            Feedback result = select.feedback();
            assertThat(result, allOf(
                    hasProperty("feedback", is(TEST_FEEDBACK)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("courseId", is(givenCourseId)),
                    hasProperty("id", notNullValue())
            ));

        }

    }

}
