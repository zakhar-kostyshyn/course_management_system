package helpers.testHelpers;

import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.Role;
import com.sombra.promotion.tables.StudentCourse;
import com.sombra.promotion.tables.User;
import org.jooq.DSLContext;

import java.util.List;

import static com.sombra.promotion.tables.Course.COURSE;
import static com.sombra.promotion.tables.CourseMark.COURSE_MARK;
import static com.sombra.promotion.tables.Feedback.FEEDBACK;
import static com.sombra.promotion.tables.Homework.HOMEWORK;
import static com.sombra.promotion.tables.InstructorCourse.INSTRUCTOR_COURSE;
import static com.sombra.promotion.tables.Lesson.LESSON;
import static com.sombra.promotion.tables.Mark.MARK;
import static com.sombra.promotion.tables.Role.ROLE;
import static com.sombra.promotion.tables.StudentCourse.STUDENT_COURSE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;

public class DeleteUtils {

    private final DSLContext ctx;
    private final List<String> nonDeleteUsernames;

    public DeleteUtils(DSLContext ctx) {
        this.ctx = ctx;
        this.nonDeleteUsernames = List.of("admin001");
    }

    public void user() {
        ctx.delete(USER).where(USER.USERNAME.notIn(nonDeleteUsernames)).execute();
    }

    public void userRole() {
        ctx.delete(USER_ROLE)
                .where(USER_ROLE.USER_ID.notIn(
                        ctx.select(USER.ID)
                                .from(USER)
                                .where(USER.USERNAME.in(nonDeleteUsernames))
                                .fetch()
                ))
                .execute();
    }

    public void role() {
        ctx.delete(ROLE).where(ROLE.NAME.notIn(RoleEnum.values())).execute();
    }

    public void studentCourse() {
        ctx.delete(STUDENT_COURSE).execute();
    }

    public void instructorCourse() {
        ctx.delete(INSTRUCTOR_COURSE).execute();
    }

    public void mark() {
        ctx.delete(MARK).execute();
    }

    public void lesson() {
        ctx.delete(LESSON).execute();
    }

    public void homework() {
        ctx.delete(HOMEWORK).execute();
    }

    public void feedback() {
        ctx.delete(FEEDBACK).execute();
    }

    public void courseMark() {
        ctx.delete(COURSE_MARK).execute();
    }

    public void course() {
        ctx.delete(COURSE).execute();
    }


}
