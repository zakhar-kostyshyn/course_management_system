package com.sombra.promotion.unit.service.generic;

import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.dto.response.HomeworkResponse;
import com.sombra.promotion.factory.generic.HomeworkFactory;
import com.sombra.promotion.repository.HomeworkRepository;
import com.sombra.promotion.service.generic.HomeworkService;
import com.sombra.promotion.service.generic.validation.HomeworkValidationService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.Homework;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeworkServiceTest {

    @Mock HomeworkRepository homeworkRepository;
    @Mock HomeworkFactory homeworkFactory;
    @Mock UUIDUtil uuidUtil;
    @Mock HomeworkValidationService homeworkValidationService;
    @InjectMocks
    HomeworkService homeworkService;
    @Captor ArgumentCaptor<Homework> homeworkCaptor;


    @Test
    void must_validate_and_save_homework_then_build_and_return_response() throws IOException {

        // setup
        String homeworkText = "test-homework";
        MockMultipartFile homework = new MockMultipartFile("test", homeworkText.getBytes());
        UUID studentId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();
        UUID homeworkId = UUID.randomUUID();
        UploadHomeworkRequest request = new UploadHomeworkRequest(homework, studentId, lessonId);
        HomeworkResponse response = mock(HomeworkResponse.class);
        when(uuidUtil.randomUUID()).thenReturn(homeworkId);
        when(homeworkFactory.build(any(Homework.class))).thenReturn(response);

        // act
        HomeworkResponse result = homeworkService.saveHomework(request);

        // verify
        verify(homeworkValidationService).assertThatInstructorInCourse(studentId, lessonId);
        verify(uuidUtil).randomUUID();
        verify(homeworkRepository).persist(homeworkCaptor.capture());
        verify(homeworkFactory).build(homeworkCaptor.capture());
        assertThat(homeworkCaptor.getValue().getId(), is(homeworkId));
        assertThat(homeworkCaptor.getValue().getFile(), is(homework.getBytes()));
        assertThat(homeworkCaptor.getValue().getStudentId(), is(studentId));
        assertThat(homeworkCaptor.getValue().getLessonId(), is(lessonId));
        assertThat(result, sameInstance(response));

    }

}
