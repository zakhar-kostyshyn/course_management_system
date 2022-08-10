package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.factory.UserFactory;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public List<UserResponse> getAllUsers() {
        return userRepository.selectAllUsers().stream()
                .map(User::getId)
                .map(userFactory::build)
                .collect(toList());
    }

    public List<UserResponse> getStudentsByCourseId(UUID courseId) {
        return userFactory.build(userRepository.selectStudentsByCourseId(courseId));
    }

}
