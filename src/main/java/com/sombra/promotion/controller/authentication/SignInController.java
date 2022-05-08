package com.sombra.promotion.controller.authentication;

import com.sombra.promotion.controller.authentication.request.SignInRequest;
import com.sombra.promotion.controller.authentication.response.AuthenticationResponse;
import com.sombra.promotion.service.authentication.AuthenticationService;
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

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> usernamePasswordSignIn(
            @RequestBody SignInRequest request
    ) {

    }

}
