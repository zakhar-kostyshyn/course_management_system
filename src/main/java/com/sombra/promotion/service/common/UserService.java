package com.sombra.promotion.service.common;

import com.sombra.promotion.service.util.UUIDUtil;
import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.mapper.common.UserMapper;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UUIDUtil uuidUtil;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::build)
                .collect(toList());
    }

    public UserResponse saveUser(String username, String hashedPassword) {
        User user = createUser(username, hashedPassword);
        userRepository.persist(user);
        return userMapper.build(user);
    }

    private User createUser(String username, String hashedPassword) {
        User user = new User();
        user.setId(uuidUtil.randomUUID());
        user.setUsername(username);
        user.setPassword(hashedPassword);
        return user;
    }


}
