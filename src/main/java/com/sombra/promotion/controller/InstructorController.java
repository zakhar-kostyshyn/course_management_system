package com.sombra.promotion.controller;

import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.dto.request.SaveMarkRequest;
import com.sombra.promotion.dto.response.*;
import com.sombra.promotion.security.SecurityUser;
import com.sombra.promotion.service.generic.FeedbackService;
import com.sombra.promotion.service.generic.LessonService;
import com.sombra.promotion.service.generic.MarkService;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.specific.CreateCourseService;
import com.sombra.promotion.service.specific.InstructorStudentService;
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
    private final LessonService lessonService;
    private final InstructorStudentService instructorStudentService;
    private final InstructorCourseService instructorCourseService;
    private final CreateCourseService createCourseService;

    @PatchMapping("/mark")
    public MarkResponse putMarkForLesson(@RequestBody SaveMarkRequest request) {
        return markService.saveMark(request);
    }

    @PostMapping("/feedback")
    public FeedbackResponse giveFinalFeedback(@RequestBody GiveFinalFeedbackRequest request) {
        return feedbackService.saveFeedback(request);
    }

    @GetMapping("/courses/{instructorId}")
    public CoursesOfInstructorResponse allCourses(@PathVariable UUID instructorId) {
        return instructorCourseService.getAllCoursesForInstructor(instructorId);
    }

    @PostMapping("/course")
    public CourseInstructorLessonsResponse createCourse(@RequestBody CreateCourseRequest request) {
        return createCourseService.createCourse(request);
    }

    @PostMapping("/lesson")
    public List<LessonResponse> createLesson(@RequestBody CreateLessonsRequest request) {
        return lessonService.saveLessons(request);
    }

    @GetMapping("/students/course/{courseId}")
    public InstructorCourseStudentsResponse getStudentsInCourse(@AuthenticationPrincipal SecurityUser authenticatedUser, @PathVariable UUID courseId) {
        return instructorStudentService.getAllStudentsInCourseForInstructor(authenticatedUser.getId(), courseId);
    }

}
