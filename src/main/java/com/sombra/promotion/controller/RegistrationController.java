package com.sombra.promotion.controller;

import com.sombra.promotion.dto.request.RegistrationUserRequest;
import com.sombra.promotion.dto.response.UserRoleResponse;
import com.sombra.promotion.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping
    public UserRoleResponse register(@RequestBody RegistrationUserRequest request) {
        return userRegistrationService.register(request);
    }

}
