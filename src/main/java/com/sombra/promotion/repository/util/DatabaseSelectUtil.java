package com.sombra.promotion.repository.util;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DatabaseSelectUtil {

    public <P, R extends UpdatableRecord<R>> List<P> findByCondition(Condition condition,
                                                                     Table<R> table,
                                                                     DSLContext ctx,
                                                                     Class<P> fetchInto) {
        return ctx.selectFrom(table)
                .where(condition)
                .fetchInto(fetchInto);
    }

    public <P, R extends UpdatableRecord<R>> P findOneByCondition(Condition condition,
                                                                  Table<R> table,
                                                                  DSLContext ctx,
                                                                  Class<P> fetchInto) {
        return ctx.selectFrom(table)
                .where(condition)
                .fetchSingleInto(fetchInto);
    }

}
