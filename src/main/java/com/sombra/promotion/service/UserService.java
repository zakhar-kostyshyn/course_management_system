package com.sombra.promotion.service;

import com.sombra.promotion.dto.details.UserDetails;
import com.sombra.promotion.factory.UserDetailsFactory;
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
    private final UserDetailsFactory userDetailsFactory;

    public List<UserDetails> getAllUsers() {
        return userRepository.selectAllUsers().stream()
                .map(User::getId)
                .map(userDetailsFactory::build)
                .collect(toList());
    }

    public List<UserDetails> getStudentsByCourseId(UUID courseId) {
        return userDetailsFactory.build(userRepository.selectStudentsByCourseId(courseId));
    }

}
