package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.FeedbackDetails;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.factory.FeedbackDetailsFactory;
import com.sombra.promotion.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackDetailsFactory feedbackDetailsFactory;

    public FeedbackDetails giveFeedbackForCourse(GiveFinalFeedbackRequest request) {
        return feedbackDetailsFactory.build(feedbackRepository.insertFeedback(request));
    }

}
