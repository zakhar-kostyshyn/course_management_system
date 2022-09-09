package com.sombra.promotion.repository;

import com.sombra.promotion.abstraction.repository.AbstractTableRepository;
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
public class CourseRepository extends AbstractTableRepository<Course, CourseRecord> {

    private final CourseDao courseDao;

    @Override
    protected DAOImpl<CourseRecord, Course, UUID> getDao() {
        return courseDao;
    }
}
