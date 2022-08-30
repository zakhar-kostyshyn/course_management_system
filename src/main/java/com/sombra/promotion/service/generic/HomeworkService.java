package com.sombra.promotion.service.generic;

import com.sombra.promotion.service.generic.validation.HomeworkValidationService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.dto.response.HomeworkResponse;
import com.sombra.promotion.factory.generic.HomeworkFactory;
import com.sombra.promotion.repository.HomeworkRepository;
import com.sombra.promotion.tables.pojos.Homework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final HomeworkFactory homeworkFactory;
    private final UUIDUtil uuidUtil;
    private final HomeworkValidationService homeworkValidationService;

    public HomeworkResponse saveHomework(UploadHomeworkRequest request) {
        homeworkValidationService.assertThatInstructorInCourse(request.getStudentId(), request.getLessonId());
        Homework homework;
        try {
            homework = createHomework(request.getHomework().getBytes(), request.getStudentId(), request.getLessonId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        homeworkRepository.persist(homework);
        return homeworkFactory.build(homework);
    }

    private Homework createHomework(byte[] file, UUID studentId, UUID lessonId) {
        Homework homework = new Homework();
        homework.setId(uuidUtil.randomUUID());
        homework.setFile(file);
        homework.setStudentId(studentId);
        homework.setLessonId(lessonId);
        return homework;
    }

}
