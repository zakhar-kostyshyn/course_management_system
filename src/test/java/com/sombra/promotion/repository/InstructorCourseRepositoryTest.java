package com.sombra.promotion.repository;

import com.sombra.promotion.testConfigs.TestHelpersConfiguration;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import com.sombra.promotion.testHelpers.InsertUtils;
import com.sombra.promotion.testHelpers.SelectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static com.sombra.promotion.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(TestHelpersConfiguration.class)
@ComponentScan(basePackageClasses = {
        InstructorCourseRepository.class,
        UserRepository.class,
        CourseRepository.class
})
class InstructorCourseRepositoryTest {

    @Autowired
    private InstructorCourseRepository repository;

    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Set instructor for course")
    class SetInstructorForCourse {

        @Test
        void must_set_instructor_for_course() {

            // given
            UUID givenUserId = insert.user(TEST_USERNAME, TEST_PASSWORD);
            UUID givenCourseId = insert.course(TEST_COURSE);

            // act
            repository.setInstructorForCourse(TEST_USERNAME, TEST_COURSE);

            // verify
            InstructorCourse result = select.instructorCourse();
            assertThat(result, allOf(
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
            UUID givenInstructorId = insert.user(TEST_INSTRUCTOR, TEST_PASSWORD);

            // act
            repository.insertCourse(TEST_COURSE, TEST_INSTRUCTOR);

            // verify
            Course resultCourse = select.course();
            assertThat(resultCourse, allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("name", is(TEST_COURSE))
            ));

            InstructorCourse resultInstructorCourse = select.instructorCourse();
            assertThat(resultInstructorCourse, allOf(
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("courseId", is(resultCourse.getId()))
            ));

        }

    }

}
