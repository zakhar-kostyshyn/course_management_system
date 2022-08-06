package com.sombra.promotion.controller;

import com.sombra.promotion.dto.details.InstructorCourseDetails;
import com.sombra.promotion.dto.details.UserDetails;
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
    public UserDetails assignRole(@RequestBody AssignRoleRequest request) {
        return userRoleService.assignRole(request);
    }

    @PatchMapping("/assign/instructor")
    public InstructorCourseDetails assignInstructorForCourse(@RequestBody AssignInstructorForCourseRequest request) {
        return instructorCourseService.assignInstructorOnCourse(request);
    }

    @GetMapping("/users")
    public List<UserDetails> getAllUsers() {
        return userService.getAllUsers();
    }


}
