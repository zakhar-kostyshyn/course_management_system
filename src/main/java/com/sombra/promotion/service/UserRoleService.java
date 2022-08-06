package com.sombra.promotion.service;

import com.sombra.promotion.dto.request.AssignRoleRequest;
import com.sombra.promotion.dto.details.UserDetails;
import com.sombra.promotion.factory.UserDetailsFactory;
import com.sombra.promotion.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserDetailsFactory userDetailsFactory;

    public UserDetails assignRole(AssignRoleRequest request) {
        userRoleRepository.setRoleForUser(request.getUsername(), request.getRole());
        return userDetailsFactory.build(request.getUsername());
    }

}
