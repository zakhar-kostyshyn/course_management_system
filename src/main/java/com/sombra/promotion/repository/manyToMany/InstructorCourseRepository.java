package com.sombra.promotion.repository.manyToMany;

import com.sombra.promotion.interfaces.repository.ManyToManyTableRepository;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.User.USER;

@Repository
@RequiredArgsConstructor
public class InstructorCourseRepository implements ManyToManyTableRepository<InstructorCourse, User, Course> {

    private final DSLContext ctx;

    @Override
    public void save(InstructorCourse instructorCourse) {
        ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(instructorCourse.getInstructorId(), instructorCourse.getCourseId())
                .execute();
    }

    @Override
    public boolean exist(UUID instructorId, UUID courseId) {
        return ctx.fetchExists(
                ctx.selectFrom(INSTRUCTOR_COURSE)
                    .where(INSTRUCTOR_COURSE.COURSE_ID.eq(courseId))
                        .and(INSTRUCTOR_COURSE.INSTRUCTOR_ID.eq(instructorId)));
    }

    @Override
    public List<Course> findByFirstId(UUID userId) {
        return ctx.select(COURSE.ID, COURSE.NAME)
                .from(COURSE)
                .join(INSTRUCTOR_COURSE).on(INSTRUCTOR_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(INSTRUCTOR_COURSE.INSTRUCTOR_ID))
                .where(USER.ID.eq(userId))
                .fetchInto(Course.class);
    }

    @Override
    public List<User> findBySecondId(UUID courseId) {
        return ctx.select(USER.ID, USER.USERNAME, USER.PASSWORD)
                .from(COURSE)
                .join(INSTRUCTOR_COURSE).on(INSTRUCTOR_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(INSTRUCTOR_COURSE.INSTRUCTOR_ID))
                .where(COURSE.ID.eq(courseId))
                .fetchInto(User.class);
    }

}
