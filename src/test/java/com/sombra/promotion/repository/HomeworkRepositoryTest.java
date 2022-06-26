package com.sombra.promotion.repository;

import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.tables.pojos.Homework;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Homework.HOMEWORK;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static com.sombra.promotion.tables.User.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@ComponentScan(basePackageClasses = {
        UserRepository.class,
        LessonRepository.class,
        HomeworkRepository.class
})
class HomeworkRepositoryTest {

    @Autowired
    private DSLContext ctx;

    @Autowired
    private HomeworkRepository repository;

    @Nested
    @DisplayName("Insert homework")
    class InsertHomework {

        @Test
        void must_insert_homework() throws IOException {

            // given
            byte[] givenContent = getClass().getResourceAsStream("/test-homework.txt").readAllBytes();
            MultipartFile givenHomework = new MockMultipartFile("test-homework.txt", "test-homework.txt", "text/plain", givenContent);
            String givenStudent = "test-student";
            String givenLesson = "test-lesson";
            String givenCourse = "given-course";
            UploadHomeworkRequest givenRequest =
                    new UploadHomeworkRequest(givenHomework, givenStudent, givenLesson, givenCourse);

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenLessonId = ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLesson)
                    .returning(LESSON.ID)
                    .fetchOne()
                    .component1();


            // act
            repository.insertHomework(givenRequest);

            // fetch
            Homework actual = ctx.select()
                    .from(HOMEWORK)
                    .fetchAnyInto(Homework.class);

            // verify

            assertThat(actual, allOf(
                    hasProperty("file", is(givenContent)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId)),
                    hasProperty("id", notNullValue())
            ));

        }

    }

}
