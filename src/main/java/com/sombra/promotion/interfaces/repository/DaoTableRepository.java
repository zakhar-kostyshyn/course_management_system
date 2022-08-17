package com.sombra.promotion.interfaces.repository;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface DaoTableRepository<P extends Serializable> {

    void save(P pojo);

    void update(P pojo);

    P findById(UUID id);

    List<P> findAll();

}
