package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.Course;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@JooqTest
@ComponentScan(basePackageClasses = {
        CourseRepository.class
})
class CourseRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private CourseRepository repository;

    @Nested
    @DisplayName("Select courses by student")
    class SelectCoursesByStudent {

        @Test
        void must_select_courses_by_student() {

            // given
            String givenUsername = "test-username";
            String givenCourseNamePrefix = "test-course-";

            UUID givenUserId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenUsername, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            List<UUID> coursesIds = range(1, 5)
                    .mapToObj(i -> ctx.insertInto(COURSE, COURSE.NAME)
                            .values(givenCourseNamePrefix + i)
                            .returning(COURSE.ID)
                            .fetchOne()
                            .component1()
                    ).collect(toList());

            coursesIds.stream()
                    .limit(3)
                    .forEach(courseId -> ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                            .values(givenUserId, courseId)
                            .execute()
                    );

            // act
            List<Course> actual = repository.selectCoursesByStudent(givenUsername);

            // verify
            assertThat(actual, allOf(
                    hasItem(hasProperty("name", is("test-course-1"))),
                    hasItem(hasProperty("name", is("test-course-2"))),
                    hasItem(hasProperty("name", is("test-course-3"))),
                    not(hasItem(hasProperty("name", is("test-course-4"))))
            ));

        }

    }

    @Nested
    @DisplayName("Select Course id by name")
    class SelectCourseIdByName {

        @Test
        void must_select_course_id_by_name() {

            // given
            String givenCourse = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            // act
            UUID actual = repository.selectCourseIdByName(givenCourse);

            // verify
            assertThat(actual, is(givenCourseId));

        }

    }

    @Nested
    @DisplayName("Select courses by instructor")
    class SelectCoursesByInstructor {

        @Test
        void must_select_courses_by_instructor() {

            // given
            String givenInstructor = "test-instructor";
            String givenCourseNamePrefix = "test-course-";

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();


            List<UUID> coursesIds = range(1, 5)
                    .mapToObj(i -> ctx.insertInto(COURSE, COURSE.NAME)
                            .values(givenCourseNamePrefix + i)
                            .returning(COURSE.ID)
                            .fetchOne()
                            .component1()
                    ).collect(toList());

            coursesIds.stream()
                    .limit(3)
                    .forEach(courseId -> ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                            .values(givenInstructorId, courseId)
                            .execute()
                    );

            // act
            List<Course> actual = repository.selectCoursesByInstructor(givenInstructor);

            // verify
            assertThat(actual, allOf(
                    hasItem(hasProperty("name", is("test-course-1"))),
                    hasItem(hasProperty("name", is("test-course-2"))),
                    hasItem(hasProperty("name", is("test-course-3"))),
                    not(hasItem(hasProperty("name", is("test-course-4"))))
            ));

        }

    }

}
