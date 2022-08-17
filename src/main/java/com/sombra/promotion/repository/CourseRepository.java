package com.sombra.promotion.repository;

import com.sombra.promotion.interfaces.repository.AbstractDaoTableRepository;
import com.sombra.promotion.tables.daos.CourseDao;
import com.sombra.promotion.tables.pojos.Course;
import com.sombra.promotion.tables.records.CourseRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Course.COURSE;

@Repository
@RequiredArgsConstructor
public class CourseRepository extends AbstractDaoTableRepository<Course, CourseRecord> {

    private final CourseDao courseDao;

    public Course findByCourseName(String courseName) {
        return findOneByCondition(COURSE.NAME.eq(courseName), Course.class);
    }

    @Override
    protected DAOImpl<CourseRecord, Course, UUID> getDao() {
        return courseDao;
    }
}
