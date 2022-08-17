package com.sombra.promotion.service.specific;

import com.sombra.promotion.dto.response.InstructorCourseStudentsResponse;
import com.sombra.promotion.factory.specific.InstructorCourseStudentsFactory;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorStudentService {

    private final InstructorCourseService instructorCourseService;
    private final StudentCourseService studentCourseService;
    private final InstructorCourseStudentsFactory instructorCourseStudentsFactory;

    public InstructorCourseStudentsResponse getAllStudentsInCourseForInstructor(UUID instructorId, UUID courseId) {
        instructorCourseService.assertThatStudentInCourse(instructorId, courseId);
        List<User> studentsByCourse = studentCourseService.getStudentsByCourse(courseId);
        return instructorCourseStudentsFactory.build(instructorId, courseId, studentsByCourse);
    }

}
