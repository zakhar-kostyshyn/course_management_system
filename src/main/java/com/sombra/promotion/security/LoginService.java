package com.sombra.promotion.security;

import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public String login(String username, String password) {

        User user = userRepository.selectUserByUsername(username);
        Authentication authenticate = authenticationManager.authenticate(new JwtUsernamePasswordAuthenticationToken(user, password));
        if (!authenticate.isAuthenticated())
            throw new RuntimeException("401");

        log.info("Logged In!!!");

        return jwtTokenService.generateToken(user.getUsername());
    }

}
