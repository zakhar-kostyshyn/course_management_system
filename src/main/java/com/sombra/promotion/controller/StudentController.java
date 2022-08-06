package com.sombra.promotion.controller;

import com.sombra.promotion.dto.details.CoursesOfStudentDetails;
import com.sombra.promotion.dto.details.HomeworkDetails;
import com.sombra.promotion.dto.details.LessonsOfCourseAndStudentDetails;
import com.sombra.promotion.dto.details.StudentCourseDetails;
import com.sombra.promotion.dto.request.CourseSubscriptionRequest;
import com.sombra.promotion.dto.request.UploadHomeworkRequest;
import com.sombra.promotion.security.SecurityUser;
import com.sombra.promotion.service.CourseService;
import com.sombra.promotion.service.HomeworkService;
import com.sombra.promotion.service.StudentCourseService;
import com.sombra.promotion.service.StudentLessonCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentCourseService studentCourseService;
    private final HomeworkService homeworkService;
    private final CourseService courseService;
    private final StudentLessonCourseService studentLessonCourseService;

    @PatchMapping("/subscribe")
    public StudentCourseDetails subscribeOnCourse(@RequestBody CourseSubscriptionRequest request) {
        return studentCourseService.subscribeStudentOnCourse(request);
    }

    @PutMapping("/homework")
    public HomeworkDetails uploadHomework(@RequestBody UploadHomeworkRequest request) {
        return homeworkService.saveHomework(request);
    }

    @GetMapping("/courses")
    public CoursesOfStudentDetails getAllCourses(@AuthenticationPrincipal SecurityUser authenticatedUser) {
        return courseService.getAllCoursesForStudent(authenticatedUser.getUsername());
    }

    @GetMapping("/course/{courseId}/lessons")
    public LessonsOfCourseAndStudentDetails getStudentLessonsFromCourse(
            @PathVariable UUID courseId,
            @AuthenticationPrincipal SecurityUser authenticatedUser
    ) {
        return studentLessonCourseService.getStudentLessonsFromCourse(courseId, authenticatedUser);
    }

}
