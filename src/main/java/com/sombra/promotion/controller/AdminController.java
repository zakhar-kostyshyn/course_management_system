package com.sombra.promotion.controller;

import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.dto.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.dto.request.AssignRoleRequest;
import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.service.generic.manyToMany.InstructorCourseService;
import com.sombra.promotion.service.generic.manyToMany.UserRoleService;
import com.sombra.promotion.service.generic.UserService;
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


    @PatchMapping("/user-role")
    public UserRoleResponse assignRole(@RequestBody AssignRoleRequest request) {
        return userRoleService.saveUserRole(request.getUserId(), request.getRole());
    }

    @PatchMapping("/instructor-course")
    public InstructorCourseResponse assignInstructorForCourse(@RequestBody AssignInstructorForCourseRequest request) {
        return instructorCourseService.saveInstructorCourse(request);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


}
