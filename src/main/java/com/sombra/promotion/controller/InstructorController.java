package com.sombra.promotion.controller;

import com.sombra.promotion.dto.response.*;
import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.dto.request.PutMarkRequest;
import com.sombra.promotion.security.SecurityUser;
import com.sombra.promotion.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final MarkService markService;
    private final FeedbackService feedbackService;
    private final CourseService courseService;
    private final LessonService lessonService;
    private final InstructorStudentCourseService instructorStudentCourseService;
    private final CreateCourseService createCourseService;

    @PatchMapping("/mark")
    public MarkResponse putMarkForLesson(@RequestBody PutMarkRequest request) {
        return markService.assignMark(request);
    }

    @PostMapping("/feedback")
    public FeedbackResponse giveFinalFeedback(@RequestBody GiveFinalFeedbackRequest request) {
        return feedbackService.giveFeedbackForCourse(request);
    }

    @GetMapping("/courses/{instructor}")
    public CoursesOfInstructorResponse allCourses(@PathVariable String instructor) {
        return courseService.getAllCoursesForInstructor(instructor);
    }

    @PostMapping("/course")
    public CourseInstructorLessonsResponse createCourse(@RequestBody CreateCourseRequest request) {
        return createCourseService.createCourse(request);
    }

    @PostMapping("/lesson")
    public List<LessonResponse> createLesson(@RequestBody CreateLessonsRequest request) {
        return lessonService.createLesson(request);
    }

    @GetMapping("/students/course/{courseId}")
    public StudentsOfCourseAndInstructorResponse getStudentsInCourse(@PathVariable UUID courseId, @AuthenticationPrincipal SecurityUser authenticatedUser) {
        return instructorStudentCourseService.getAllStudentsByCourse(courseId, authenticatedUser);
    }

}
