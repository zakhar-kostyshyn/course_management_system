package com.sombra.promotion.interfaces.repository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface ManyToManyTableRepository<P extends Serializable, P1 extends Serializable, P2 extends Serializable> {

    void save(P pojo);

    boolean exist(UUID firstId, UUID secondId);

    List<P2> findByFirstId(UUID id);

    List<P1> findBySecondId(UUID id);

}
