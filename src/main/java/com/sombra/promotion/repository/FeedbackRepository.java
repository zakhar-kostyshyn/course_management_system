package com.sombra.promotion.repository;

import com.sombra.promotion.interfaces.repository.AbstractDaoTableRepository;
import com.sombra.promotion.tables.daos.FeedbackDao;
import com.sombra.promotion.tables.pojos.Feedback;
import com.sombra.promotion.tables.records.FeedbackRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FeedbackRepository extends AbstractDaoTableRepository<Feedback, FeedbackRecord> {

    private final FeedbackDao feedbackDao;

    @Override
    protected DAOImpl<FeedbackRecord, Feedback, UUID> getDao() {
        return feedbackDao;
    }

}
