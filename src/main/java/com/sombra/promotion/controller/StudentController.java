package com.sombra.promotion.controller;

import com.sombra.promotion.dto.response.*;
import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.request.FinishCourseRequest;
import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.security.SecurityUser;
import com.sombra.promotion.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentCourseService studentCourseService;
    private final HomeworkService homeworkService;
    private final CourseService courseService;
    private final StudentLessonCourseService studentLessonCourseService;
    private final UserCourseFinishService userCourseFinishService;

    @PatchMapping("/subscribe")
    public StudentCourseResponse subscribeOnCourse(@RequestBody CourseSubscriptionRequest request) {
        return studentCourseService.subscribeStudentOnCourse(request);
    }

    @PutMapping("/homework")
    public HomeworkResponse uploadHomework(
            @RequestParam MultipartFile homework,
            @RequestParam String student,
            @RequestParam String lesson,
            @RequestParam String course
    ) {
        return homeworkService.saveHomework(new UploadHomeworkRequest(
                homework, student, lesson, course
        ));
    }

    @GetMapping("/courses")
    public CoursesOfStudentResponse getAllCourses(@AuthenticationPrincipal SecurityUser authenticatedUser) {
        return courseService.getAllCoursesForStudent(authenticatedUser.getUsername());
    }

    @GetMapping("/course/{courseId}/lessons")
    public LessonsOfCourseAndStudentResponse getStudentLessonsFromCourse(
            @PathVariable UUID courseId,
            @AuthenticationPrincipal SecurityUser authenticatedUser
    ) {
        return studentLessonCourseService.getStudentLessonsFromCourse(courseId, authenticatedUser);
    }

    @PostMapping("/finishCourse")
    public FinishCourseResponse finishCourse(@RequestBody FinishCourseRequest request) {
        return userCourseFinishService.finishCourse(request);
    }

}
