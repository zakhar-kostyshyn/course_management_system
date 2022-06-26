package com.sombra.promotion.repository;

import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Feedback.FEEDBACK;

@Repository
@RequiredArgsConstructor
public class FeedbackRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public void insertFeedback(GiveFinalFeedbackRequest request) {

        UUID studentId = userRepository.selectUserIdByUsername(request.getStudent());
        UUID instructorId = userRepository.selectUserIdByUsername(request.getInstructor());
        UUID courseId = courseRepository.selectCourseIdByName(request.getCourse());

        ctx.insertInto(FEEDBACK, FEEDBACK.COURSE_ID, FEEDBACK.FEEDBACK_, FEEDBACK.STUDENT_ID, FEEDBACK.INSTRUCTOR_ID)
                .values(courseId, request.getFeedback(), studentId, instructorId)
                .execute();

    }

}
