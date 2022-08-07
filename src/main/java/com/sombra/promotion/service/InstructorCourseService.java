package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.InstructorCourseDetails;
import com.sombra.promotion.dto.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.dto.request.CreateCourseRequest;
import com.sombra.promotion.exception.NotFoundCourseBelongsForInstructorException;
import com.sombra.promotion.factory.InstructorCourseDetailsFactory;
import com.sombra.promotion.repository.InstructorCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorCourseService {

    private final InstructorCourseRepository instructorCourseRepository;
    private final InstructorCourseDetailsFactory instructorCourseDetailsFactory;

    public InstructorCourseDetails assignInstructorOnCourse(AssignInstructorForCourseRequest request) {
        instructorCourseRepository.setInstructorForCourse(request.getInstructor(), request.getCourse());
        return instructorCourseDetailsFactory.build(request.getInstructor(), request.getCourse());
    }

    public InstructorCourseDetails createCourse(CreateCourseRequest request) {
        instructorCourseRepository.insertCourse(request.getCourse(), request.getInstructor());
        return instructorCourseDetailsFactory.build(request.getInstructor(), request.getCourse());
    }

    public void assertCourseBelongsToInstructor(UUID courseId, String instructor) {
        boolean isExist = instructorCourseRepository.existInstructorCourseBy(courseId, instructor);
        if (!isExist) throw new NotFoundCourseBelongsForInstructorException(courseId, instructor);
    }

}
