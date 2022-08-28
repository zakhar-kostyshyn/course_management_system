package com.sombra.promotion.service.generic;

import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.tables.pojos.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByRoleEnum(RoleEnum roleEnum) {
        return roleRepository.requiredByRoleEnum(roleEnum);
    }

}
