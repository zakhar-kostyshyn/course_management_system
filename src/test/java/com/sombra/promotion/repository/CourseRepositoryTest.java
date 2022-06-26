package com.sombra.promotion.repository;

import com.sombra.promotion.config.TestUtilsConfiguration;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.utils.InsertUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.Constants.*;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(TestUtilsConfiguration.class)
@ComponentScan(basePackageClasses = {
        CourseRepository.class
})
class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private InsertUtils insert;

    @Nested
    @DisplayName("Select courses by student")
    class SelectCoursesByStudent {

        @Test
        void must_select_courses_by_student() {
            UUID givenUserId = insert.user(TEST_USERNAME, TEST_PASSWORD);
            range(1, 5)
                .mapToObj(i -> insert.course(TEST_COURSE + "-" + i))
                .limit(3)
                .forEach(courseId -> insert.studentCourse(givenUserId, courseId));
            List<Course> result = repository.selectCoursesByStudent(TEST_USERNAME);
            assertThat(result, allOf(
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
            UUID givenCourseId = insert.course(TEST_COURSE);
            UUID actual = repository.selectCourseIdByName(TEST_COURSE);
            assertThat(actual, is(givenCourseId));
        }

    }

    @Nested
    @DisplayName("Select courses by instructor")
    class SelectCoursesByInstructor {

        @Test
        void must_select_courses_by_instructor() {
            UUID givenInstructorId = insert.user(TEST_INSTRUCTOR, TEST_PASSWORD);
            range(1, 5)
                .mapToObj(i -> insert.course(TEST_COURSE + "-" + i))
                .limit(3)
                .forEach(courseId -> insert.instructorCourse(givenInstructorId, courseId));
            List<Course> result = repository.selectCoursesByInstructor(TEST_INSTRUCTOR);
            assertThat(result, allOf(
                    hasItem(hasProperty("name", is("test-course-1"))),
                    hasItem(hasProperty("name", is("test-course-2"))),
                    hasItem(hasProperty("name", is("test-course-3"))),
                    not(hasItem(hasProperty("name", is("test-course-4"))))
            ));

        }

    }

}
