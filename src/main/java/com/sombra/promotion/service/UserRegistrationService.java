package com.sombra.promotion.service;

import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final DomainRepository domainRepository;

    public void register(RegistrationUserRequest request) {

        UUID salt = UUID.randomUUID();
        String hashedPassword = Base64
                .getEncoder()
                .encodeToString((request.getPassword() + salt).getBytes());

        domainRepository.insertUser(
                request.getUsername(),
                hashedPassword,
                salt,
                RoleEnum.student
        );
    }

}
