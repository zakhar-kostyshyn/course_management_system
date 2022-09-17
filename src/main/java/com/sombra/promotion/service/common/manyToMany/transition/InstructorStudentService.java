package com.sombra.promotion.service.common.manyToMany.transition;

import com.sombra.promotion.dto.response.InstructorCourseStudentsResponse;
import com.sombra.promotion.mapper.InstructorCourseStudentsMapper;
import com.sombra.promotion.service.common.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.common.manyToMany.StudentCourseService;
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
    private final InstructorCourseStudentsMapper instructorCourseStudentsMapper;

    public InstructorCourseStudentsResponse getAllStudentsInCourseForInstructor(UUID instructorId, UUID courseId) {
        instructorCourseService.assertThatInstructorInCourse(instructorId, courseId);
        List<User> studentsByCourse = studentCourseService.getStudentsByCourse(courseId);
        return instructorCourseStudentsMapper.build(instructorId, courseId, studentsByCourse);
    }

}
