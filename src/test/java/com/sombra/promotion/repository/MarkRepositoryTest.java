package com.sombra.promotion.repository;

import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.tables.pojos.Mark;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static com.sombra.promotion.tables.Mark.MARK;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@ComponentScan(basePackageClasses = {
        MarkRepository.class,
        UserRepository.class,
        LessonRepository.class
})
class MarkRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private MarkRepository repository;

    @Nested
    @DisplayName("Select mark by student username and lesson ID")
    class SelectMarkByStudentUsernameAndLessonId {

        @Test
        void must_select_mark_by_student_username_and_lesson_id() {

            // given
            String givenCourseName = "test-courseName";
            String givenStudent = "test-student";
            String givenInstructor = "test-instructor";
            String givenLessonName = "test-lesson";
            int givenMark = 100;

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenLessonId = ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLessonName)
                    .returning(LESSON.ID)
                    .fetchOne()
                    .component1();

            ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                    .values(givenStudentId, givenCourseId)
                    .execute();

            UUID givenMarkId = ctx.insertInto(MARK, MARK.STUDENT_ID, MARK.INSTRUCTOR_ID, MARK.LESSON_ID, MARK.MARK_)
                    .values(givenStudentId, givenInstructorId, givenLessonId, givenMark)
                    .returning(MARK.ID)
                    .fetchOne()
                    .component1();

            // act
            Mark actual = repository.selectMarkByStudentUsernameAndLessonId(givenStudent, givenLessonId);

            // verify
            assertThat(actual, allOf(
                    hasProperty("id", is(givenMarkId)),
                    hasProperty("mark", is(givenMark)),
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
            String givenCourseName = "test-courseName";
            String givenStudent = "test-student";
            String givenInstructor = "test-instructor";
            String givenLessonName = "test-lesson";
            int givenMark = 100;

            PutMarkRequest givenRequest = new PutMarkRequest(
                    givenMark,
                    givenStudent,
                    givenInstructor,
                    givenLessonName,
                    givenCourseName
            );

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenLessonId = ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLessonName)
                    .returning(LESSON.ID)
                    .fetchOne()
                    .component1();

            ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                    .values(givenStudentId, givenCourseId)
                    .execute();

            // act
            repository.insertMark(givenRequest);

            // fetch
            Mark actual = ctx.select().from(MARK).fetchSingleInto(Mark.class);

            // verify
            // verify
            assertThat(actual, allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("mark", is(givenMark)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId))
            ));

        }

    }

}
