package com.sombra.promotion.repository;

import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.enums.RoleEnum.instructor;
import static com.sombra.promotion.enums.RoleEnum.student;
import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.Feedback.FEEDBACK;
import static com.sombra.promotion.tables.Homework.HOMEWORK;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static com.sombra.promotion.tables.Mark.MARK;
import static com.sombra.promotion.tables.Role.ROLE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@JooqTest
@Import(RepositoryTestConfiguration.class)
@DisplayName("Tests for Domain repository")
class DomainRepositoryTest {

    @Autowired
    private DSLContext ctx;
    @Autowired
    private DomainRepository repository;

    @Nested
    @DisplayName("Tests for user insert")
    class InsertUser {

        @Test
        void must_insert_user() {

            // given
            UUID salt = UUID.randomUUID();

            // act
            UUID userId = repository.insertUser("test-username1", "test-hashedPassword1", salt, student);

            // fetch
            User actual = ctx.select()
                    .from(USER)
                    .where(USER.ID.eq(userId))
                    .fetchSingle()
                    .into(User.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("username", is("test-username1")),
                    hasProperty("password", is("test-hashedPassword1")),
                    hasProperty("salt", is(salt)),
                    hasProperty("id", notNullValue())
            ));

        }

        @Test
        void must_insert_user_role() {

            // given
            UUID salt = UUID.randomUUID();

            // act
            UUID userId = repository.insertUser("test-username1", "test-hashedPassword1", salt, student);

            // fetch
            Record actual = ctx.select()
                    .from(USER_ROLE)
                    .join(ROLE)
                    .on(USER_ROLE.ROLE_ID
                            .eq(ROLE.ID)
                    ).where(USER_ROLE.USER_ID
                            .eq(userId)
                    ).fetchSingle();

            RoleEnum roleStudentName = actual.into(ROLE.NAME).component1();
            UUID userRoleUserId = actual.into(USER_ROLE.USER_ID).component1();

            // verify
            assertThat(roleStudentName, is(student));
            assertThat(userRoleUserId, is(userId));

        }

    }


    @Nested
    @DisplayName("Set role for user")
    class SetRoleForUser {

        @Test
        void must_set_one_role_for_user() {

            // given
            String username = "test-username";
            RoleEnum roleType = instructor;

            ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(username, "test-password", UUID.randomUUID())
                    .execute();

            // act
            repository.setRoleForUser(username, roleType);

            // fetch
            RoleEnum actual = ctx.select()
                    .from(USER)
                    .join(USER_ROLE).on(USER_ROLE.USER_ID.eq(USER.ID))
                    .join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                    .where(USER.USERNAME.eq(username))
                    .fetchAny(ROLE.NAME);


            // verify
            assertThat(actual, is(roleType));

        }

        @Test
        void must_do_nothing_when_user_already_has_this_role() {

            // given
            String username = "test-username";
            RoleEnum roleType = instructor;

            UUID createdUserId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(username, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID instructorRoleId = ctx.select(ROLE.ID)
                    .from(ROLE)
                    .where(ROLE.NAME.eq(roleType))
                    .fetchAny()
                    .component1();

            ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, USER_ROLE.PREDEFINED)
                    .values(createdUserId, instructorRoleId, false)
                    .execute();

            // act
            repository.setRoleForUser(username, roleType);

            // fetch
            List<RoleEnum> actual = ctx.select()
                    .from(USER)
                    .join(USER_ROLE).on(USER_ROLE.USER_ID.eq(USER.ID))
                    .join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                    .where(USER.USERNAME.eq(username))
                    .fetch(ROLE.NAME);


            // verify
            assertThat(actual, contains(is(roleType)));
        }

    }

    @Nested
    @DisplayName("Select user ID by username")
    class SelectUserIdByUsername {

        @Test
        void must_select_id_by_username() {

            // give
            UUID userId = UUID.randomUUID();
            String username = "test-username";

            ctx.insertInto(USER, USER.ID, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(userId, username, "test-password", UUID.randomUUID())
                    .execute();

            // act
            UUID actual = repository.selectUserIdByUsername(username);

            // verify
            assertThat(actual, is(userId));

        }

    }

    @Nested
    @DisplayName("Select role type by username")
    class SelectRoleTypeByUsername {

        @Test
        void must_select_roleType_by_username() {

            // setup
            String username = "test-username";
            RoleEnum roleType = student;

            UUID createdUserId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(username, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID instructorRoleId = ctx.select(ROLE.ID)
                    .from(ROLE)
                    .where(ROLE.NAME.eq(roleType))
                    .fetchAny()
                    .component1();

            ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, USER_ROLE.PREDEFINED)
                    .values(createdUserId, instructorRoleId, false)
                    .execute();

            // act
            RoleEnum actual = repository.selectRoleTypeByUsername(username);

            // verify
            assertThat(actual, is(roleType));

        }

    }


    @Nested
    @DisplayName("Select role ID by role type")
    class SelectRoleIdByRoleType {

        @Test
        void must_select_roleID_by_role_type() {

            // setup
            RoleEnum givenRole = student;
            UUID givenStudentId = ctx.select(ROLE.ID)
                    .from(ROLE)
                    .where(ROLE.NAME.eq(student))
                    .fetchAny()
                    .component1();


            // act
            UUID actual = repository.selectRoleIDByRoleType(givenRole);

            // verify
            assertThat(actual, is(givenStudentId));

        }

    }


    @Nested
    @DisplayName("Select instructor for course")
    class SelectInstructorForCourse {

        @Test
        void must_select_instructor_for_course() {

            // given
            String givenCourseName = "test-courseName";
            String givenUsername = "test-username";

            UUID givenUserId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenUsername, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            // act
            repository.setInstructorForCourse(givenUsername, givenCourseName);

            // fetch
            InstructorCourse actual = ctx.select()
                    .from(INSTRUCTOR_COURSE)
                    .fetchSingleInto(InstructorCourse.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("instructorId", is(givenUserId)),
                    hasProperty("courseId", is(givenCourseId))
            ));

        }

    }

    @Nested
    @DisplayName("Select courses by student")
    class SelectCoursesByStudent {

        @Test
        void must_select_courses_by_student() {

            // given
            String givenUsername = "test-username";
            String givenCourseNamePrefix = "test-course-";

            UUID givenUserId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenUsername, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            List<UUID> coursesIds = range(1, 5)
                    .mapToObj(i -> ctx.insertInto(COURSE, COURSE.NAME)
                            .values(givenCourseNamePrefix + i)
                            .returning(COURSE.ID)
                            .fetchOne()
                            .component1()
                    ).collect(toList());

            coursesIds.stream()
                    .limit(3)
                    .forEach(courseId -> ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                            .values(givenUserId, courseId)
                            .execute()
                    );

            // act
            List<Course> actual = repository.selectCoursesByStudent(givenUsername);

            // verify
            assertThat(actual, allOf(
                    hasItem(hasProperty("name", is("test-course-1"))),
                    hasItem(hasProperty("name", is("test-course-2"))),
                    hasItem(hasProperty("name", is("test-course-3"))),
                    not(hasItem(hasProperty("name", is("test-course-4"))))
            ));

        }

    }

    @Nested
    @DisplayName("Select mark by student username and lesson ID")
    class SelectMarkByStudentUsernameAndLessonId {

        @Test
        void must_select_mark_by_student_username_and_lesson_id() {

            // given
            String givenCourseName = "test-courseName";
            String givenStudent = "test-student";
            String givenInstructor = "test-instructor";
            String givenLessonName = "test-lesson";
            int givenMark = 100;

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenLessonId = ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLessonName)
                    .returning(LESSON.ID)
                    .fetchOne()
                    .component1();

            ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                    .values(givenStudentId, givenCourseId)
                    .execute();

            UUID givenMarkId = ctx.insertInto(MARK, MARK.STUDENT_ID, MARK.INSTRUCTOR_ID, MARK.LESSON_ID, MARK.MARK_)
                    .values(givenStudentId, givenInstructorId, givenLessonId, givenMark)
                    .returning(MARK.ID)
                    .fetchOne()
                    .component1();

            // act
            Mark actual = repository.selectMarkByStudentUsernameAndLessonId(givenStudent, givenLessonId);

            // verify
            assertThat(actual, allOf(
                    hasProperty("id", is(givenMarkId)),
                    hasProperty("mark", is(givenMark)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId))
            ));

        }
    }

    @Nested
    @DisplayName("Select Lesson ")
    class SelectLessonByNameAndCourse {

        @Test
        void must_select_lesson_by_name_and_course() {

            // given
            String givenLesson = "test-lesson";
            String givenCourse = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLesson)
                    .execute();

            // act
            UUID actual = repository.selectLessonByNameAndCourse(givenLesson, givenCourse);

            // verify
            assertThat(actual, is(actual));

        }

    }

    @Nested
    @DisplayName("Insert homework")
    class InsertHomework {

        @Test
        void must_insert_homework() throws IOException {

            // given
            byte[] givenContent = getClass().getResourceAsStream("/test-homework.txt").readAllBytes();
            MultipartFile givenHomework = new MockMultipartFile("test-homework.txt", "test-homework.txt", "text/plain", givenContent);
            String givenStudent = "test-student";
            String givenLesson = "test-lesson";
            String givenCourse = "given-course";
            UploadHomeworkRequest givenRequest =
                    new UploadHomeworkRequest(givenHomework, givenStudent, givenLesson, givenCourse);

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenLessonId = ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLesson)
                    .returning(LESSON.ID)
                    .fetchOne()
                    .component1();


            // act
            repository.insertHomework(givenRequest);

            // fetch
            Homework actual = ctx.select()
                    .from(HOMEWORK)
                    .fetchAnyInto(Homework.class);

            // verify

            assertThat(actual, allOf(
                    hasProperty("file", is(givenContent)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId)),
                    hasProperty("id", notNullValue())
            ));

        }

    }

    @Nested
    @DisplayName("Set mark for lesson")
    class InsertMark {

        @Test
        void must_insert_mark() {

            // given
            String givenCourseName = "test-courseName";
            String givenStudent = "test-student";
            String givenInstructor = "test-instructor";
            String givenLessonName = "test-lesson";
            int givenMark = 100;

            PutMarkRequest givenRequest = new PutMarkRequest(
                    givenMark,
                    givenStudent,
                    givenInstructor,
                    givenLessonName,
                    givenCourseName
            );

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenLessonId = ctx.insertInto(LESSON, LESSON.COURSE_ID, LESSON.NAME)
                    .values(givenCourseId, givenLessonName)
                    .returning(LESSON.ID)
                    .fetchOne()
                    .component1();

            ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                    .values(givenStudentId, givenCourseId)
                    .execute();

            // act
            repository.insertMark(givenRequest);

            // fetch
            Mark actual = ctx.select().from(MARK).fetchSingleInto(Mark.class);

            // verify
            // verify
            assertThat(actual, allOf(
                    hasProperty("id", notNullValue()),
                    hasProperty("mark", is(givenMark)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("lessonId", is(givenLessonId))
            ));

        }

    }

    @Nested
    @DisplayName("Insert feedback")
    class InsertFeedback {

        @Test
        void must_insert_feedback() {

            // given
            String givenFeedback = "test-feedback";
            String givenCourse = "test-course";
            String givenStudent = "test-student";
            String givenInstructor = "test-instructor";

            GiveFinalFeedbackRequest givenRequest = new GiveFinalFeedbackRequest(
                    givenFeedback,
                    givenCourse,
                    givenStudent,
                    givenInstructor
            );


            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            // act
            repository.insertFeedback(givenRequest);

            // fetch
            Feedback actual = ctx.select()
                    .from(FEEDBACK)
                    .fetchSingleInto(Feedback.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("feedback", is(givenFeedback)),
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("instructorId", is(givenInstructorId)),
                    hasProperty("courseId", is(givenCourseId)),
                    hasProperty("id", notNullValue())
            ));

        }

    }

    @Nested
    @DisplayName("Select course by instructor")
    class SelectCourseByInstructor {

        @Test
        void must_select_courses_by_instructor() {

            // given
            String givenInstructor = "test-instructor";
            String givenCourseNamePrefix = "test-course-";

            UUID givenInstructorId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenInstructor, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();


            List<UUID> coursesIds = range(1, 5)
                    .mapToObj(i -> ctx.insertInto(COURSE, COURSE.NAME)
                            .values(givenCourseNamePrefix + i)
                            .returning(COURSE.ID)
                            .fetchOne()
                            .component1()
                    ).collect(toList());

            coursesIds.stream()
                    .limit(3)
                    .forEach(courseId -> ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                            .values(givenInstructorId, courseId)
                            .execute()
                    );

            // act
            List<Course> actual = repository.selectCoursesByInstructor(givenInstructor);

            // verify
            assertThat(actual, allOf(
                    hasItem(hasProperty("name", is("test-course-1"))),
                    hasItem(hasProperty("name", is("test-course-2"))),
                    hasItem(hasProperty("name", is("test-course-3"))),
                    not(hasItem(hasProperty("name", is("test-course-4"))))
            ));

        }

    }

    @Nested
    @DisplayName("Select users by course id")
    class SelectUsersByCourseId {

        @Test
        void must_select_users_by_course_id() {

            // given
            String givenStudentPrefix = "test-student-";
            String givenCourseName = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            List<UUID> studentIds = range(1, 5)
                    .mapToObj(i -> ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                            .values(givenStudentPrefix + i, "test-password", UUID.randomUUID())
                            .returningResult(USER.ID)
                            .fetchOne()
                            .component1()
                    ).collect(toList());

            studentIds.stream()
                    .limit(3)
                    .forEach(studentId -> ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                            .values(studentId, givenCourseId)
                            .execute()
                    );

            // act
            List<User> actual = repository.selectStudentByCourseId(givenCourseId);

            // verify
            assertThat(actual, allOf(
                    hasItem(hasProperty("username", is("test-student-1"))),
                    hasItem(hasProperty("username", is("test-student-2"))),
                    hasItem(hasProperty("username", is("test-student-3"))),
                    not(hasItem(hasProperty("username", is("test-student-4"))))
            ));

        }

    }

    @Nested
    @DisplayName("Select lessons by course")
    class SelectLessonsByCourse {

        @Test
        void must_select_lessons_by_course() {

            // given
            String givenLessonPrefix = "test-lesson-";


            String givenCourseName1 = "test-course-1";
            UUID givenCourseId1 = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName1)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();
            range(1, 4).forEach(i -> ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                    .values(givenLessonPrefix + i, givenCourseId1)
                    .returningResult(LESSON.ID)
                    .fetchOne()
                    .component1());


            String givenCourseName2 = "test-course-2";
            UUID givenCourseId2 = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourseName2)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();
            ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                    .values(givenLessonPrefix + 4, givenCourseId2)
                    .returningResult(LESSON.ID)
                    .execute();


            // act
            List<Lesson> actual = repository.selectLessonsByCourse(givenCourseName1);

            // verify
            assertThat(actual, allOf(
                    hasItem(hasProperty("name", is("test-lesson-1"))),
                    hasItem(hasProperty("name", is("test-lesson-2"))),
                    hasItem(hasProperty("name", is("test-lesson-3"))),
                    not(hasItem(hasProperty("name", is("test-lesson-4"))))
            ));

        }

    }

    @Nested
    @DisplayName("Set student for course")
    class SetStudentForCourse {

        @Test
        void must_set_student_for_course() {

            // given
            String givenStudent = "test-student";
            String givenCourse = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            UUID givenStudentId = ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                    .values(givenStudent, "test-password", UUID.randomUUID())
                    .returningResult(USER.ID)
                    .fetchOne()
                    .component1();

            // act
            repository.setStudentForCourse(givenStudent, givenCourse);

            // fetch
            StudentCourse actual = ctx.select().from(STUDENT_COURSE).fetchAnyInto(StudentCourse.class);

            // verify
            assertThat(actual, allOf(
                    hasProperty("studentId", is(givenStudentId)),
                    hasProperty("courseId", is(givenCourseId))
            ));

        }

    }

    @Nested
    @DisplayName("Select Course id by name")
    class SelectCourseIdByName {

        @Test
        void must_select_course_id_by_name() {

            // given
            String givenCourse = "test-course";

            UUID givenCourseId = ctx.insertInto(COURSE, COURSE.NAME)
                    .values(givenCourse)
                    .returning(COURSE.ID)
                    .fetchOne()
                    .component1();

            // act
            UUID actual = repository.selectCourseIdByName(givenCourse);

            // verify
            assertThat(actual, is(givenCourseId));

        }

    }

}