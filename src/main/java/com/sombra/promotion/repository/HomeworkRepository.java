package com.sombra.promotion.repository;

import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.tables.pojos.Homework;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Homework.HOMEWORK;
import static com.sombra.promotion.tables.User.USER;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class HomeworkRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @SneakyThrows
    public UUID insertHomework(UploadHomeworkRequest request) {
        UUID studentId = userRepository.selectUserIdByUsername(request.getStudent());
        UUID lessonId = lessonRepository.selectLessonByNameAndCourse(request.getLesson(), request.getCourse());
        return requireNonNull(ctx.insertInto(HOMEWORK, HOMEWORK.FILE, HOMEWORK.LESSON_ID, HOMEWORK.STUDENT_ID)
                        .values(request.getHomework().getBytes(), lessonId, studentId)
                        .returningResult(HOMEWORK.ID)
                        .fetchOne())
                .component1();
    }

    public Homework selectHomeworkBy(UUID id) {
        return ctx.select()
                .from(USER)
                .where(USER.ID.eq(id))
                .fetchSingleInto(Homework.class);
    }

}
