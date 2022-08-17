package com.sombra.promotion.controller;

import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.dto.response.*;
import com.sombra.promotion.security.SecurityUser;
import com.sombra.promotion.service.generic.HomeworkService;
import com.sombra.promotion.service.generic.manyToMany.StudentCourseService;
import com.sombra.promotion.service.specific.FinishCourseService;
import com.sombra.promotion.service.specific.StudentLessonService;
import com.sombra.promotion.service.specific.StudentSubscriptionOnCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final HomeworkService homeworkService;
    private final StudentLessonService studentLessonService;
    private final FinishCourseService finishCourseService;
    private final StudentSubscriptionOnCourseService studentSubscriptionOnCourseService;
    private final StudentCourseService studentCourseService;

    @PatchMapping("/subscribe")
    public StudentCourseResponse subscribeOnCourse(@RequestBody CourseSubscriptionRequest request) {
        return studentSubscriptionOnCourseService.subscribeStudentOnCourse(request);
    }

    @PutMapping("/homework")
    public HomeworkResponse uploadHomework(
            @RequestParam MultipartFile homework,
            @RequestParam UUID studentId,
            @RequestParam UUID lessonId
    ) {
        return homeworkService.saveHomework(new UploadHomeworkRequest(
                homework, studentId, lessonId
        ));
    }

    @GetMapping("/courses")
    public CoursesOfStudentResponse getAllCourses(@AuthenticationPrincipal SecurityUser authenticatedUser) {
        return studentCourseService.getAllCoursesForStudent(authenticatedUser.getId());
    }

    @GetMapping("/course/{courseId}/lessons")
    public StudentCourseLessonsResponse getStudentLessonsFromCourse(
            @PathVariable UUID courseId,
            @AuthenticationPrincipal SecurityUser authenticatedUser
    ) {
        return studentLessonService.getLessonsInCourseOfStudent(courseId, authenticatedUser.getId());
    }

    @PostMapping("/finishCourse")
    public CourseMarkResponse finishCourse(@RequestBody FinishCourseRequest request) {
        return finishCourseService.finishCourse(request);
    }

}
