package com.sombra.promotion.repository;

import com.sombra.promotion.abstraction.repository.AbstractTableRepository;
import com.sombra.promotion.tables.daos.HomeworkDao;
import com.sombra.promotion.tables.pojos.Homework;
import com.sombra.promotion.tables.records.HomeworkRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HomeworkRepository extends AbstractTableRepository<Homework, HomeworkRecord> {

    private final HomeworkDao homeworkDao;

    @Override
    protected DAOImpl<HomeworkRecord, Homework, UUID> getDao() {
        return homeworkDao;
    }


}
