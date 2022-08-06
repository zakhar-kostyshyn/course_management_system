package com.sombra.promotion.controller;

import com.sombra.promotion.dto.details.*;
import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.request.CreateLessonRequest;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.dto.request.PutMarkRequest;
import com.sombra.promotion.security.SecurityUser;
import com.sombra.promotion.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final MarkService markService;
    private final FeedbackService feedbackService;
    private final CourseService courseService;
    private final InstructorCourseService instructorCourseService;
    private final LessonService lessonService;
    private final InstructorStudentCourseService instructorStudentCourseService;

    @PatchMapping("/mark")
    public MarkDetails putMarkForLesson(@RequestBody PutMarkRequest request) {
        return markService.assignMark(request);
    }

    @PostMapping("/feedback")
    public FeedbackDetails giveFinalFeedback(@RequestBody GiveFinalFeedbackRequest request) {
        return feedbackService.giveFeedbackForCourse(request);
    }

    @GetMapping("/courses/{instructor}")
    public CoursesOfInstructorDetails allCourses(@PathVariable String instructor) {
        return courseService.getAllCoursesForInstructor(instructor);
    }

    @PostMapping("/course")
    public InstructorCourseDetails createCourse(@RequestBody CreateCourseRequest request) {
        return instructorCourseService.createCourse(request);
    }

    @PostMapping("/lesson")
    public LessonDetails createLesson(@RequestBody CreateLessonRequest request) {
        return lessonService.createLesson(request);
    }

    @GetMapping("/students/course/{courseId}")
    public StudentsOfCourseAndInstructorDetails getStudentsInCourse(@PathVariable UUID courseId, @AuthenticationPrincipal SecurityUser authenticatedUser) {
        return instructorStudentCourseService.getAllStudentsByCourse(courseId, authenticatedUser);
    }

}
