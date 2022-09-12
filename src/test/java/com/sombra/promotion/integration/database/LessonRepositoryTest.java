package com.sombra.promotion.integration.database;

import com.sombra.promotion.integration.database.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.tables.pojos.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class LessonRepositoryTest extends DatabaseIntegrationTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void must_find_lesson_by_id() {

        // setup
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);

        // act
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();

        // verify
        assertThat(lesson.getCourseId(), is(courseId));
        assertThat(lesson.getId(), is(lessonId));
        assertThat(lesson.getName(), is(TEST_LESSON_1));

    }


    @Test
    void must_required_feedback_by_id() {

        // setup
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);

        // act
        Lesson lesson = lessonRepository.requiredById(lessonId);

        // verify
        assertThat(lesson.getCourseId(), is(courseId));
        assertThat(lesson.getId(), is(lessonId));
        assertThat(lesson.getName(), is(TEST_LESSON_1));

    }


    @Test
    void must_persist_homework() {

        // setup
        UUID courseId = insert.course(TEST_COURSE);
        Lesson lesson = new Lesson(null, TEST_LESSON_1, courseId);

        // act
        lessonRepository.persist(lesson);

        // setup
        Lesson entity = select.lesson();
        assertThat(entity.getName(), is(TEST_LESSON_1));
        assertThat(entity.getCourseId(), is(courseId));
        assertThat(entity.getId(), notNullValue());

    }


    @Test
    void must_find_all_homework() {

        // setup
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId1 = insert.lesson(TEST_LESSON_1, courseId);
        UUID lessonId2 = insert.lesson(TEST_LESSON_2, courseId);

        // act
        List<Lesson> lessons = lessonRepository.findAll();

        // verify
        assertThat(lessons, hasSize(2));
        assertThat(lessons, hasItems(
                hasProperty("id", is(lessonId1)),
                hasProperty("id", is(lessonId2))
        ));

    }

    @Test
    void must_find_lesson_by_course_id() {

        // setup
        UUID courseId1 = insert.course("test-courseName-1");
        UUID courseId2 = insert.course("test-courseName-2");
        UUID lessonId1 = insert.lesson(TEST_LESSON_1, courseId1);
        insert.lesson(TEST_LESSON_2, courseId2);

        // act
        List<Lesson> lessons = lessonRepository.findLessonByCourseId(courseId1);

        // verify
        assertThat(lessons, hasSize(1));
        assertThat(lessons, hasItems(
                hasProperty("id", is(lessonId1))
        ));

    }

}