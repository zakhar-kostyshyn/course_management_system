package com.sombra.promotion.controller.student;

import com.sombra.promotion.controller.student.request.CourseSubscriptionRequest;
import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.controller.student.response.StudentCourseResponse;
import com.sombra.promotion.service.AssignService;
import com.sombra.promotion.service.HomeworkService;
import com.sombra.promotion.service.StudentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final AssignService assignService;
    private final StudentDetailsService studentDetailsService;
    private final HomeworkService homeworkService;

    @PatchMapping("/subscribe")
    public ResponseEntity<String> subscribeOnCourse(
            @RequestBody CourseSubscriptionRequest request
    ) {
        assignService.subscribeStudentOnCourse(request);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/homework")
    public ResponseEntity<String> uploadHomework(
            @RequestBody UploadHomeworkRequest request
    ) {
        homeworkService.saveHomework(request);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{student}/courses")
    public ResponseEntity<List<StudentCourseResponse>> allCourses(
            @PathVariable String student
    ) {
        return ResponseEntity.ok(studentDetailsService.getAllStudentCourses(student));
    }

}
