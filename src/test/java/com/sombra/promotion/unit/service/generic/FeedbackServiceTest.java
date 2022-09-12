package com.sombra.promotion.unit.service.generic;

import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.dto.response.FeedbackResponse;
import com.sombra.promotion.factory.generic.FeedbackFactory;
import com.sombra.promotion.repository.FeedbackRepository;
import com.sombra.promotion.service.generic.FeedbackService;
import com.sombra.promotion.service.generic.validation.StudentInstructorInCourseValidationService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock FeedbackRepository feedbackRepository;
    @Mock FeedbackFactory feedbackFactory;
    @Mock UUIDUtil uuidUtil;
    @Mock StudentInstructorInCourseValidationService studentInstructorInCourseValidationService;
    @InjectMocks
    FeedbackService feedbackService;
    @Captor ArgumentCaptor<Feedback> feedbackCaptor;

    @Test
    void must_validate_and_save_finalFeedback_then_build_and_return_response() {

        // setup
        String feedback = "test-feedback";
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID instructorId = UUID.randomUUID();
        UUID feedbackId = UUID.randomUUID();
        GiveFinalFeedbackRequest request = new GiveFinalFeedbackRequest(feedback, courseId, studentId, instructorId);
        FeedbackResponse response = mock(FeedbackResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(feedbackId);
        when(feedbackFactory.build(any(Feedback.class))).thenReturn(response);

        // act
        FeedbackResponse result = feedbackService.saveFeedback(request);

        // verify
        verify(studentInstructorInCourseValidationService).assertThatInstructorAndStudentInCourse(studentId, instructorId, courseId);
        verify(uuidUtil).randomUUID();
        verify(feedbackRepository).persist(feedbackCaptor.capture());
        verify(feedbackFactory).build(feedbackCaptor.capture());
        assertThat(feedbackCaptor.getValue().getId(), is(feedbackId));
        assertThat(feedbackCaptor.getValue().getFeedback(), is(feedback));
        assertThat(feedbackCaptor.getValue().getStudentId(), is(studentId));
        assertThat(feedbackCaptor.getValue().getInstructorId(), is(instructorId));
        assertThat(feedbackCaptor.getValue().getCourseId(), is(courseId));
        assertThat(result, sameInstance(response));

    }

}
