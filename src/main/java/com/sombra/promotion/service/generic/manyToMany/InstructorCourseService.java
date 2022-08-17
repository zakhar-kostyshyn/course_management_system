package com.sombra.promotion.service.generic.manyToMany;

import com.sombra.promotion.dto.request.AssignInstructorForCourseRequest;
import com.sombra.promotion.dto.response.CoursesOfInstructorResponse;
import com.sombra.promotion.dto.response.InstructorCourseResponse;
import com.sombra.promotion.exception.NotFoundCourseBelongsForInstructorException;
import com.sombra.promotion.factory.specific.InstructorCourseFactory;
import com.sombra.promotion.repository.manyToMany.InstructorCourseRepository;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.InstructorCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InstructorCourseService {

    private final InstructorCourseRepository instructorCourseRepository;
    private final InstructorCourseFactory instructorCourseFactory;

    public InstructorCourseResponse saveInstructorCourse(AssignInstructorForCourseRequest request) {
        InstructorCourse instructorCourse = createInstructorCourse(request.getInstructorId(), request.getCourseId());
        instructorCourseRepository.save(instructorCourse);
        return instructorCourseFactory.build(instructorCourse);
    }

    private InstructorCourse createInstructorCourse(UUID instructorId, UUID courseId) {
        InstructorCourse instructorCourse = new InstructorCourse();
        instructorCourse.setInstructorId(instructorId);
        instructorCourse.setCourseId(courseId);
        return instructorCourse;
    }

    @Transactional
    public List<InstructorCourseResponse> saveInstructorCourse(UUID courseId, List<UUID> instructors) {
        List<InstructorCourse> instructorCourses = instructors.stream()
                .map(instructor -> createInstructorCourse(instructor, courseId))
                .peek(instructorCourseRepository::save)
                .collect(toList());
        return instructorCourseFactory.build(instructorCourses);
    }

    public CoursesOfInstructorResponse getAllCoursesForInstructor(UUID instructorId) {
        List<Course> coursesByInstructor = instructorCourseRepository.findByFirstId(instructorId);
        return instructorCourseFactory.build(instructorId, coursesByInstructor);
    }

    public void assertThatStudentInCourse(UUID instructorId, UUID courseId) {
        boolean isExist = instructorCourseRepository.exist(instructorId, courseId);
        if (!isExist) throw new NotFoundCourseBelongsForInstructorException(instructorId, courseId);
    }

}
