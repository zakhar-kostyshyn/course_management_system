package com.sombra.promotion.security.service;

import com.sombra.promotion.exception.IncorrectCredentialsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock AuthenticationManager authenticationManager;
    @Mock JwtTokenService jwtTokenService;
    @InjectMocks LoginService loginService;

    @Test
    void must_try_to_authenticate_user() {

        // setup
        String username = "test-username";
        String password = "test-password";
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        // act
        loginService.login(username, password);

        // verify
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(username, password));

    }


    @Test
    void must_throw_specific_exception_when_authentication_failed() {

        // setup
        String username = "test-username";
        String password = "test-password";
        when(authenticationManager.authenticate(any())).thenThrow(RuntimeException.class);

        // act
        // verify
        assertThrows(IncorrectCredentialsException.class, () -> loginService.login(username, password));

    }


    @Test
    void must_throw_exception_when_authentication_is_not_authenticated() {

        // setup
        String username = "test-username";
        String password = "test-password";
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // act
        // verify
        assertThrows(IncorrectCredentialsException.class, () -> loginService.login(username, password));

    }


    @Test
    void must_generate_token_and_return_it() {

        // setup
        String username = "test-username";
        String password = "test-password";
        String token = "test-token";
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtTokenService.generateToken(any())).thenReturn(token);

        // act
        String result = loginService.login(username, password);

        // verify
        verify(jwtTokenService).generateToken(username);
        assertThat(result, is(token));

    }

}