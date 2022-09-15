package com.sombra.promotion.service.generic;

import com.sombra.promotion.dto.response.CourseMarkResponse;
import com.sombra.promotion.factory.generic.CourseMarkFactory;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.repository.CourseMarkRepository;
import com.sombra.promotion.tables.pojos.CourseMark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseMarkService {

    private final CourseMarkRepository courseMarkRepository;
    private final CourseMarkFactory courseMarkFactory;
    private final UUIDUtil uuidUtil;

    public CourseMarkResponse savePassedCourseMark(double mark, UUID studentId, UUID courseId) {
        return saveCourseMark(mark, studentId, courseId, true);
    }

    public CourseMarkResponse saveNonPassedCourseMark(double mark, UUID studentId, UUID courseId) {
        return saveCourseMark(mark, studentId, courseId, false);
    }

    private CourseMarkResponse saveCourseMark(double mark, UUID studentId, UUID courseId, boolean isPassed) {
        CourseMark courseMark = createCourseMark(mark, studentId, courseId, isPassed);
        courseMarkRepository.persist(courseMark);
        return courseMarkFactory.build(courseMark);
    }

    private CourseMark createCourseMark(double mark, UUID studentId, UUID courseId, boolean isPassed) {
        CourseMark courseMark = new CourseMark();
        courseMark.setId(uuidUtil.randomUUID());
        courseMark.setStudentId(studentId);
        courseMark.setMark(mark);
        courseMark.setCourseId(courseId);
        courseMark.setCoursePassed(isPassed);
        return courseMark;
    }

}
