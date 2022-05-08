package com.sombra.promotion.controller.student;

import com.sombra.promotion.controller.student.request.CourseSubscriptionRequest;
import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.controller.student.response.StudentCourseResponse;
import com.sombra.promotion.controller.student.response.StudentSubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    @PatchMapping("/subscribe")
    public ResponseEntity<StudentSubscriptionResponse> subscribeOnCourse(
            @RequestBody CourseSubscriptionRequest request
    ) {

    }

    @PutMapping("/homework")
    public void uploadHomework(
            @RequestBody UploadHomeworkRequest request
    ) {

    }

    @GetMapping("/courses/{student}")
    public ResponseEntity<StudentCourseResponse> allCourses(
            @PathVariable String student
    ) {

    }

}
