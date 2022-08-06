package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.FeedbackDetails;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeedbackDetailsFactory {

    private final FeedbackRepository feedbackRepository;
    private final UserDetailsFactory userDetailsFactory;
    private final CourseDetailsFactory courseDetailsFactory;

    public FeedbackDetails build(UUID feedbackId) {
        Feedback feedback = feedbackRepository.selectHomeworkBy(feedbackId);
        return FeedbackDetails.builder()
                .feedback(feedback.getFeedback())
                .studentWhoReceive(userDetailsFactory.build(feedback.getStudentId()))
                .instructorWhoLeft(userDetailsFactory.build(feedback.getInstructorId()))
                .courseDetails(courseDetailsFactory.build(feedback.getCourseId()))
                .build();
    }

}
