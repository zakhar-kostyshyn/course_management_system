package com.sombra.promotion.service.specific;

import com.sombra.promotion.dto.request.RegistrationUserRequest;
import com.sombra.promotion.dto.response.UserResponse;
import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.factory.specific.UserRoleFactory;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.service.generic.UserService;
import com.sombra.promotion.service.generic.manyToMany.UserRoleService;
import com.sombra.promotion.tables.pojos.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleRepository roleRepository;
    private final UserRoleFactory userRoleFactory;

    @Transactional
    public UserRoleResponse register(RegistrationUserRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UserResponse userResponse = userService.saveUser(request.getUsername(), hashedPassword);
        Role studentRole = roleRepository.requiredByRoleEnum(RoleEnum.student);
        userRoleService.saveUserRole(userResponse.getUserId(), studentRole.getId());
        return userRoleFactory.build(userResponse.getUserId(), studentRole.getId());
    }

}
