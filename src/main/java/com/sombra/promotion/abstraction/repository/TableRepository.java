package com.sombra.promotion.abstraction.repository;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TableRepository<P extends Serializable> {

    void persist(P pojo);

    void update(P pojo);

    Optional<P> findById(UUID id);

    @NonNull
    P requiredById(UUID id);

    @NonNull
    List<P> findAll();

}
