package com.sombra.promotion.abstraction.repository;

import java.io.Serializable;

public interface Persistence<P extends Serializable> {

    void persist(P pojo);

}
