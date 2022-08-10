package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.CourseResponse;
import com.sombra.promotion.dto.response.CoursesOfInstructorResponse;
import com.sombra.promotion.dto.response.CoursesOfStudentResponse;
import com.sombra.promotion.factory.CourseFactory;
import com.sombra.promotion.factory.InstructorCourseFactory;
import com.sombra.promotion.factory.StudentCourseFactory;
import com.sombra.promotion.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorCourseFactory instructorCourseFactory;
    private final StudentCourseFactory studentCourseFactory;
    private final CourseFactory courseFactory;

    public CoursesOfInstructorResponse getAllCoursesForInstructor(String instructor) {
        return instructorCourseFactory.build(
                instructor, courseRepository.selectCoursesByInstructor(instructor)
        );
    }

    public CourseResponse createCourse(String courseName) {
        return courseFactory.build(courseRepository.insertCourse(courseName));
    }

    public CoursesOfStudentResponse getAllCoursesForStudent(String student) {
        return studentCourseFactory.build(
                student, courseRepository.selectCoursesByStudent(student)
        );
    }


}
