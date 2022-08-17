package com.sombra.promotion.repository.manyToMany;

import com.sombra.promotion.interfaces.repository.ManyToManyTableRepository;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.StudentCourse;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;

@Repository
@RequiredArgsConstructor
public class StudentCourseRepository implements ManyToManyTableRepository<StudentCourse, User, Course> {

    private final DSLContext ctx;

    public int countAmountStudentCourseBy(UUID studentId) {
        return ctx.fetchCount(
                ctx.select()
                        .from(STUDENT_COURSE)
                        .where(STUDENT_COURSE.STUDENT_ID.eq(studentId))
        );
    }

    @Override
    public void save(StudentCourse studentCourse) {
        ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                .values(studentCourse.getStudentId(), studentCourse.getCourseId())
                .execute();
    }

    @Override
    public boolean exist(UUID studentId, UUID courseId) {
        return ctx.fetchExists(
                ctx.selectFrom(STUDENT_COURSE)
                        .where(STUDENT_COURSE.COURSE_ID.eq(courseId)
                                .and(STUDENT_COURSE.STUDENT_ID.eq(studentId))));
    }

    @Override
    public List<Course> findByFirstId(UUID userId) {
        return ctx.select(COURSE.ID, COURSE.NAME)
                .from(COURSE)
                .join(STUDENT_COURSE).on(STUDENT_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(STUDENT_COURSE.STUDENT_ID))
                .where(USER.ID.eq(userId))
                .fetchInto(Course.class);
    }

    @Override
    public List<User> findBySecondId(UUID courseId) {
        return ctx.select(USER.ID, USER.USERNAME, USER.PASSWORD)
                .from(USER)
                .join(STUDENT_COURSE).on(STUDENT_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(STUDENT_COURSE.STUDENT_ID))
                .where(COURSE.ID.eq(courseId))
                .fetchInto(User.class);
    }
}
