package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.RegistrationUserRequest;
import com.sombra.promotion.dto.details.UserDetails;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.factory.UserDetailsFactory;
import com.sombra.promotion.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsFactory userDetailsFactory;

    public UserDetails register(RegistrationUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UUID createdUserId = userRoleRepository.insertUser(request.getUsername(), hashedPassword, RoleEnum.student);
        return userDetailsFactory.build(createdUserId);
    }

}
