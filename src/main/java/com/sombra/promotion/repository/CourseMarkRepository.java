package com.sombra.promotion.repository;


import com.sombra.promotion.interfaces.repository.AbstractDaoTableRepository;
import com.sombra.promotion.tables.daos.CourseMarkDao;
import com.sombra.promotion.tables.pojos.CourseMark;
import com.sombra.promotion.tables.records.CourseMarkRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CourseMarkRepository extends AbstractDaoTableRepository<CourseMark, CourseMarkRecord> {

    private final CourseMarkDao courseMarkDao;

    @Override
    protected DAOImpl<CourseMarkRecord, CourseMark, UUID> getDao() {
        return courseMarkDao;
    }


}
