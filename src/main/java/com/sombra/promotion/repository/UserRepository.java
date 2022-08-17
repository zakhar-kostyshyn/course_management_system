package com.sombra.promotion.repository;

import com.sombra.promotion.interfaces.repository.AbstractDaoTableRepository;
import com.sombra.promotion.tables.daos.UserDao;
import com.sombra.promotion.tables.pojos.User;
import com.sombra.promotion.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.sombra.promotion.tables.User.USER;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository extends AbstractDaoTableRepository<User, UserRecord> {

    private final UserDao userDao;

    public User findByUsername(String username) {
        return findOneByCondition(USER.USERNAME.eq(username), User.class);
    }

    @Override
    protected DAOImpl<UserRecord, User, UUID> getDao() {
        return userDao;
    }

}
