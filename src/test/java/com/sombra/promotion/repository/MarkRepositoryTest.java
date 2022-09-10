package com.sombra.promotion.repository;

import com.sombra.promotion.repository.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.tables.pojos.Mark;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MarkRepositoryTest extends DatabaseIntegrationTest {

    @Autowired private MarkRepository markRepository;

    @Test
    void must_find_mark_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.instructor(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        UUID markId = insert.mark(TEST_MARK, studentId, instructorId, lessonId);

        // act
        Mark mark = markRepository.findById(markId).orElseThrow();

        // verify
        assertThat(mark.getStudentId(), is(studentId));
        assertThat(mark.getLessonId(), is(lessonId));
        assertThat(mark.getInstructorId(), is(instructorId));
        assertThat(mark.getMark(), is(TEST_MARK));
        assertThat(mark.getId(), is(markId));

    }


    @Test
    void must_required_mark_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.instructor(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        UUID markId = insert.mark(TEST_MARK, studentId, instructorId, lessonId);

        // act
        Mark mark = markRepository.findById(markId).orElseThrow();

        // verify
        assertThat(mark.getStudentId(), is(studentId));
        assertThat(mark.getLessonId(), is(lessonId));
        assertThat(mark.getInstructorId(), is(instructorId));
        assertThat(mark.getMark(), is(TEST_MARK));
        assertThat(mark.getId(), is(markId));

    }


    @Test
    void must_persist_mark() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.instructor(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        Mark mark = new Mark(null, TEST_MARK, instructorId, studentId, lessonId);

        // act
        markRepository.persist(mark);

        // setup
        Mark entity = select.mark();
        assertThat(entity.getStudentId(), is(studentId));
        assertThat(entity.getLessonId(), is(lessonId));
        assertThat(entity.getMark(), is(TEST_MARK));
        assertThat(entity.getInstructorId(), is(instructorId));
        assertThat(entity.getId(), notNullValue());

    }


    @Test
    void must_find_all_marks() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.instructor(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId = insert.lesson(TEST_LESSON_1, courseId);
        UUID markId1 = insert.mark(10, studentId, instructorId, lessonId);
        UUID markId2 = insert.mark(20, studentId, instructorId, lessonId);

        // act
        List<Mark> marks = markRepository.findAll();

        // verify
        assertThat(marks, hasSize(2));
        assertThat(marks, hasItems(
                hasProperty("id", is(markId1)),
                hasProperty("id", is(markId2))
        ));

    }


    @Test
    void must_find_marks_by_studentId_and_list_of_lessons() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID instructorId = insert.instructor(TEST_INSTRUCTOR, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID lessonId1 = insert.lesson(TEST_LESSON_1, courseId);
        UUID lessonId2 = insert.lesson(TEST_LESSON_2, courseId);
        UUID lessonId3 = insert.lesson(TEST_LESSON_3, courseId);

        UUID markId11 = insert.mark(10, studentId, instructorId, lessonId1);
        UUID markId12 = insert.mark(20, studentId, instructorId, lessonId1);
        UUID markId21 = insert.mark(30, studentId, instructorId, lessonId2);
        UUID markId22 = insert.mark(40, studentId, instructorId, lessonId2);
        insert.mark(50, studentId, instructorId, lessonId3);
        insert.mark(60, studentId, instructorId, lessonId3);

        // act
        List<Mark> marks = markRepository.findMarkByStudentIdAndLessonId(studentId, List.of(lessonId1, lessonId2));

        // verify
        assertThat(marks, hasSize(4));
        assertThat(marks, hasItems(
                hasProperty("id", is(markId11)),
                hasProperty("id", is(markId12)),
                hasProperty("id", is(markId21)),
                hasProperty("id", is(markId22))
        ));

    }



}