package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.InstructorCourse;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class InstructorCourseRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public InstructorCourse setInstructorForCourse(String instructorUsername, String courseName) {
        UUID instructorId = userRepository.selectUserIdByUsername(instructorUsername);
        UUID courseId = courseRepository.selectCourseIdBy(courseName);
        return ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(instructorId, courseId)
                .returningResult(INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .fetchSingleInto(InstructorCourse.class);
    }

    public InstructorCourse insertInstructorCourse(String courseName, String instructor) {
        UUID instructorId = userRepository.selectUserIdByUsername(instructor);
        UUID courseId = courseRepository.selectCourseIdBy(courseName);
        return ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(instructorId, courseId)
                .returningResult(INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .fetchSingleInto(InstructorCourse.class);
    }

    public List<InstructorCourse> insertInstructorCourse(String course, List<String> instructors) {
        return instructors.stream().map(instructor -> insertInstructorCourse(course, instructor)).collect(toList());
    }

    public boolean existInstructorCourseBy(UUID courseId, String instructor) {
        UUID instructorId = userRepository.selectUserIdByUsername(instructor);
        return ctx.fetchExists(
                ctx.select()
                        .from(INSTRUCTOR_COURSE)
                        .where(INSTRUCTOR_COURSE.COURSE_ID.eq(courseId)
                                .and(INSTRUCTOR_COURSE.INSTRUCTOR_ID.eq(instructorId))
                        )
        );
    }

}
