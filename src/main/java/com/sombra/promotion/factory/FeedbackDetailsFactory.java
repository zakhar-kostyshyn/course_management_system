package com.sombra.promotion.factory;

import com.sombra.promotion.dto.details.FeedbackDetails;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FeedbackDetailsFactory {

    private final FeedbackRepository feedbackRepository;
    private final UserDetailsFactory userDetailsFactory;
    private final CourseDetailsFactory courseDetailsFactory;

    public FeedbackDetails build(UUID feedbackId) {
        Feedback feedback = feedbackRepository.selectFeedbackBy(feedbackId);
        return FeedbackDetails.builder()
                .feedbackId(feedback.getId())
                .feedback(feedback.getFeedback())
                .studentWhoReceive(userDetailsFactory.build(feedback.getStudentId()))
                .instructorWhoLeft(userDetailsFactory.build(feedback.getInstructorId()))
                .courseDetails(courseDetailsFactory.build(feedback.getCourseId()))
                .build();
    }

    public List<FeedbackDetails> build(List<UUID> feedbacksIds) {
        return feedbacksIds.stream().map(this::build).collect(Collectors.toList());
    }

}
