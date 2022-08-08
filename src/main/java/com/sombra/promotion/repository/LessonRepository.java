package com.sombra.promotion.repository;

import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.row;

@Repository
@RequiredArgsConstructor
public class LessonRepository {

    private final DSLContext ctx;
    private final CourseRepository courseRepository;

    public List<Lesson> selectLessonsByCourseName(String courseName) {
        return ctx.select(LESSON.ID, LESSON.NAME, LESSON.COURSE_ID)
                .from(LESSON)
                .join(COURSE).on(COURSE.ID.eq(LESSON.COURSE_ID))
                .where(COURSE.NAME.eq(courseName))
                .fetchInto(Lesson.class);
    }

    public List<Lesson> selectLessonsByCourseId(UUID courseId) {
        return ctx.select(LESSON.ID, LESSON.NAME, LESSON.COURSE_ID)
                .from(LESSON)
                .join(COURSE).on(COURSE.ID.eq(LESSON.COURSE_ID))
                .where(COURSE.ID.eq(courseId))
                .fetchInto(Lesson.class);
    }

    public UUID selectLessonByNameAndCourse(String lessonName, String courseName) {
        UUID courseId = courseRepository.selectCourseIdBy(courseName);
        return requireNonNull(ctx.select(LESSON.ID)
                .from(LESSON)
                .where(LESSON.COURSE_ID.eq(courseId).and(LESSON.NAME.eq(lessonName)))
                .fetchOne()
        ).component1();
    }

    public List<UUID> insertLesson(List<String> lessons, String course) {
        UUID courseId = courseRepository.selectCourseIdBy(course);
        return ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                .values(lessons.stream().map(lesson -> row(lesson, courseId)).collect(toList()))
                .returningResult(LESSON.ID)
                .fetch()
                .map(Record1::component1);
    }

    public Lesson selectLessonBy(UUID id) {
        return ctx.select()
                .from(LESSON)
                .where(LESSON.ID.eq(id))
                .fetchSingleInto(Lesson.class);
    }


}
