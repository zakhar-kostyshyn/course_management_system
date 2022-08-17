package com.sombra.promotion.service.generic;

import com.sombra.promotion.util.UUIDUtil;
import com.sombra.promotion.dto.response.FeedbackResponse;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.factory.generic.FeedbackFactory;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackFactory feedbackFactory;
    private final UUIDUtil uuidUtil;

    public FeedbackResponse saveFeedback(GiveFinalFeedbackRequest request) {
        Feedback feedback = createFeedback(request.getFeedback(), request.getStudentId(), request.getInstructorId(), request.getCourseId());
        feedbackRepository.save(feedback);
        return feedbackFactory.build(feedback);
    }

    private Feedback createFeedback(String feedbackText, UUID studentId, UUID instructorId, UUID courseId) {
        Feedback feedback = new Feedback();
        feedback.setId(uuidUtil.randomUUID());
        feedback.setFeedback(feedbackText);
        feedback.setStudentId(studentId);
        feedback.setInstructorId(instructorId);
        feedback.setCourseId(courseId);
        return feedback;
    }


}
