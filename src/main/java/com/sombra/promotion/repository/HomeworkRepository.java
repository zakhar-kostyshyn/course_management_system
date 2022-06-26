package com.sombra.promotion.repository;

import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Homework.HOMEWORK;

@Repository
@RequiredArgsConstructor
public class HomeworkRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @SneakyThrows
    public void insertHomework(UploadHomeworkRequest request) {
        UUID studentId = userRepository.selectUserIdByUsername(request.getStudent());
        UUID lessonId = lessonRepository.selectLessonByNameAndCourse(request.getLesson(), request.getCourse());
        ctx.insertInto(HOMEWORK, HOMEWORK.FILE, HOMEWORK.LESSON_ID, HOMEWORK.STUDENT_ID)
                .values(request.getHomework().getBytes(), lessonId, studentId)
                .execute();
    }

}
