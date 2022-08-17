package com.sombra.promotion.interfaces.repository;

import com.sombra.promotion.util.DatabaseSelectUtil;
import org.jooq.*;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public abstract class AbstractDaoTableRepository<P extends Serializable, R extends UpdatableRecord<R>> implements DaoTableRepository<P> {

    @Autowired
    private DatabaseSelectUtil databaseSelectUtil;

    @Override
    public void save(P pojo) {
        getDao().insert(pojo);
    }

    @Override
    public void update(P pojo) {
        getDao().update(pojo);
    }

    @Override
    public P findById(UUID id) {
        return getDao().findById(id);
    }

    @Override
    public List<P> findAll() {
        return getDao().findAll();
    }

    protected P findOneByCondition(Condition condition, Class<P> fetchInto) {
        return databaseSelectUtil.findOneByCondition(condition, getDao(), fetchInto);
    }

    protected List<P> findByCondition(Condition condition, Class<P> fetchInto) {
        return databaseSelectUtil.findByCondition(condition, getDao(), fetchInto);
    }

    protected abstract DAOImpl<R, P, UUID> getDao();

}
