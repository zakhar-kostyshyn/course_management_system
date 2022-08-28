package com.sombra.promotion.repository;


import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.abstraction.repository.AbstractTableRepository;
import com.sombra.promotion.tables.daos.RoleDao;
import com.sombra.promotion.tables.pojos.Role;
import com.sombra.promotion.tables.records.RoleRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Role.ROLE;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class RoleRepository extends AbstractTableRepository<Role, RoleRecord> {

    private final RoleDao roleDao;

    public Role requiredByRoleEnum(RoleEnum roleEnum) {
        return requiredByCondition(ROLE.NAME.eq(roleEnum), ROLE, Role.class);
    }

    @Override
    protected DAOImpl<RoleRecord, Role, UUID> getDao() {
        return roleDao;
    }

}
