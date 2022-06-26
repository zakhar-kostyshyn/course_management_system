package com.sombra.promotion.repository;

import com.sombra.promotion.config.TestUtilsConfiguration;
import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.tables.pojos.Mark;
import com.sombra.promotion.utils.InsertUtils;
import com.sombra.promotion.utils.SelectUtils;
import org.jooq.DSLContext;
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
        MarkRepository.class,
        UserRepository.class,
        LessonRepository.class
})
class MarkRepositoryTest {

    @Autowired private MarkRepository repository;
    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Select mark by student username and lesson ID")
    class SelectMarkByStudentUsernameAndLessonId {

        @Test
        void must_select_mark_by_student_username_and_lesson_id() {

            // given
            UUID givenStudentId = insert.user(TEST_STUDENT, TEST_PASSWORD);
            UUID givenInstructorId = insert.user(TEST_INSTRUCTOR, TEST_PASSWORD);
            UUID givenCourseId = insert.course(TEST_COURSE);
            UUID givenLessonId = insert.lesson(TEST_LESSON, givenCourseId);
            insert.studentCourse(givenStudentId, givenCourseId);
            UUID givenMarkId = insert.mark(TEST_MARK, givenStudentId, givenInstructorId, givenLessonId);

            // act
            Mark result = repository.selectMarkByStudentUsernameAndLessonId(TEST_STUDENT, givenLessonId);

            // verify
            assertThat(result, allOf(
                    hasProperty("id", is(givenMarkId)),
                    hasProperty("mark", is(TEST_MARK)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId))
            ));

        }
    }

    @Nested
    @DisplayName("Set mark for lesson")
    class InsertMark {

        @Test
        void must_insert_mark() {

            // given
            PutMarkRequest givenRequest = new PutMarkRequest(
                    TEST_MARK,
                    TEST_STUDENT,
                    TEST_INSTRUCTOR,
                    TEST_LESSON,
                    TEST_COURSE
            );

            UUID givenStudentId = insert.user(TEST_STUDENT, TEST_PASSWORD);
            UUID givenInstructorId = insert.user(TEST_INSTRUCTOR, TEST_PASSWORD);
            UUID givenCourseId = insert.course(TEST_COURSE);
            UUID givenLessonId = insert.lesson(TEST_LESSON, givenCourseId);
            insert.studentCourse(givenStudentId, givenCourseId);

            // act
            repository.insertMark(givenRequest);

            // verify
            Mark result = select.mark();
            assertThat(result, allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("mark", is(TEST_MARK)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId))
            ));

        }

    }

}
