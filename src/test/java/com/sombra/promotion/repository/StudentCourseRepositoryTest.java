package com.sombra.promotion.repository;

import com.sombra.promotion.config.TestUtilsConfiguration;
import com.sombra.promotion.tables.pojos.StudentCourse;
import com.sombra.promotion.utils.InsertUtils;
import com.sombra.promotion.utils.SelectUtils;
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
@Import(TestUtilsConfiguration.class)
@ComponentScan(basePackageClasses = {
        StudentCourseRepository.class,
        UserRepository.class,
        CourseRepository.class
})
class StudentCourseRepositoryTest {

    @Autowired private StudentCourseRepository repository;
    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Set student for course")
    class SetStudentForCourse {

        @Test
        void must_set_student_for_course() {

            // given
            UUID givenCourseId = insert.course(TEST_COURSE);
            UUID givenStudentId = insert.user(TEST_STUDENT, TEST_PASSWORD);

            // act
            repository.setStudentForCourse(TEST_STUDENT, TEST_COURSE);

            // verify
            StudentCourse result = select.studentCourse();
            assertThat(result, allOf(
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("courseId", is(givenCourseId))
            ));

        }

    }

}
