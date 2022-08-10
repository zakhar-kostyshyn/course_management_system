package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.AssignRoleRequest;
import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.factory.UserFactory;
import com.sombra.promotion.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserFactory userFactory;

    public UserResponse assignRole(AssignRoleRequest request) {
        userRoleRepository.setRoleForUser(request.getUsername(), request.getRole());
        return userFactory.build(request.getUsername());
    }

}
