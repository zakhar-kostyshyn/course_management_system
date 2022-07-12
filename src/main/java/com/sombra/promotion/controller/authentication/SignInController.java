package com.sombra.promotion.controller.authentication;

import com.sombra.promotion.controller.authentication.request.SignInRequest;
import com.sombra.promotion.security.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signIn")
@RequiredArgsConstructor
public class SignInController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<String> usernamePasswordSignIn(
            @RequestBody SignInRequest request
    ) {
        return ResponseEntity.ok(loginService.login(request.getUsername(), request.getPassword()));
    }

}
