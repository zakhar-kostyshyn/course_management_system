package com.sombra.promotion.repository;

import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Feedback.FEEDBACK;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class FeedbackRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public UUID insertFeedback(GiveFinalFeedbackRequest request) {

        UUID studentId = userRepository.selectUserIdByUsername(request.getStudent());
        UUID instructorId = userRepository.selectUserIdByUsername(request.getInstructor());
        UUID courseId = courseRepository.selectCourseIdBy(request.getCourse());

        return requireNonNull(ctx.insertInto(FEEDBACK, FEEDBACK.COURSE_ID, FEEDBACK.FEEDBACK_, FEEDBACK.STUDENT_ID, FEEDBACK.INSTRUCTOR_ID)
                        .values(courseId, request.getFeedback(), studentId, instructorId)
                        .returningResult(FEEDBACK.ID)
                        .fetchOne())
                .component1();

    }

    public Feedback selectHomeworkBy(UUID id) {
        return ctx.select()
                .from(FEEDBACK)
                .where(FEEDBACK.ID.eq(id))
                .fetchSingleInto(Feedback.class);
    }

}
