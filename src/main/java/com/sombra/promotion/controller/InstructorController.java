package com.sombra.promotion.controller;

import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.dto.request.CreateLessonsRequest;
import com.sombra.promotion.dto.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.dto.request.SaveMarkRequest;
import com.sombra.promotion.dto.response.*;
import com.sombra.promotion.service.generic.FeedbackService;
import com.sombra.promotion.service.generic.LessonService;
import com.sombra.promotion.service.generic.MarkService;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.specific.CreateCourseService;
import com.sombra.promotion.service.generic.manyToMany.transition.InstructorStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.service.util.statics.SecurityPrincipalUtil.authenticatedUserId;

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

    @PostMapping("/mark")
    public MarkResponse putMarkForLesson(@RequestBody SaveMarkRequest request) {
        request.setInstructorId(authenticatedUserId());
        return markService.saveMark(request);
    }

    @PostMapping("/feedback")
    public FeedbackResponse giveFinalFeedback(@RequestBody GiveFinalFeedbackRequest request) {
        request.setInstructorId(authenticatedUserId());
        return feedbackService.saveFeedback(request);
    }

    @PostMapping("/course")
    public CourseInstructorLessonsResponse createCourse(@RequestBody CreateCourseRequest request) {
        return createCourseService.createCourse(request);
    }

    @PostMapping("/lesson")
    public List<LessonResponse> createLesson(@RequestBody CreateLessonsRequest request) {
        request.setInstructorId(authenticatedUserId());
        return lessonService.saveLessons(request);
    }

    @GetMapping("/courses")
    public CoursesOfInstructorResponse getAllCoursesForInstructor() {
        return instructorCourseService.getAllCoursesForInstructor(authenticatedUserId());
    }

    @GetMapping("/students/in/{courseId}")
    public InstructorCourseStudentsResponse getAllStudentsInCourseForInstructor(@PathVariable UUID courseId) {
        return instructorStudentService.getAllStudentsInCourseForInstructor(authenticatedUserId(), courseId);
    }

}
