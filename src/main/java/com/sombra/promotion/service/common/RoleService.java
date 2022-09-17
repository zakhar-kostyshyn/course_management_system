package com.sombra.promotion.service.common;

import com.sombra.promotion.dto.response.RoleResponse;
import com.sombra.promotion.enums.RoleEnum;
import com.sombra.promotion.mapper.common.RoleMapper;
import com.sombra.promotion.repository.RoleRepository;
import com.sombra.promotion.tables.pojos.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role getRoleByRoleEnum(RoleEnum roleEnum) {
        return roleRepository.requiredByRoleEnum(roleEnum);
    }

    public RoleResponse getRoleById(UUID id) {
        return roleMapper.build(roleRepository.requiredById(id));
    }

}
