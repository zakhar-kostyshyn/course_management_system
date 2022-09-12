package com.sombra.promotion.integration.database;

import com.sombra.promotion.integration.database.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FeedbackRepositoryITest extends DatabaseIntegrationTest {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Test
    void must_find_feedback_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.student(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID feedbackId = insert.feedback(TEST_FEEDBACK, studentId, instructorId, courseId);

        // act
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow();

        // verify
        assertThat(feedback.getFeedback(), is(TEST_FEEDBACK));
        assertThat(feedback.getStudentId(), is(studentId));
        assertThat(feedback.getInstructorId(), is(instructorId));
        assertThat(feedback.getCourseId(), is(courseId));

    }


    @Test
    void must_required_feedback_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.student(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID feedbackId = insert.feedback(TEST_FEEDBACK, studentId, instructorId, courseId);

        // act
        Feedback feedback = feedbackRepository.requiredById(feedbackId);

        // verify
        assertThat(feedback.getFeedback(), is(TEST_FEEDBACK));
        assertThat(feedback.getStudentId(), is(studentId));
        assertThat(feedback.getInstructorId(), is(instructorId));
        assertThat(feedback.getCourseId(), is(courseId));

    }


    @Test
    void must_persist_course_mark() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.student(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        Feedback feedback = new Feedback(null, TEST_FEEDBACK, studentId, instructorId, courseId);

        // act
        feedbackRepository.persist(feedback);

        // setup
        Feedback entity = select.feedback();
        assertThat(entity.getFeedback(), is(TEST_FEEDBACK));
        assertThat(entity.getStudentId(), is(studentId));
        assertThat(entity.getCourseId(), is(courseId));
        assertThat(entity.getId(), notNullValue());

    }


    @Test
    void must_find_all_course_mark() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.student(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID feedback1 = insert.feedback("test-feedback-1", studentId, instructorId, courseId);
        UUID feedback2 = insert.feedback("test-feedback-2", studentId, instructorId, courseId);
        UUID feedback3 = insert.feedback("test-feedback-3", studentId, instructorId, courseId);

        // act
        List<Feedback> feedbacks = feedbackRepository.findAll();

        // verify
        assertThat(feedbacks, hasSize(3));
        assertThat(feedbacks, hasItems(
                hasProperty("id", is(feedback1)),
                hasProperty("id", is(feedback2)),
                hasProperty("id", is(feedback3))
        ));

    }

}
