package com.sombra.promotion.controller.authentication;

import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.controller.authentication.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    @PostMapping
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationUserRequest request
    ) {

    }

}
