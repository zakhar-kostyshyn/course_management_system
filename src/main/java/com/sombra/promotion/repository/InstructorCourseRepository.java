package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class InstructorCourseRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public void setInstructorForCourse(String instructorUsername, String courseName) {
        UUID instructorId = userRepository.selectUserIdByUsername(instructorUsername);
        UUID courseId = courseRepository.selectCourseIdBy(courseName);
        ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(instructorId, courseId)
                .execute();
    }

    public UUID insertCourse(String course, String instructor) {
        UUID instructorId = userRepository.selectUserIdByUsername(instructor);
        UUID courseId = requireNonNull(ctx.insertInto(COURSE, COURSE.NAME)
                .values(course)
                .returning(COURSE.ID)
                .fetchAny()
        ).component1();
        ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(instructorId, courseId)
                .execute();
        return courseId;
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
