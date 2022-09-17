package com.sombra.promotion.controller;

import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.dto.response.*;
import com.sombra.promotion.service.generic.HomeworkService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import com.sombra.promotion.service.specific.FinishCourseService;
import com.sombra.promotion.service.specific.StudentLessonService;
import com.sombra.promotion.service.specific.StudentSubscriptionOnCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.sombra.promotion.service.util.statics.SecurityPrincipalUtil.authenticatedUserId;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final HomeworkService homeworkService;
    private final StudentLessonService studentLessonService;
    private final FinishCourseService finishCourseService;
    private final StudentSubscriptionOnCourseService studentSubscriptionOnCourseService;
    private final StudentCourseService studentCourseService;

    @PatchMapping("/course-subscribe")
    public StudentCourseResponse subscribeOnCourse(@RequestBody CourseSubscriptionRequest request) {
        request.setStudentId(authenticatedUserId());
        return studentSubscriptionOnCourseService.subscribeStudentOnCourse(request);
    }

    @PostMapping("/homework")
    public HomeworkResponse uploadHomework(@RequestParam MultipartFile homework, @RequestParam UUID lessonId) {
        return homeworkService.saveHomework(new UploadHomeworkRequest(
                homework, authenticatedUserId(), lessonId
        ));
    }

    @GetMapping("/courses")
    public CoursesOfStudentResponse getAllCourses() {
        return studentCourseService.getAllCoursesForStudent(authenticatedUserId());
    }

    @GetMapping("/lessons/in/{courseId}")
    public StudentCourseLessonsResponse getStudentLessonsFromCourse(@PathVariable UUID courseId) {
        return studentLessonService.getLessonsOfStudentInCourse(authenticatedUserId(), courseId);
    }

    @PostMapping("/course-finish")
    public FinishCourseResponse finishCourse(@RequestBody FinishCourseRequest request) {
        request.setStudentId(authenticatedUserId());
        return finishCourseService.finishCourse(request);
    }

}
