package com.sombra.promotion.repository;

import com.sombra.promotion.interfaces.repository.AbstractDaoTableRepository;
import com.sombra.promotion.tables.daos.LessonDao;
import com.sombra.promotion.tables.pojos.Lesson;
import com.sombra.promotion.tables.records.LessonRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Lesson.LESSON;

@Repository
@RequiredArgsConstructor
public class LessonRepository extends AbstractDaoTableRepository<Lesson, LessonRecord> {

    private final LessonDao lessonDao;

    public List<Lesson> findByCourseId(UUID courseId) {
        return findByCondition(LESSON.COURSE_ID.eq(courseId), Lesson.class);
    }

    @Override
    protected DAOImpl<LessonRecord, Lesson, UUID> getDao() {
        return lessonDao;
    }
}
