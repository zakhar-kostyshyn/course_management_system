package com.sombra.promotion.service;

import com.sombra.promotion.dto.response.UserInfoResponse;
import com.sombra.promotion.service.common.UserService;
import com.sombra.promotion.service.common.manyToMany.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRoleService userRoleService;
    private final UserService userService;

    public List<UserInfoResponse> getUserInfo() {
        return userService.getAllUsers().stream()
                .map(userResponse -> UserInfoResponse.builder()
                        .user(userResponse)
                        .roles(userRoleService.getRoleResponsesByUserId(userResponse.getUserId()))
                        .build())
                .collect(Collectors.toList());
    }

}
