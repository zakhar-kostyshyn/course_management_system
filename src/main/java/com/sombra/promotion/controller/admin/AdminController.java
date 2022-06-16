package com.sombra.promotion.controller.admin;

import com.sombra.promotion.controller.admin.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.controller.admin.request.AssignRoleByAdminRequest;
import com.sombra.promotion.service.AllUsersService;
import com.sombra.promotion.service.AssignService;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AllUsersService allUsersService;

    @RestController
    @RequestMapping("/assign")
    @RequiredArgsConstructor
    class AdminAssignController {

        private final AssignService assignService;

        @PatchMapping("/role")
        public ResponseEntity<String> assignRole(
                @RequestBody AssignRoleByAdminRequest request
        ) {
            assignService.assignRole(request);
            return ResponseEntity.ok("Success");
        }

        @PatchMapping("/instructor")
        public ResponseEntity<String> assignInstructorForCourse(
                @RequestBody AssignInstructorForCourseRequest request
        ) {
            assignService.assignInstructorOnCourse(request);
            return ResponseEntity.ok("Success");
        }

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(allUsersService.getAllUsers());
    }


}
