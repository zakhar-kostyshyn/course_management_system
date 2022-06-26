package com.sombra.promotion.service;

import com.sombra.promotion.controller.admin.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.controller.admin.request.AssignRoleByAdminRequest;
import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.controller.student.request.CourseSubscriptionRequest;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignService {

    private UserRoleRepository userRoleRepository;
    private UserRepository userRepository;
    private InstructorCourseRepository instructorCourseRepository;
    private StudentCourseRepository studentCourseRepository;
    private MarkRepository markRepository;
    private FeedbackRepository feedbackRepository;

    public void assignRole(
            AssignRoleByAdminRequest request
    ) {
        RoleEnum roleEnum = RoleEnum.valueOf(request.getRole());
        userRoleRepository.setRoleForUser(request.getUsername(), roleEnum);
    }

    public void assignInstructorOnCourse(AssignInstructorForCourseRequest request) {
        RoleEnum userRole = userRepository.selectRoleTypeByUsername(request.getInstructor());
        if (!userRole.equals(RoleEnum.instructor))
            throw new RuntimeException("Cannot assign course on " + userRole + ". Role should be instructor");
        instructorCourseRepository.setInstructorForCourse(request.getInstructor(), request.getCourse());
    }

    public void subscribeStudentOnCourse(CourseSubscriptionRequest request) {
        RoleEnum userRole = userRepository.selectRoleTypeByUsername(request.getStudent());
        if (!userRole.equals(RoleEnum.student))
            throw new RuntimeException("Cannot subscribe on course user with role: " + userRole + ". Role should be instructor");

        studentCourseRepository.setStudentForCourse(request.getStudent(), request.getCourse());
    }

    public void assignMark(PutMarkRequest request) {

        RoleEnum userRole = userRepository.selectRoleTypeByUsername(request.getStudent());
        if (!userRole.equals(RoleEnum.instructor))
            throw new RuntimeException("User with role: " + userRole + " cannot assign mark");

        markRepository.insertMark(request);

    }

    public void giveFeedbackForCourse(GiveFinalFeedbackRequest request) {
        RoleEnum userRole = userRepository.selectRoleTypeByUsername(request.getStudent());
        if (!userRole.equals(RoleEnum.instructor))
            throw new RuntimeException("User with role: " + userRole + " cannot assign mark");

        feedbackRepository.insertFeedback(request);

    }
}
