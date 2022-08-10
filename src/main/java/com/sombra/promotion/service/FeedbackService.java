package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.FeedbackResponse;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.factory.FeedbackFactory;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackFactory feedbackFactory;

    public FeedbackResponse giveFeedbackForCourse(GiveFinalFeedbackRequest request) {
        return feedbackFactory.build(feedbackRepository.insertFeedback(request));
    }

    public List<FeedbackResponse> getInstructorsFeedbacksForStudentForCourse(String student, String course) {
        return feedbackFactory.build(feedbackRepository.selectFeedbackBy(student, course).stream().map(Feedback::getId).collect(toList()));
    }

}
