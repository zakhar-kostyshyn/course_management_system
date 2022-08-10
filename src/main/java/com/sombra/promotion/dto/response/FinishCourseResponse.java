package com.sombra.promotion.dto.response;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class FinishCourseResponse {

    @NonNull private UserResponse student;
    @NonNull private CourseResponse course;
    @NonNull private List<MarkResponse> courseMarks;
    @NonNull private List<FeedbackResponse> feedbackDetails;      // we can get feedbacks from more than one instructor
    private boolean isCoursePassed;
    private double finalMark;

}
