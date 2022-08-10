package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.FeedbackDetails;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.factory.FeedbackDetailsFactory;
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
    private final FeedbackDetailsFactory feedbackDetailsFactory;

    public FeedbackDetails giveFeedbackForCourse(GiveFinalFeedbackRequest request) {
        return feedbackDetailsFactory.build(feedbackRepository.insertFeedback(request));
    }

    public List<FeedbackDetails> getInstructorsFeedbacksForStudentForCourse(String student, String course) {
        return feedbackDetailsFactory.build(feedbackRepository.selectFeedbackBy(student, course).stream().map(Feedback::getId).collect(toList()));
    }

}
