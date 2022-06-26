package com.sombra.promotion.repository;

import com.sombra.promotion.config.TestUtilsConfiguration;
import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.tables.pojos.Homework;
import com.sombra.promotion.utils.InsertUtils;
import com.sombra.promotion.utils.SelectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.sombra.promotion.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(TestUtilsConfiguration.class)
@ComponentScan(basePackageClasses = {
        UserRepository.class,
        LessonRepository.class,
        HomeworkRepository.class
})
class HomeworkRepositoryTest {

    @Autowired private HomeworkRepository repository;
    @Autowired private InsertUtils insert;
    @Autowired private SelectUtils select;

    @Nested
    @DisplayName("Insert homework")
    class InsertHomework {

        @Test
        void must_insert_homework() throws IOException {

            // given
            byte[] givenContent = getClass().getResourceAsStream("/test-homework.txt").readAllBytes();
            MultipartFile givenHomework = new MockMultipartFile("test-homework.txt", "test-homework.txt", "text/plain", givenContent);
            UploadHomeworkRequest givenRequest = new UploadHomeworkRequest(givenHomework, TEST_STUDENT, TEST_LESSON, TEST_COURSE);
            UUID givenStudentId = insert.user(TEST_STUDENT, TEST_PASSWORD);
            UUID givenCourseId = insert.course(TEST_COURSE);
            UUID givenLessonId = insert.lesson(TEST_LESSON, givenCourseId);

            // act
            repository.insertHomework(givenRequest);

            // verify
            Homework result = select.homework();
            assertThat(result, allOf(
                    hasProperty("file", is(givenContent)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId)),
                    hasProperty("id", notNullValue())
            ));

        }

    }

}
