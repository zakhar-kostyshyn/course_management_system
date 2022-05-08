package com.sombra.promotion.controller.instructor;

import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.controller.instructor.response.InstructorCourseResponse;
import com.sombra.promotion.controller.instructor.response.FeedbackResponse;
import com.sombra.promotion.controller.instructor.response.PutMarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    @PatchMapping("/mark")
    public ResponseEntity<PutMarkResponse> putMarkForLesson(
            @RequestBody PutMarkRequest request
    ) {

    }

    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponse> giveFinalFeedback(
            @RequestBody GiveFinalFeedbackRequest request
    ) {

    }

    @GetMapping("/courses/{instructor}")
    public ResponseEntity<List<InstructorCourseResponse>> allCourses(
            @PathVariable String instructor
    ) {

    }


}
