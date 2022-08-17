package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.FeedbackResponse;
import com.sombra.promotion.interfaces.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackFactory extends AbstractResponseFactory<Feedback, FeedbackResponse, FeedbackRepository> {

    private final FeedbackRepository feedbackRepository;
    private final UserFactory userFactory;
    private final CourseFactory courseFactory;

    @Override
    public FeedbackRepository getDao() {
        return feedbackRepository;
    }

    @Override
    public FeedbackResponse build(Feedback model) {
        return FeedbackResponse.builder()
                .feedbackId(model.getId())
                .feedback(model.getFeedback())
                .studentWhoReceive(userFactory.build(model.getStudentId()))
                .instructorWhoLeft(userFactory.build(model.getInstructorId()))
                .courseResponse(courseFactory.build(model.getCourseId()))
                .build();
    }

}
