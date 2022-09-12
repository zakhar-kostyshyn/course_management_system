package com.sombra.promotion.integration.database;

import com.sombra.promotion.integration.database.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.repository.HomeworkRepository;
import com.sombra.promotion.tables.pojos.Homework;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HomeworkRepositoryITest extends DatabaseIntegrationTest {

    @Autowired
    HomeworkRepository homeworkRepository;

    @Test
    void must_find_homework_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        UUID homeworkId = insert.homework(TEST_FILE_CONTENT.getBytes(), studentId, lessonId);

        // act
        Homework homework = homeworkRepository.findById(homeworkId).orElseThrow();

        // verify
        assertThat(homework.getFile(), is(TEST_FILE_CONTENT.getBytes()));
        assertThat(homework.getStudentId(), is(studentId));
        assertThat(homework.getLessonId(), is(lessonId));

    }


    @Test
    void must_required_feedback_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        UUID homeworkId = insert.homework(TEST_FILE_CONTENT.getBytes(), studentId, lessonId);

        // act
        Homework homework = homeworkRepository.requiredById(homeworkId);

        // verify
        assertThat(homework.getFile(), is(TEST_FILE_CONTENT.getBytes()));
        assertThat(homework.getStudentId(), is(studentId));
        assertThat(homework.getLessonId(), is(lessonId));

    }


    @Test
    void must_persist_homework() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        Homework homework = new Homework(null, TEST_FILE_CONTENT.getBytes(), studentId, lessonId);

        // act
        homeworkRepository.persist(homework);

        // setup
        Homework entity = select.homework();
        assertThat(entity.getFile(), is(TEST_FILE_CONTENT.getBytes()));
        assertThat(entity.getStudentId(), is(studentId));
        assertThat(entity.getLessonId(), is(lessonId));
        assertThat(entity.getId(), notNullValue());

    }


    @Test
    void must_find_all_homework() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        UUID homeworkId1 = insert.homework("test-homework-1".getBytes(), studentId, lessonId);
        UUID homeworkId2 = insert.homework("test-homework-2".getBytes(), studentId, lessonId);
        UUID homeworkId3 = insert.homework("test-homework-3".getBytes(), studentId, lessonId);

        // act
        List<Homework> homeworks = homeworkRepository.findAll();

        // verify
        assertThat(homeworks, hasSize(3));
        assertThat(homeworks, hasItems(
                hasProperty("id", is(homeworkId1)),
                hasProperty("id", is(homeworkId2)),
                hasProperty("id", is(homeworkId3))
        ));

    }

}
