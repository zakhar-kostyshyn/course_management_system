package com.sombra.promotion.integration.database;

import com.sombra.promotion.integration.database.abstraction.DatabaseIntegrationTest;
import com.sombra.promotion.repository.CourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static helpers.Constants.TEST_COURSE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CourseRepositoryITest extends DatabaseIntegrationTest {

    @Autowired
    CourseRepository courseRepository;


    @Test
    void must_find_course_by_id() {

        // setup
        UUID courseId = insert.course(TEST_COURSE);

        // act
        Course course = courseRepository.findById(courseId).orElseThrow();

        // verify
        assertThat(course.getId(), is(courseId));
        assertThat(course.getName(), is(TEST_COURSE));

    }


    @Test
    void must_required_course_by_id() {

        // setup
        UUID courseId = insert.course(TEST_COURSE);

        // act
        Course course = courseRepository.requiredById(courseId);

        // verify
        assertThat(course.getId(), is(courseId));
        assertThat(course.getName(), is(TEST_COURSE));

    }


    @Test
    void must_persist_course() {

        // setup
        Course course = new Course(null, TEST_COURSE);

        // act
        courseRepository.persist(course);

        // setup
        Course entity = select.course();
        assertThat(entity.getName(), is(TEST_COURSE));
        assertThat(entity.getId(), notNullValue());

    }


    @Test
    void must_find_all_course_mark() {

        // setup
        UUID course1 = insert.course("test-course-1");
        UUID course2 = insert.course("test-course-2");
        UUID course3 = insert.course("test-course-3");

        // act
        List<Course> courses = courseRepository.findAll();

        // verify
        assertThat(courses, hasSize(3));
        assertThat(courses, hasItems(
                hasProperty("id", is(course1)),
                hasProperty("id", is(course2)),
                hasProperty("id", is(course3))
        ));

    }

}
