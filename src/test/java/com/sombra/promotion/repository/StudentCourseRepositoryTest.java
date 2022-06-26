package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.StudentCourse;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@ComponentScan(basePackageClasses = {
        StudentCourseRepository.class,
        UserRepository.class,
        CourseRepository.class
})
class StudentCourseRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private StudentCourseRepository repository;

    @Nested
    @DisplayName("Set student for course")
    class SetStudentForCourse {

        @Test
        void must_set_student_for_course() {

            // given
            String givenStudent = "test-student";
            String givenCourse = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            // act
            repository.setStudentForCourse(givenStudent, givenCourse);

            // fetch
            StudentCourse actual = ctx.select().from(STUDENT_COURSE).fetchAnyInto(StudentCourse.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("courseId", is(givenCourseId))
            ));

        }

    }

}
