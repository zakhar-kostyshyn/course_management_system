package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.CourseDetails;
import com.sombra.promotion.dto.details.CoursesOfInstructorDetails;
import com.sombra.promotion.dto.details.CoursesOfStudentDetails;
import com.sombra.promotion.factory.CourseDetailsFactory;
import com.sombra.promotion.factory.InstructorCourseDetailsFactory;
import com.sombra.promotion.factory.StudentCourseDetailsFactory;
import com.sombra.promotion.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorCourseDetailsFactory instructorCourseDetailsFactory;
    private final StudentCourseDetailsFactory studentCourseDetailsFactory;
    private final CourseDetailsFactory courseDetailsFactory;

    public CoursesOfInstructorDetails getAllCoursesForInstructor(String instructor) {
        return instructorCourseDetailsFactory.build(
                instructor, courseRepository.selectCoursesByInstructor(instructor)
        );
    }

    public CourseDetails createCourse(String courseName) {
        return courseDetailsFactory.build(courseRepository.insertCourse(courseName));
    }

    public CoursesOfStudentDetails getAllCoursesForStudent(String student) {
        return studentCourseDetailsFactory.build(
                student, courseRepository.selectCoursesByStudent(student)
        );
    }


}
