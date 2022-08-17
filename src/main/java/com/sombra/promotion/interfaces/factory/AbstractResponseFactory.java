package com.sombra.promotion.interfaces.factory;

import com.sombra.promotion.interfaces.repository.DaoTableRepository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public abstract class AbstractResponseFactory<P extends Serializable, R, D extends DaoTableRepository<P>>
        implements ResponseFactory<P, R> {

    public abstract D getDao();

    @Override
    public R build(UUID id) {
        return build(getDao().findById(id));
    }

    @Override
    public List<R> build(List<P> pojos) {
        return pojos.stream().map(this::build).collect(toList());
    }

    @Override
    public List<R> buildByIds(List<UUID> ids) {
        return ids.stream().map(this::build).collect(toList());
    }

}
