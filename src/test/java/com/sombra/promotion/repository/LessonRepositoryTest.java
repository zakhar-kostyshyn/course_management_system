package com.sombra.promotion.repository;

import com.sombra.promotion.testConfigs.TestHelpersConfiguration;
import com.sombra.promotion.tables.pojos.Lesson;
import com.sombra.promotion.testHelpers.InsertUtils;
import com.sombra.promotion.testHelpers.SelectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.Constants.TEST_COURSE;
import static com.sombra.promotion.Constants.TEST_LESSON;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(TestHelpersConfiguration.class)
@ComponentScan(basePackageClasses = {
        LessonRepository.class,
        CourseRepository.class
})
class LessonRepositoryTest {

    @Autowired private LessonRepository repository;
    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Select lessons by course")
    class SelectLessonsByCourse {

        @Test
        void must_select_lessons_by_course() {

            // given
            String givenCourseName1 = "test-course-1";
            String givenCourseName2 = "test-course-2";
            UUID givenCourseId1 = insert.course(givenCourseName1);
            range(1, 4).forEach(i -> insert.lesson(TEST_LESSON + "-" + i, givenCourseId1));
            UUID givenCourseId2 = insert.course(givenCourseName2);
            insert.lesson(TEST_LESSON + "-" + 4, givenCourseId2);

            // act
            List<Lesson> result = repository.selectLessonsByCourse(givenCourseName1);

            // verify
            assertThat(result, allOf(
                    hasItem(hasProperty("name", is("test-lesson-1"))),
                    hasItem(hasProperty("name", is("test-lesson-2"))),
                    hasItem(hasProperty("name", is("test-lesson-3"))),
                    not(hasItem(hasProperty("name", is("test-lesson-4"))))
            ));

        }

    }

    @Nested
    @DisplayName("Select Lesson ")
    class SelectLessonByNameAndCourse {

        @Test
        void must_select_lesson_by_name_and_course() {

            // given
            UUID givenCourseId = insert.course(TEST_COURSE);
            insert.lesson(TEST_LESSON, givenCourseId);

            // act
            UUID result = repository.selectLessonByNameAndCourse(TEST_LESSON, TEST_COURSE);

            // verify
            assertThat(result, is(result));

        }

    }

    @Nested
    @DisplayName("Insert Lesson")
    class InsertLesson {

        @Test
        void must_insert_lesson() {

            // given
            UUID givenCourseId = insert.course(TEST_COURSE);

            // act
            repository.insertLesson(TEST_LESSON, TEST_COURSE);

            // verify
            Lesson result = select.lesson();
            assertThat(result, allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("name", is(TEST_LESSON)),
                    hasProperty("courseId", is(givenCourseId))
            ));
        }

    }

}
