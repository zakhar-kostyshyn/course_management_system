package com.sombra.promotion.repository;

import com.sombra.promotion.interfaces.repository.AbstractDaoTableRepository;
import com.sombra.promotion.tables.daos.MarkDao;
import com.sombra.promotion.tables.pojos.Mark;
import com.sombra.promotion.tables.records.MarkRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Mark.MARK;

@Repository
@RequiredArgsConstructor
public class MarkRepository extends AbstractDaoTableRepository<Mark, MarkRecord> {

    private final MarkDao markDao;

    public List<Mark> findByStudentIdAndLessonId(UUID studentId, List<UUID> lessonIds) {
        return findByCondition(MARK.STUDENT_ID.eq(studentId).and(MARK.LESSON_ID.in(lessonIds)), Mark.class);
    }

    @Override
    protected DAOImpl<MarkRecord, Mark, UUID> getDao() {
        return markDao;
    }
}
