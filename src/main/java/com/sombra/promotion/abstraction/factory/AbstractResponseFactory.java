package com.sombra.promotion.abstraction.factory;

import com.sombra.promotion.abstraction.repository.TableRepository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public abstract class AbstractResponseFactory<P extends Serializable, R, TR extends TableRepository<P>>
        implements ResponseFactory<P, R> {

    public abstract TR getRepository();

    @Override
    public R build(UUID id) {
        return build(getRepository().requiredById(id));
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
