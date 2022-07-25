package com.sombra.promotion.service;

import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.UserRoleRepository;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegistrationUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UUID createdUserId = userRoleRepository.insertUser(request.getUsername(), hashedPassword, RoleEnum.student);
        return userRepository.selectUserById(createdUserId);
    }

}
