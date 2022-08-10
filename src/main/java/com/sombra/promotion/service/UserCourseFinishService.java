package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.FinishCourseResponse;
import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.factory.CourseFactory;
import com.sombra.promotion.factory.UserFactory;
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
    private final CourseFactory courseFactory;
    private final UserFactory userFactory;
    private final FeedbackService feedbackService;

    @Value("${app.minimum-course-pass-mark}")
    private int minimumCoursePassMark;

    @Transactional
    public FinishCourseResponse finishCourse(FinishCourseRequest request) {

        List<MarkResponse> marksByStudentAndCourse = studentMarkService.getMarksByStudentAndCourse(request.getStudentUsername(), request.getCourseName());
        double averageMarkForCourse = calculateAverageMarkForCourse(marksByStudentAndCourse);
        boolean isCoursePassed = isCoursePassed(averageMarkForCourse);
        courseMarkRepository.insertCourseMark((int)averageMarkForCourse, request.getStudentUsername(), request.getCourseName(), isCoursePassed);

        return FinishCourseResponse.builder()
                .course(courseFactory.build(request.getCourseName()))
                .student(userFactory.build(request.getStudentUsername()))
                .courseMarks(marksByStudentAndCourse)
                .feedbackDetails(feedbackService.getInstructorsFeedbacksForStudentForCourse(request.getStudentUsername(), request.getCourseName()))
                .finalMark(averageMarkForCourse)
                .isCoursePassed(isCoursePassed)
                .build();
    }

    private double calculateAverageMarkForCourse(List<MarkResponse> markDetails) {
        return markDetails.stream().mapToInt(MarkResponse::getMarkValue).average().orElseThrow();
    }

    private boolean isCoursePassed(double averageCourseMark) {
        return minimumCoursePassMark <= averageCourseMark;
    }

}
