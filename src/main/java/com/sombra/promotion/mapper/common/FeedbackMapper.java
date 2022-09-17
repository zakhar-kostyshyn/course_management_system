package com.sombra.promotion.mapper.common;

import com.sombra.promotion.dto.response.FeedbackResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackMapper extends AbstractResponseFactory<Feedback, FeedbackResponse, FeedbackRepository> {

    private final FeedbackRepository feedbackRepository;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    @Override
    public FeedbackRepository getRepository() {
        return feedbackRepository;
    }

    @Override
    public FeedbackResponse build(Feedback model) {
        return FeedbackResponse.builder()
                .feedbackId(model.getId())
                .feedback(model.getFeedback())
                .studentWhoReceive(userMapper.build(model.getStudentId()))
                .instructorWhoLeft(userMapper.build(model.getInstructorId()))
                .course(courseMapper.build(model.getCourseId()))
                .build();
    }

}
