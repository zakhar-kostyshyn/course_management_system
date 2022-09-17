package com.sombra.promotion.unit.service.common.manyToMany;

import com.sombra.promotion.dto.response.CoursesOfStudentResponse;
import com.sombra.promotion.dto.response.StudentCourseResponse;
import com.sombra.promotion.exception.NotFoundCourseBelongsForStudentException;
import com.sombra.promotion.mapper.StudentCourseMapper;
import com.sombra.promotion.repository.manyToMany.StudentCourseRepository;
import com.sombra.promotion.service.common.manyToMany.StudentCourseService;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.StudentCourse;
import com.sombra.promotion.tables.pojos.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentCourseServiceTest {

    @Mock StudentCourseRepository studentCourseRepository;
    @Mock
    StudentCourseMapper studentCourseMapper;
    @InjectMocks
    StudentCourseService studentCourseService;
    @Captor ArgumentCaptor<StudentCourse> studentCourseCaptor;

    UUID studentId;
    UUID courseId;
    StudentCourseResponse studentCourseResponse;
    List<User> usersByCourse;
    List<Course> coursesByStudent;
    CoursesOfStudentResponse coursesOfStudentResponse;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        studentCourseResponse = mock(StudentCourseResponse.class);
        usersByCourse = List.of(mock(User.class));
        coursesByStudent = List.of(mock(Course.class));
        coursesOfStudentResponse = mock(CoursesOfStudentResponse.class);
    }


    @Test
    void must_save_student_course() {
        studentCourseService.saveStudentCourse(studentId, courseId);
        verify(studentCourseRepository).persist(studentCourseCaptor.capture());
        assertThat(studentCourseCaptor.getValue().getStudentId(), is(studentId));
        assertThat(studentCourseCaptor.getValue().getCourseId(), is(courseId));
    }


    @Test
    void must_return_response_of_saved_student_course() {
        when(studentCourseMapper.build(any(StudentCourse.class))).thenReturn(studentCourseResponse);
        var result = studentCourseService.saveStudentCourse(studentId, courseId);
        assertThat(result, sameInstance(studentCourseResponse));
    }

    @Test
    void must_get_students_by_course() {
        when(studentCourseRepository.findBySecondId(any())).thenReturn(usersByCourse);
        var result = studentCourseService.getStudentsByCourse(courseId);
        verify(studentCourseRepository).findBySecondId(courseId);
        assertThat(result, sameInstance(usersByCourse));
    }

    @Test
    void must_get_all_courses_for_student_response() {
        when(studentCourseRepository.findByFirstId(any())).thenReturn(coursesByStudent);
        when(studentCourseMapper.build(any(), any())).thenReturn(coursesOfStudentResponse);

        var result = studentCourseService.getAllCoursesForStudent(studentId);

        verify(studentCourseRepository).findByFirstId(studentId);
        verify(studentCourseMapper).build(studentId, coursesByStudent);
        assertThat(result, sameInstance(coursesOfStudentResponse));
    }

    @Test
    void must_throw_exception_when_student_course_not_exist() {
        when(studentCourseRepository.exist(any(), any())).thenReturn(false);
        assertThrows(
                NotFoundCourseBelongsForStudentException.class,
                () -> studentCourseService.assertThatStudentInCourse(studentId, courseId)
        );
    }

}
