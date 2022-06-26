package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@ComponentScan(basePackageClasses = {
        InstructorCourseRepository.class,
        UserRepository.class,
        CourseRepository.class
})
class InstructorCourseRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private InstructorCourseRepository repository;

    @Nested
    @DisplayName("Set instructor for course")
    class SetInstructorForCourse {

        @Test
        void must_set_instructor_for_course() {

            // given
            String givenCourseName = "test-courseName";
            String givenUsername = "test-username";

            UUID givenUserId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenUsername, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            // act
            repository.setInstructorForCourse(givenUsername, givenCourseName);

            // fetch
            InstructorCourse actual = ctx.select()
                    .from(INSTRUCTOR_COURSE)
                    .fetchSingleInto(InstructorCourse.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("instructorId", is(givenUserId)),
                    hasProperty("courseId", is(givenCourseId))
            ));

        }

    }

    @Nested
    @DisplayName("Insert course")
    class InsertCourse {

        @Test
        void must_insert_course() {

            // given
            String givenInstructor = "test-instructor";
            String givenCourse = "test-course";

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            // act
            repository.insertCourse(givenCourse, givenInstructor);

            // fetch
            Course actualCourse = ctx.select()
                    .from(COURSE)
                    .fetchAnyInto(Course.class);
            InstructorCourse actualInstructorCourse = ctx.select()
                    .from(INSTRUCTOR_COURSE)
                    .fetchAnyInto(InstructorCourse.class);

            // verify
            assertThat(actualCourse, allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("name", is(givenCourse))
            ));
            assertThat(actualInstructorCourse, allOf(
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("courseId", is(actualCourse.getId()))
            ));

        }

    }

}
