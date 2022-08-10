package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.FinishCourseDetails;
import com.sombra.promotion.dto.details.MarkDetails;
import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.factory.CourseDetailsFactory;
import com.sombra.promotion.factory.UserDetailsFactory;
import com.sombra.promotion.repository.CourseMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCourseFinishService {

    private final StudentMarkService studentMarkService;
    private final CourseMarkRepository courseMarkRepository;
    private final CourseDetailsFactory courseDetailsFactory;
    private final UserDetailsFactory userDetailsFactory;
    private final FeedbackService feedbackService;

    @Value("${app.minimum-course-pass-mark}")
    private int minimumCoursePassMark;

    @Transactional
    public FinishCourseDetails finishCourse(FinishCourseRequest request) {

        List<MarkDetails> marksByStudentAndCourse = studentMarkService.getMarksByStudentAndCourse(request.getStudentUsername(), request.getCourseName());
        double averageMarkForCourse = calculateAverageMarkForCourse(marksByStudentAndCourse);
        boolean isCoursePassed = isCoursePassed(averageMarkForCourse);
        courseMarkRepository.insertCourseMark((int)averageMarkForCourse, request.getStudentUsername(), request.getCourseName(), isCoursePassed);

        return FinishCourseDetails.builder()
                .course(courseDetailsFactory.build(request.getCourseName()))
                .student(userDetailsFactory.build(request.getStudentUsername()))
                .courseMarks(marksByStudentAndCourse)
                .feedbackDetails(feedbackService.getInstructorsFeedbacksForStudentForCourse(request.getStudentUsername(), request.getCourseName()))
                .finalMark(averageMarkForCourse)
                .isCoursePassed(isCoursePassed)
                .build();
    }

    private double calculateAverageMarkForCourse(List<MarkDetails> markDetails) {
        return markDetails.stream().mapToInt(MarkDetails::getMarkValue).average().orElseThrow();
    }

    private boolean isCoursePassed(double averageCourseMark) {
        return minimumCoursePassMark <= averageCourseMark;
    }

}
