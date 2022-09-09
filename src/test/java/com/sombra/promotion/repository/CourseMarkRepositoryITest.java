package com.sombra.promotion.repository;

import com.sombra.promotion.repository.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.tables.pojos.CourseMark;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CourseMarkRepositoryITest extends DatabaseIntegrationTest {

    @Autowired CourseMarkRepository courseMarkRepository;


    @Test
    void must_find_course_mark_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID courseMarkId = insert.courseMark(2.5, courseId, studentId, true);

        // act
        CourseMark courseMark = courseMarkRepository.findById(courseMarkId).orElseThrow();

        // verify
        assertThat(courseMark.getCourseId(), is(courseId));
        assertThat(courseMark.getCoursePassed(), is(true));
        assertThat(courseMark.getMark(), is(2.5));
        assertThat(courseMark.getStudentId(), is(studentId));

    }


    @Test
    void must_required_course_mark_by_id() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID courseMarkId = insert.courseMark(2.5, courseId, studentId, true);

        // act
        CourseMark courseMark = courseMarkRepository.requiredById(courseMarkId);

        // verify
        assertThat(courseMark.getCourseId(), is(courseId));
        assertThat(courseMark.getCoursePassed(), is(true));
        assertThat(courseMark.getMark(), is(2.5));
        assertThat(courseMark.getStudentId(), is(studentId));

    }


    @Test
    void must_persist_course_mark() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        CourseMark courseMark = new CourseMark(null, 2.5, studentId, courseId, true);

        // act
        courseMarkRepository.persist(courseMark);

        // setup
        CourseMark entity = select.courseMark();
        assertThat(entity.getMark(), is(2.5));
        assertThat(entity.getStudentId(), is(studentId));
        assertThat(entity.getCourseId(), is(courseId));
        assertThat(entity.getCoursePassed(), is(true));
        assertThat(entity.getId(), notNullValue());

    }


    @Test
    void must_find_all_course_mark() {

        // setup
        UUID studentId = insert.student(TEST_STUDENT, TEST_PASSWORD);
        UUID courseId = insert.course(TEST_COURSE);
        UUID courseMarkId1 = insert.courseMark(1.5, courseId, studentId, true);
        UUID courseMarkId2 = insert.courseMark(2.5, courseId, studentId, true);
        UUID courseMarkId3 = insert.courseMark(3.5, courseId, studentId, true);

        // act
        List<CourseMark> courseMarkList = courseMarkRepository.findAll();

        // verify
        assertThat(courseMarkList, hasSize(3));
        assertThat(courseMarkList, hasItems(
                hasProperty("id", is(courseMarkId1)),
                hasProperty("id", is(courseMarkId2)),
                hasProperty("id", is(courseMarkId3))
        ));

    }



}
