package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.dto.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.exception.NotFoundCourseBelongsForInstructorException;
import com.sombra.promotion.factory.InstructorCourseFactory;
import com.sombra.promotion.repository.InstructorCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorCourseService {

    private final InstructorCourseRepository instructorCourseRepository;
    private final InstructorCourseFactory instructorCourseFactory;

    public InstructorCourseResponse assignInstructorOnCourse(AssignInstructorForCourseRequest request) {
        return instructorCourseFactory.build(
                instructorCourseRepository.setInstructorForCourse(request.getInstructor(), request.getCourse())
        );
    }

    public List<InstructorCourseResponse> createInstructorCourse(String course, List<String> instructors) {
        return instructorCourseFactory.build(
                instructorCourseRepository.insertInstructorCourse(course, instructors)
        );
    }

    public void assertCourseBelongsToInstructor(UUID courseId, String instructor) {
        boolean isExist = instructorCourseRepository.existInstructorCourseBy(courseId, instructor);
        if (!isExist) throw new NotFoundCourseBelongsForInstructorException(courseId, instructor);
    }

}
