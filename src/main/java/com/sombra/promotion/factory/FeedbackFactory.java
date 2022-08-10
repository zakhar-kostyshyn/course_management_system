package com.sombra.promotion.factory;

import com.sombra.promotion.dto.response.FeedbackResponse;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FeedbackFactory {

    private final FeedbackRepository feedbackRepository;
    private final UserFactory userFactory;
    private final CourseFactory courseFactory;

    public FeedbackResponse build(UUID feedbackId) {
        Feedback feedback = feedbackRepository.selectFeedbackBy(feedbackId);
        return FeedbackResponse.builder()
                .feedbackId(feedback.getId())
                .feedback(feedback.getFeedback())
                .studentWhoReceive(userFactory.build(feedback.getStudentId()))
                .instructorWhoLeft(userFactory.build(feedback.getInstructorId()))
                .courseResponse(courseFactory.build(feedback.getCourseId()))
                .build();
    }

    public List<FeedbackResponse> build(List<UUID> feedbacksIds) {
        return feedbacksIds.stream().map(this::build).collect(Collectors.toList());
    }

}
