package com.sombra.promotion.service;

import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.UserRoleRepository;
import com.sombra.promotion.tables.pojos.User;
import com.sombra.promotion.utils.Base64Helper;
import com.sombra.promotion.utils.UUIDHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final UUIDHelper uuidHelper;
    private final Base64Helper base64Helper;

    public User register(RegistrationUserRequest request) {
        UUID salt = uuidHelper.randomUUID();
        String userPassword = request.getPassword() + salt;
        String hashedPassword = base64Helper.encodeToString(userPassword);
        UUID createdUserId = userRoleRepository.insertUser(request.getUsername(), hashedPassword, salt, RoleEnum.student);
        User user = userRepository.selectUserById(createdUserId);
        return user;
    }

}
