package com.sombra.promotion.service.generic;

import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.dto.response.LessonResponse;
import com.sombra.promotion.factory.generic.LessonFactory;
import com.sombra.promotion.repository.LessonRepository;
import com.sombra.promotion.service.generic.validation.LessonValidationService;
import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.tables.pojos.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonFactory lessonFactory;
    private final UUIDUtil uuidUtil;
    private final LessonValidationService lessonValidationService;

    public List<LessonResponse> saveLessons(CreateLessonsRequest request) {
        lessonValidationService.assertThatInstructorInCourse(request.getInstructorId(), request.getCourseId());
        return request.getLessons().stream()
                .map(lessonName -> addLessonToCourse(lessonName, request.getCourseId()))
                .collect(toList());
    }

    public LessonResponse addLessonToCourse(String lessonName, UUID courseId) {
        Lesson lesson = createLesson(lessonName, courseId);
        lessonRepository.persist(lesson);
        return lessonFactory.build(lesson);
    }

    private Lesson createLesson(String lessonName, UUID courseId) {
        Lesson lesson = new Lesson();
        lesson.setId(uuidUtil.randomUUID());
        lesson.setCourseId(courseId);
        lesson.setName(lessonName);
        return lesson;
    }

    public List<LessonResponse> getLessonsByCourse(UUID courseId) {
        List<Lesson> lessons = lessonRepository.findLessonByCourseId(courseId);
        return lessonFactory.build(lessons);
    }

    public UUID findCourseIdByLessonId(UUID lessonId) {
        return lessonRepository.requiredById(lessonId).getCourseId();
    }

}
