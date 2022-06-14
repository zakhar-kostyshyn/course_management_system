package com.sombra.promotion.service;

import com.sombra.promotion.controller.admin.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.controller.admin.request.AssignRoleByAdminRequest;
import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.controller.student.request.CourseSubscriptionRequest;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignService {

    private final DomainRepository domainRepository;
    public void assignRole(
            AssignRoleByAdminRequest request
    ) {
        RoleEnum roleEnum = RoleEnum.valueOf(request.getRole());
        domainRepository.setRoleForUser(request.getUsername(), roleEnum);
    }

    public void assignInstructorOnCourse(AssignInstructorForCourseRequest request) {
        RoleEnum userRole = domainRepository.selectRoleTypeByUsername(request.getInstructor());
        if (!userRole.equals(RoleEnum.instructor))
            throw new RuntimeException("Cannot assign course on " + userRole + ". Role should be instructor");
        domainRepository.setInstructorForCourse(request.getInstructor(), request.getCourse());
    }

    public void subscribeStudentOnCourse(CourseSubscriptionRequest request) {
        RoleEnum userRole = domainRepository.selectRoleTypeByUsername(request.getStudent());
        if (!userRole.equals(RoleEnum.student))
            throw new RuntimeException("Cannot subscribe on course user with role: " + userRole + ". Role should be instructor");

        domainRepository.setStudentForCourse(request.getStudent(), request.getCourse());
    }

    public void assignMark(PutMarkRequest request) {

        RoleEnum userRole = domainRepository.selectRoleTypeByUsername(request.getStudent());
        if (!userRole.equals(RoleEnum.instructor))
            throw new RuntimeException("User with role: " + userRole + " cannot assign mark");

        domainRepository.insertMark(request);

    }

    public void giveFeedbackForCourse(GiveFinalFeedbackRequest request) {
        RoleEnum userRole = domainRepository.selectRoleTypeByUsername(request.getStudent());
        if (!userRole.equals(RoleEnum.instructor))
            throw new RuntimeException("User with role: " + userRole + " cannot assign mark");

        domainRepository.insertFeedback(request);

    }
}















