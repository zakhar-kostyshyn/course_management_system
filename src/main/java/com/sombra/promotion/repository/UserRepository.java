package com.sombra.promotion.repository;

import com.sombra.promotion.abstraction.repository.AbstractTableRepository;
import com.sombra.promotion.tables.daos.UserDao;
import com.sombra.promotion.tables.pojos.User;
import com.sombra.promotion.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.User.USER;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository extends AbstractTableRepository<User, UserRecord> {

    private final UserDao userDao;

    public User requiredByUsername(String username) {
        return requiredByCondition(USER.USERNAME.eq(username), USER, User.class);
    }

    @Override
    protected DAOImpl<UserRecord, User, UUID> getDao() {
        return userDao;
    }

}
