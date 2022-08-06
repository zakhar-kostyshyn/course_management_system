package com.sombra.promotion.controller;

import com.sombra.promotion.dto.request.LoginRequest;
import com.sombra.promotion.security.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public String login(@RequestBody LoginRequest request) {
        return loginService.login(request.getUsername(), request.getPassword());
    }

}
