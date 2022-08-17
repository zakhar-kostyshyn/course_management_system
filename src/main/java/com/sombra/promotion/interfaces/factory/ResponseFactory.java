package com.sombra.promotion.interfaces.factory;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface ResponseFactory<P extends Serializable, R> {

    R build(P model);

    List<R> build(List<P> models);

    List<R> buildByIds(List<UUID> modelIds);

    R build(UUID id);

}
