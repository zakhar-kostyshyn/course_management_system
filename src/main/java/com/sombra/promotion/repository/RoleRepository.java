package com.sombra.promotion.repository;


import com.sombra.promotion.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.Role.ROLE;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
public class RoleRepository {

    private final DSLContext ctx;

    public UUID selectRoleIDByRoleType(RoleEnum roleEnum) {
        return requireNonNull(ctx.select(ROLE.ID)
                .from(ROLE)
                .where(ROLE.NAME.eq(roleEnum))
                .fetchOne()
        ).component1();
    }

}
