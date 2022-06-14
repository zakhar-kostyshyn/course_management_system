package com.sombra.promotion.repository;

import com.sombra.promotion.controller.instructor.request.GiveFinalFeedbackRequest;
import com.sombra.promotion.controller.instructor.request.PutMarkRequest;
import com.sombra.promotion.controller.student.request.UploadHomeworkRequest;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.pojos.Lesson;
import com.sombra.promotion.tables.pojos.Mark;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

@Repository
@RequiredArgsConstructor
@Slf4j
public class DomainRepository {

    private final DSLContext ctx;

    public UUID insertUser(
            String username,
            String hashedPassword,
            UUID salt,
            RoleEnum roleEnum
    ) {
        UUID createdUserID =
                ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD, USER.SALT)
                        .values(username, hashedPassword, salt)
                        .returningResult(USER.ID)
                        .fetchOne()
                        .value1();

        UUID idByRoleType = selectRoleIDByRoleType(roleEnum);
        ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID)
                .values(createdUserID, idByRoleType)
                .execute();

        return createdUserID;
    }

    public void setRoleForUser(String username, RoleEnum roleEnum) {

        UUID userId = selectUserIdByUsername(username);
        UUID roleId = selectRoleIDByRoleType(roleEnum);

        if (ctx.fetchExists(
                ctx.select()
                        .from(USER_ROLE)
                        .where(USER_ROLE.USER_ID.eq(userId)
                        .and(USER_ROLE.ROLE_ID.eq(roleId)))
            )
        ) {
            log.warn(String.format("User %s already granted by role: %s. No action done.", username, roleEnum));
        } else {
            ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, USER_ROLE.PREDEFINED)
                    .values(userId, roleId, false)
                    .execute();
        }
    }

    public UUID selectUserIdByUsername(String username) {
        return ctx.select(USER.ID)
                .from(USER)
                .where(USER.USERNAME.eq(username))
                .fetchOne()
                .component1();
    }

    public RoleEnum selectRoleTypeByUsername(String username) {
        return ctx.select(ROLE.NAME)
                .from(USER)
                .join(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .where(USER.USERNAME.eq(username))
                .fetchOne()
                .component1();
    }

    public UUID selectRoleIDByRoleType(RoleEnum roleEnum) {
        return ctx.select(ROLE.ID)
                .from(ROLE)
                .where(ROLE.NAME.eq(roleEnum))
                .fetchOne()
                .component1();
    }

    public void setInstructorForCourse(String instructorUsername, String courseName) {
        UUID instructorId = selectUserIdByUsername(instructorUsername);
        UUID courseId = selectCourseIdByName(courseName);
        ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(instructorId, courseId)
                .execute();
    }

    public UUID selectCourseIdByName(String courseName) {
        return ctx.select(COURSE.ID)
                .from(COURSE)
                .where(COURSE.NAME.eq(courseName))
                .fetchOne()
                .component1();
    }

    public void setStudentForCourse(String studentUsername, String courseName) {
        UUID studentId = selectUserIdByUsername(studentUsername);
        UUID courseId = selectCourseIdByName(courseName);

        ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                .values(studentId, courseId)
                .execute();
    }

    public List<Course> selectCoursesByStudent(String student) {
        return ctx.select(COURSE.ID, COURSE.NAME)
                .from(COURSE)
                .join(STUDENT_COURSE).on(STUDENT_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(STUDENT_COURSE.STUDENT_ID.eq(USER.ID))
                .where(USER.USERNAME.eq(student))
                .fetchInto(Course.class);
    }

    public List<Lesson> selectLessonsByCourse(String courseName) {
        return ctx.select(LESSON.ID, LESSON.NAME, LESSON.COURSE_ID)
                .from(LESSON)
                .join(COURSE).on(COURSE.ID.eq(LESSON.COURSE_ID))
                .where(COURSE.NAME.eq(courseName))
                .fetchInto(Lesson.class);
    }

    public Mark selectMarkByStudentUsernameAndLessonId(String studentUsername, UUID lessonId) {
        UUID userId = selectUserIdByUsername(studentUsername);
        return ctx.select()
                .from(MARK)
                .where(MARK.STUDENT_ID.eq(userId).and(MARK.LESSON_ID.eq(lessonId)))
                .fetchSingleInto(Mark.class);
    }

    @SneakyThrows
    public void insertHomework(UploadHomeworkRequest request) {
        UUID studentId = selectUserIdByUsername(request.getStudent());
        UUID lessonId = selectLessonByNameAndCourse(request.getLesson(), request.getCourse());
        ctx.insertInto(HOMEWORK, HOMEWORK.FILE, HOMEWORK.LESSON_ID, HOMEWORK.STUDENT_ID)
                .values(request.getHomework().getBytes(), lessonId, studentId)
                .execute();
    }

    public UUID selectLessonByNameAndCourse(String lessonName, String courseName) {
        UUID courseId = selectCourseIdByName(courseName);
        return ctx.select(LESSON.ID)
                .from(LESSON)
                .where(LESSON.COURSE_ID.eq(courseId).and(LESSON.NAME.eq(lessonName)))
                .fetchOne()
                .component1();
    }


    public void insertMark(PutMarkRequest request) {
        // TODO run migration and add COURSE_ID
        UUID studentId = selectUserIdByUsername(request.getStudent());
        UUID instructorId = selectUserIdByUsername(request.getInstructor());
        UUID lessonId = selectLessonByNameAndCourse(request.getLesson(), request.getCourse());
        ctx.insertInto(MARK, MARK.STUDENT_ID, MARK.INSTRUCTOR_ID, MARK.LESSON_ID, MARK.MARK_)
                .values(studentId, instructorId, lessonId, request.getMark())
                .execute();
    }

    public void insertFeedback(GiveFinalFeedbackRequest request) {

        UUID studentId = selectUserIdByUsername(request.getStudent());
        UUID instructorId = selectUserIdByUsername(request.getInstructor());
        UUID courseId = selectCourseIdByName(request.getCourse());

        ctx.insertInto(FEEDBACK, FEEDBACK.COURSE_ID, FEEDBACK.FEEDBACK_, FEEDBACK.STUDENT_ID, FEEDBACK.INSTRUCTOR_ID)
                .values(courseId, request.getFeedback(), studentId, instructorId)
                .execute();

    }

    public List<Course> selectCoursesByInstructor(String instructor) {
        return ctx.select(COURSE.ID, COURSE.NAME)
                .from(COURSE)
                .join(INSTRUCTOR_COURSE).on(INSTRUCTOR_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(INSTRUCTOR_COURSE.INSTRUCTOR_ID))
                .where(USER.USERNAME.eq(instructor))
                .fetchInto(Course.class);
    }

    public List<User> selectStudentByCourseId(UUID courseId) {
        return ctx.select(USER.ID, USER.USERNAME, USER.PASSWORD, USER.SALT)
                .from(COURSE)
                .join(STUDENT_COURSE).on(STUDENT_COURSE.COURSE_ID.eq(COURSE.ID))
                .join(USER).on(USER.ID.eq(STUDENT_COURSE.STUDENT_ID))
                .where(COURSE.ID.eq(courseId))
                .fetchInto(User.class);
    }

}
