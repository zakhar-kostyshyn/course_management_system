package com.sombra.promotion.service.generic.manyToMany;

import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.response.CoursesOfStudentResponse;
import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.exception.NotFoundCourseBelongsForStudentException;
import com.sombra.promotion.factory.specific.StudentCourseFactory;
import com.sombra.promotion.repository.manyToMany.StudentCourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.StudentCourse;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentCourseFactory studentCourseFactory;

    public StudentCourseResponse saveStudentCourse(CourseSubscriptionRequest request) {
        StudentCourse studentCourse = createStudentCourse(request.getStudentId(), request.getCourseId());
        studentCourseRepository.save(studentCourse);
        return studentCourseFactory.build(studentCourse);
    }

    public StudentCourse createStudentCourse(UUID studentId, UUID courseId) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        return studentCourse;
    }

    public CoursesOfStudentResponse getAllCoursesForStudent(UUID studentId) {
        List<Course> coursesByStudent = studentCourseRepository.findByFirstId(studentId);
        return studentCourseFactory.build(studentId, coursesByStudent);
    }

    public List<User> getStudentsByCourse(UUID courseId) {
        return studentCourseRepository.findBySecondId(courseId);
    }

    public void assertThatStudentInCourse(UUID studentId, UUID courseId) {
        boolean isExist = studentCourseRepository.exist(studentId, courseId);
        if (!isExist) throw new NotFoundCourseBelongsForStudentException(studentId, courseId);
    }

}
