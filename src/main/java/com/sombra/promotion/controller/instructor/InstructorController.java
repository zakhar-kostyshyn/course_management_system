package com.sombra.promotion.controller.instructor;

import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.controller.instructor.response.InstructorCourseResponse;
import com.sombra.promotion.controller.instructor.response.FeedbackResponse;
import com.sombra.promotion.controller.instructor.response.PutMarkResponse;
import com.sombra.promotion.service.AssignService;
import com.sombra.promotion.service.InstructorDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final AssignService assignService;
    private final InstructorDetailsService instructorDetailsService;

    @PatchMapping("/mark")
    public ResponseEntity<String> putMarkForLesson(
            @RequestBody PutMarkRequest request
    ) {
        assignService.assignMark(request);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/feedback")
    public ResponseEntity<String> giveFinalFeedback(
            @RequestBody GiveFinalFeedbackRequest request
    ) {
        assignService.giveFeedbackForCourse(request);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/courses/{instructor}")
    public ResponseEntity<List<InstructorCourseResponse>> allCourses(
            @PathVariable String instructor
    ) {
        return ResponseEntity.ok(instructorDetailsService.getInstructorCourseDetails(instructor));
    }


}
