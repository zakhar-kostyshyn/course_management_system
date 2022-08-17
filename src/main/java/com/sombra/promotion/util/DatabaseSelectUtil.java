package com.sombra.promotion.util;

import org.jooq.Condition;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DatabaseSelectUtil {

    public <P, R extends UpdatableRecord<R>> List<P> findByCondition(Condition condition,
                                                                     DAOImpl<R, P, UUID> dao,
                                                                     Class<P> fetchInto) {
        return dao.ctx().selectFrom(dao.getTable())
                .where(condition)
                .fetchInto(fetchInto);
    }

    public <P, R extends UpdatableRecord<R>> P findOneByCondition(Condition condition,
                                                                  DAOImpl<R, P, UUID> dao,
                                                                  Class<P> fetchInto) {
        return dao.ctx().selectFrom(dao.getTable())
                .where(condition)
                .fetchSingleInto(fetchInto);
    }

}
