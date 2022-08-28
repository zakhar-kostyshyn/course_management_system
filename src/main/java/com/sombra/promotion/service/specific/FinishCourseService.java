package com.sombra.promotion.service.specific;

import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.dto.response.CourseMarkResponse;
import com.sombra.promotion.dto.response.FinishCourseResponse;
import com.sombra.promotion.dto.response.MarkResponse;
import com.sombra.promotion.factory.generic.CourseMarkFactory;
import com.sombra.promotion.factory.specific.FinishCourseFactory;
import com.sombra.promotion.service.generic.CourseMarkService;
import com.sombra.promotion.service.generic.MarkService;
import com.sombra.promotion.tables.pojos.CourseMark;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinishCourseService {

    private final CourseMarkService courseMarkService;
    private final FinishCourseFactory finishCourseFactory;
    private final MarkService markService;

    @Value("${app.minimum-course-pass-mark}")
    private int minimumCoursePassMark;

    @Transactional
    public FinishCourseResponse finishCourse(FinishCourseRequest request) {

        List<MarkResponse> marksByStudentAndCourse = markService.getMarksByStudentAndCourse(request.getStudentId(), request.getCourseId());
        double averageMarkForCourse = calculateAverageMarkForCourse(marksByStudentAndCourse);
        boolean isCoursePassed = isCoursePassed(averageMarkForCourse);

        CourseMark courseMark;
        if (isCoursePassed)
            courseMark = courseMarkService.savePassedCourseMark(averageMarkForCourse, request.getStudentId(), request.getCourseId());
        else courseMark = courseMarkService.saveNonPassedCourseMark(averageMarkForCourse, request.getStudentId(), request.getCourseId());

        return finishCourseFactory.build(courseMark, marksByStudentAndCourse);
    }

    private double calculateAverageMarkForCourse(List<MarkResponse> markDetails) {
        return markDetails.stream().mapToInt(MarkResponse::getMarkValue).average().orElseThrow();
    }

    private boolean isCoursePassed(double averageCourseMark) {
        return minimumCoursePassMark <= averageCourseMark;
    }

}
