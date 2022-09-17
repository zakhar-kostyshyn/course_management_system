package com.sombra.promotion.abstraction.repository;

import com.sombra.promotion.exception.global.NotFoundException;
import com.sombra.promotion.repository.util.DatabaseSelectUtil;
import org.jooq.*;
import org.jooq.exception.NoDataFoundException;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractTableRepository<P extends Serializable, R extends UpdatableRecord<R>> implements TableRepository<P> {

    @Autowired
    private DatabaseSelectUtil databaseSelectUtil;

    @Autowired
    private DSLContext ctx;

    @Override
    public void persist(P pojo) {
        getDao().insert(pojo);
    }

    @Override
    public Optional<P> findById(UUID id) {
        return Optional.ofNullable(getDao().findById(id));
    }

    @Override
    public @NonNull P requiredById(UUID id) {
        String tableName = getDao().getTable().getName();
        return findById(id).orElseThrow(() -> new NotFoundException(id, tableName));
    }

    @Override
    @NonNull
    public List<P> findAll() {
        return getDao().findAll();
    }

    protected @NonNull P requiredByCondition(Condition condition, Table<R> table, Class<P> fetchInto) {
        try {
            return databaseSelectUtil.findOneByCondition(condition, table, ctx, fetchInto);
        } catch (NoDataFoundException e) {
            String tableName = getDao().getTable().getName();
            throw new NotFoundException(tableName);
        }
    }

    protected List<P> findAllByCondition(Condition condition, Table<R> table, Class<P> fetchInto) {
        return databaseSelectUtil.findByCondition(condition, table, ctx, fetchInto);
    }

    protected abstract DAOImpl<R, P, UUID> getDao();

}
