package com.sombra.promotion.repository;

import com.sombra.promotion.dto.request.PutMarkRequest;
import com.sombra.promotion.tables.pojos.Mark;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Mark.MARK;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class MarkRepository {

    private final DSLContext ctx;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    public Mark selectMarkByStudentUsernameAndLessonId(String studentUsername, UUID lessonId) {
        UUID userId = userRepository.selectUserIdByUsername(studentUsername);
        return ctx.select()
                .from(MARK)
                .where(MARK.STUDENT_ID.eq(userId).and(MARK.LESSON_ID.eq(lessonId)))
                .fetchSingleInto(Mark.class);
    }

    public Mark selectMarkBy(UUID id) {
        return ctx.select()
                .from(MARK)
                .where(MARK.ID.eq(id))
                .fetchSingleInto(Mark.class);
    }

    public UUID insertMark(PutMarkRequest request) {
        UUID studentId = userRepository.selectUserIdByUsername(request.getStudent());
        UUID instructorId = userRepository.selectUserIdByUsername(request.getInstructor());
        UUID lessonId = lessonRepository.selectLessonByNameAndCourse(request.getLesson(), request.getCourse());
        return requireNonNull(ctx.insertInto(MARK, MARK.STUDENT_ID, MARK.INSTRUCTOR_ID, MARK.LESSON_ID, MARK.MARK_)
                        .values(studentId, instructorId, lessonId, request.getMark())
                        .returningResult(MARK.ID)
                        .fetchOne())
                .value1();
    }

}
