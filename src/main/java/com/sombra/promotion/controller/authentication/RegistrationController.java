package com.sombra.promotion.controller.authentication;

import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.service.UserRegistrationService;
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

    private final UserRegistrationService userRegistrationService;

    @PostMapping
    public ResponseEntity<String> register(
            @RequestBody RegistrationUserRequest request
    ) {
        userRegistrationService.register(request);
        return ResponseEntity.ok("Created");
    }

}
