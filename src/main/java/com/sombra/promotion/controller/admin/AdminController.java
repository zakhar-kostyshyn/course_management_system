package com.sombra.promotion.controller.admin;

import com.sombra.promotion.controller.admin.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.controller.admin.request.AssignRoleByAdminRequest;
import com.sombra.promotion.controller.admin.response.AssignInstructorResponse;
import com.sombra.promotion.controller.admin.response.AssignRoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @RestController
    @RequestMapping("/assign")
    class AdminAssignController {

        @PatchMapping("/role")
        public ResponseEntity<AssignRoleResponse> assignRole(
                @RequestBody AssignRoleByAdminRequest request
        ) {

        }

        @PatchMapping("/instructor")
        public ResponseEntity<AssignInstructorResponse> assignInstructorForCourse(
                @RequestBody AssignInstructorForCourseRequest request
        ) {

        }

    }


}
