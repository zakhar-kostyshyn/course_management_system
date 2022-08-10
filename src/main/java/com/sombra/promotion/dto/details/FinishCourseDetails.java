package com.sombra.promotion.dto.details;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class FinishCourseDetails {

    @NonNull private UserDetails student;
    @NonNull private CourseDetails course;
    @NonNull private List<MarkDetails> courseMarks;
    @NonNull private List<FeedbackDetails> feedbackDetails;      // we can get feedbacks from more than one instructor
    private boolean isCoursePassed;
    private double finalMark;

}
