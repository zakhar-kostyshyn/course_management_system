package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class LessonRepository {

    private final DSLContext ctx;
    private final CourseRepository courseRepository;

    public List<Lesson> selectLessonsByCourse(String courseName) {
        return ctx.select(LESSON.ID, LESSON.NAME, LESSON.COURSE_ID)
                .from(LESSON)
                .join(COURSE).on(COURSE.ID.eq(LESSON.COURSE_ID))
                .where(COURSE.NAME.eq(courseName))
                .fetchInto(Lesson.class);
    }

    public UUID selectLessonByNameAndCourse(String lessonName, String courseName) {
        UUID courseId = courseRepository.selectCourseIdByName(courseName);
        return requireNonNull(ctx.select(LESSON.ID)
                .from(LESSON)
                .where(LESSON.COURSE_ID.eq(courseId).and(LESSON.NAME.eq(lessonName)))
                .fetchOne()
        ).component1();
    }

    public void insertLesson(String lesson, String course) {
        UUID courseId = courseRepository.selectCourseIdByName(course);
        ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                .values(lesson, courseId)
                .execute();
    }


}
