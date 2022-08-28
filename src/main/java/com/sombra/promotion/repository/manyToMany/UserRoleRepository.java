package com.sombra.promotion.repository.manyToMany;


import com.sombra.promotion.abstraction.repository.ManyToManyTableRepository;
import com.sombra.promotion.tables.pojos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sombra.promotion.tables.Role.ROLE;
import static com.sombra.promotion.tables.User.USER;
import static com.sombra.promotion.tables.UserRole.USER_ROLE;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRoleRepository implements ManyToManyTableRepository<UserRole, User, Role> {

    private final DSLContext ctx;

    @Override
    public void save(UserRole userRole) {
        ctx.insertInto(USER_ROLE, USER_ROLE.ROLE_ID, USER_ROLE.USER_ID)
                .values(userRole.getRoleId(), userRole.getUserId())
                .execute();
    }

    @Override
    public boolean exist(UUID userId, UUID roleId) {
        return ctx.fetchExists(
                ctx.selectFrom(USER_ROLE)
                        .where(USER_ROLE.USER_ID.eq(userId))
                                .and(USER_ROLE.ROLE_ID.eq(roleId)));
    }

    @Override
    public List<Role> findByFirstId(UUID userId) {
        return ctx.select(ROLE.fields())
                .from(ROLE)
                .join(USER_ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .join(USER).on(USER.ID.eq(USER_ROLE.USER_ID))
                .where(USER.ID.eq(userId))
                .fetchInto(Role.class);
    }

    @Override
    public List<User> findBySecondId(UUID roleId) {
        return ctx.select(USER.fields())
                .from(USER)
                .join(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .join(ROLE).on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .where(ROLE.ID.eq(roleId))
                .fetchInto(User.class);
    }
}
