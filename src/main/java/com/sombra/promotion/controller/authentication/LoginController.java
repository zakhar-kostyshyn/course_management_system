package com.sombra.promotion.controller.authentication;

import com.sombra.promotion.controller.authentication.request.LoginRequest;
import com.sombra.promotion.security.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        String token = loginService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(token);
    }

}
