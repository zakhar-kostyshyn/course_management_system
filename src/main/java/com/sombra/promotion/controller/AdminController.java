package com.sombra.promotion.controller;

import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.dto.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.dto.request.AssignRoleRequest;
import com.sombra.promotion.service.InstructorCourseService;
import com.sombra.promotion.service.UserRoleService;
import com.sombra.promotion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRoleService userRoleService;
    private final InstructorCourseService instructorCourseService;
    private final UserService userService;


    @PatchMapping("/assign/role")
    public UserResponse assignRole(@RequestBody AssignRoleRequest request) {
        return userRoleService.assignRole(request);
    }

    @PatchMapping("/assign/instructor")
    public InstructorCourseResponse assignInstructorForCourse(@RequestBody AssignInstructorForCourseRequest request) {
        return instructorCourseService.assignInstructorOnCourse(request);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


}
