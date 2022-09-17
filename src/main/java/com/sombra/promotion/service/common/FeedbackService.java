package com.sombra.promotion.service.common;

import com.sombra.promotion.service.common.validation.StudentInstructorInCourseValidationService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.dto.response.FeedbackResponse;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.mapper.common.FeedbackMapper;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.tables.pojos.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final UUIDUtil uuidUtil;
    private final StudentInstructorInCourseValidationService studentInstructorInCourseValidationService;

    public FeedbackResponse saveFeedback(GiveFinalFeedbackRequest request) {
        studentInstructorInCourseValidationService.assertThatInstructorAndStudentInCourse(request.getStudentId(), request.getInstructorId(), request.getCourseId());
        Feedback feedback = createFeedback(request.getFeedback(), request.getStudentId(), request.getInstructorId(), request.getCourseId());
        feedbackRepository.persist(feedback);
        return feedbackMapper.build(feedback);
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
