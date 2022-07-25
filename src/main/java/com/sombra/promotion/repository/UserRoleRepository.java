package com.sombra.promotion.repository;


import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.tables.pojos.Role;
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
public class UserRoleRepository {

    private final DSLContext ctx;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public void setRoleForUser(String username, RoleEnum roleEnum) {

        UUID userId = userRepository.selectUserIdByUsername(username);
        UUID roleId = roleRepository.selectRoleIDByRoleType(roleEnum);

        if (ctx.fetchExists(
                ctx.select()
                        .from(USER_ROLE)
                        .where(USER_ROLE.USER_ID.eq(userId)
                                .and(USER_ROLE.ROLE_ID.eq(roleId)))
        )
        ) {
            log.warn(String.format("User %s already granted by role: %s. No action done.", username, roleEnum));
        } else {
            ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID, USER_ROLE.PREDEFINED)
                    .values(userId, roleId, false)
                    .execute();
        }
    }

    public UUID insertUser(
            String username,
            String hashedPassword,
            RoleEnum roleEnum
    ) {
        UUID createdUserID =
                requireNonNull(
                        ctx.insertInto(USER, USER.USERNAME, USER.PASSWORD)
                                .values(username, hashedPassword)
                                .returningResult(USER.ID)
                                .fetchOne()
                ).value1();

        UUID idByRoleType = roleRepository.selectRoleIDByRoleType(roleEnum);
        ctx.insertInto(USER_ROLE, USER_ROLE.USER_ID, USER_ROLE.ROLE_ID)
                .values(createdUserID, idByRoleType)
                .execute();

        return createdUserID;
    }

    public List<Role> selectRolesByUsername(String username) {
        return ctx.select()
                .from(USER)
                .join(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .join(ROLE).on(USER_ROLE.ROLE_ID.eq(ROLE.ID))
                .fetchInto(Role.class);
    }

}
