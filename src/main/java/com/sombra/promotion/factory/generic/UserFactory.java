package com.sombra.promotion.factory.generic;

import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.abstraction.factory.AbstractResponseFactory;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory extends AbstractResponseFactory<User, UserResponse, UserRepository> {

    private final UserRepository userRepository;

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Override
    public UserResponse build(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

}
