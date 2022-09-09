package helpers.testHelpers;

import com.sombra.promotion.enums.RoleEnum;
import org.jooq.DSLContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.sombra.promotion.Tables.*;
import static com.sombra.promotion.enums.RoleEnum.*;
import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.CourseMark.COURSE_MARK;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static com.sombra.promotion.tables.Mark.MARK;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;

public class InsertUtils {

    private final DSLContext ctx;
    private final PasswordEncoder passwordEncoder;
    private final SelectUtils select;

    public InsertUtils(DSLContext ctx, SelectUtils select) {
        this.ctx = ctx;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.select = select;
    }

    public UUID user(String username, String password) {
        return ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD)
                .values(username, passwordEncoder.encode(password))
                .returningResult(USER.ID)
                .fetchOne()
                .component1();
    }

    public UUID user(UUID id, String username, String password) {
        return ctx.insertInto(USER, USER.ID, USER.USERNAME, USER.PASSWORD)
                .values(id, username, passwordEncoder.encode(password))
                .returningResult(USER.ID)
                .fetchOne()
                .component1();
    }

    @Transactional
    public UUID admin(String username, String password) {
        return userWithRole(username, password, admin);
    }

    @Transactional
    public UUID student(String username, String password) {
        return userWithRole(username, password, student);
    }

    @Transactional
    public UUID instructor(String username, String password) {
        return userWithRole(username, password, instructor);
    }

    private UUID userWithRole(String username, String password, RoleEnum role) {
        UUID userAdminId = user(username, password);
        userRole(userAdminId, select.roleId(role));
        return userAdminId;
    }

    public UUID course(String course) {
        return ctx.insertInto(COURSE, COURSE.NAME)
                .values(course)
                .returning(COURSE.ID)
                .fetchOne()
                .component1();
    }

    public UUID lesson(String lesson, UUID courseId) {
        return ctx.insertInto(LESSON, LESSON.NAME, LESSON.COURSE_ID)
                .values(lesson, courseId)
                .returning(LESSON.ID)
                .fetchOne()
                .component1();
    }

    public UUID mark(int mark, UUID studentId, UUID instructorId, UUID lessonId) {
        return ctx.insertInto(MARK, MARK.STUDENT_ID, MARK.INSTRUCTOR_ID, MARK.LESSON_ID, MARK.MARK_)
                .values(studentId, instructorId, lessonId, mark)
                .returning(MARK.ID)
                .fetchOne()
                .component1();
    }

    public UUID courseMark(double mark, UUID courseId, UUID studentId, boolean passed) {
        return ctx.insertInto(COURSE_MARK, COURSE_MARK.MARK, COURSE_MARK.COURSE_ID, COURSE_MARK.STUDENT_ID, COURSE_MARK.COURSE_PASSED)
                .values(mark, courseId, studentId, passed)
                .returning(COURSE_MARK.ID)
                .fetchOne()
                .component1();
    }

    public UUID feedback(String feedback, UUID studentId, UUID instructorId, UUID courseId) {
        return ctx.insertInto(FEEDBACK, FEEDBACK.FEEDBACK_, FEEDBACK.STUDENT_ID, FEEDBACK.INSTRUCTOR_ID, FEEDBACK.COURSE_ID)
                .values(feedback, studentId, instructorId, courseId)
                .returning(FEEDBACK.ID)
                .fetchOne()
                .component1();
    }

    public UUID homework(byte[] file, UUID studentId, UUID lessonId) {
        return ctx.insertInto(HOMEWORK, HOMEWORK.FILE, HOMEWORK.STUDENT_ID, HOMEWORK.LESSON_ID)
                .values(file, studentId, lessonId)
                .returning(HOMEWORK.ID)
                .fetchOne()
                .component1();
    }

    public void userRole(UUID userId, UUID roleId) {
        ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, USER_ROLE.PREDEFINED)
                .values(userId, roleId, false)
                .execute();
    }

    public void studentCourse(UUID userId, UUID courseId) {
        ctx.insertInto(STUDENT_COURSE, STUDENT_COURSE.STUDENT_ID, STUDENT_COURSE.COURSE_ID)
                .values(userId, courseId)
                .execute();
    }

    public void instructorCourse(UUID userId, UUID courseId) {
        ctx.insertInto(INSTRUCTOR_COURSE, INSTRUCTOR_COURSE.INSTRUCTOR_ID, INSTRUCTOR_COURSE.COURSE_ID)
                .values(userId, courseId)
                .execute();
    }

}
