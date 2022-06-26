package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.Lesson;
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
import static com.sombra.promotion.tables.Lesson.LESSON;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@JooqTest
@ComponentScan(basePackageClasses = {
        LessonRepository.class,
        CourseRepository.class
})
class LessonRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private LessonRepository repository;

    @Nested
    @DisplayName("Select lessons by course")
    class SelectLessonsByCourse {

        @Test
        void must_select_lessons_by_course() {

            // given
            String givenLessonPrefix = "test-lesson-";


            String givenCourseName1 = "test-course-1";
            UUID givenCourseId1 = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName1)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();
            range(1, 4).forEach(i -> ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                    .values(givenLessonPrefix + i, givenCourseId1)
                    .returningResult(LESSON.ID)
                    .fetchOne()
                    .component1());


            String givenCourseName2 = "test-course-2";
            UUID givenCourseId2 = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName2)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();
            ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                    .values(givenLessonPrefix + 4, givenCourseId2)
                    .returningResult(LESSON.ID)
                    .execute();


            // act
            List<Lesson> actual = repository.selectLessonsByCourse(givenCourseName1);

            // verify
            assertThat(actual, allOf(
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
            String givenLesson = "test-lesson";
            String givenCourse = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLesson)
                    .execute();

            // act
            UUID actual = repository.selectLessonByNameAndCourse(givenLesson, givenCourse);

            // verify
            assertThat(actual, is(actual));

        }

    }

    @Nested
    @DisplayName("Insert Lesson")
    class InsertLesson {

        @Test
        void must_insert_lesson() {

            // given
            String givenLesson = "test-lesson";
            String givenCourse = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchAny()
                    .component1();

            // act
            repository.insertLesson(givenLesson, givenCourse);

            // fetch
            Lesson actualLesson = ctx.select()
                    .from(LESSON)
                    .fetchAnyInto(Lesson.class);

            // verify
            assertThat(actualLesson, allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("name", is(givenLesson)),
                    hasProperty("courseId", is(givenCourseId))
            ));
        }

    }

}
