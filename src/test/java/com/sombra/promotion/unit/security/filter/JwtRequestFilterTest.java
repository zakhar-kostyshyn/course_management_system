package com.sombra.promotion.unit.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sombra.promotion.exception.token.InvalidTokenException;
import com.sombra.promotion.exception.token.TokenExpiredException;
import com.sombra.promotion.security.filter.JwtRequestFilter;
import com.sombra.promotion.security.model.SecurityUser;
import com.sombra.promotion.security.service.JwtTokenService;
import com.sombra.promotion.security.service.SecurityUserDetailsService;
import com.sombra.promotion.service.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {


    @Test
    void must_skip_handling_when_auth_header_is_null() throws ServletException, IOException {
        authorizationHeader = null;
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtTokenService, never()).decode(anyString());
    }


    @Test
    void must_skip_handling_when_auth_header_not_start_with_bearer() throws ServletException, IOException {
        authorizationHeader = "test-token";
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtTokenService, never()).decode(anyString());
    }


    @Test
    void must_skip_handling_when_security_context_already_has_authentication() throws ServletException, IOException {

        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(securityContext.getAuthentication()).thenReturn(mock(Authentication.class));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtTokenService, never()).decode(anyString());
    }


    @Test
    void must_decode_token() {
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(jwtTokenService.decode(anyString())).thenReturn(decodedToken);
        when(decodedToken.getSubject()).thenReturn(subject);
        when(decodedToken.getExpiresAtAsInstant()).thenReturn(tokenExpire);
        when(dateUtil.nowInstant()).thenReturn(nowInstant);
        when(securityUserDetailsService.loadUserByUsername(anyString())).thenReturn(securityUser);
        when(securityUser.getId()).thenReturn(securityID);
        doReturn(grantedAuthorities).when(securityUser).getAuthorities();

        filter.doFilterInternal(request, response, filterChain);

        verify(jwtTokenService).decode(token);
    }


    @Test
    void must_throw_exception_when_time_of_token_expiration_came() {
        tokenExpire = Instant.now().minus(1, ChronoUnit.DAYS);
        nowInstant = Instant.now();
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(jwtTokenService.decode(anyString())).thenReturn(decodedToken);
        when(decodedToken.getExpiresAtAsInstant()).thenReturn(tokenExpire);
        when(dateUtil.nowInstant()).thenReturn(nowInstant);

        assertThrows(TokenExpiredException.class, () -> filter.doFilterInternal(request, response, filterChain));
    }


    @Test
    void must_throw_exception_when_subject_from_token_is_null() {
        subject = null;
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(jwtTokenService.decode(anyString())).thenReturn(decodedToken);
        when(decodedToken.getExpiresAtAsInstant()).thenReturn(tokenExpire);
        when(dateUtil.nowInstant()).thenReturn(nowInstant);
        when(decodedToken.getSubject()).thenReturn(subject);

        assertThrows(InvalidTokenException.class, () -> filter.doFilterInternal(request, response, filterChain));
    }


    @Test
    void must_throw_exception_when_subject_from_token_is_blank() {
        subject = "   ";
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(jwtTokenService.decode(anyString())).thenReturn(decodedToken);
        when(decodedToken.getExpiresAtAsInstant()).thenReturn(tokenExpire);
        when(dateUtil.nowInstant()).thenReturn(nowInstant);
        when(decodedToken.getSubject()).thenReturn(subject);

        assertThrows(InvalidTokenException.class, () -> filter.doFilterInternal(request, response, filterChain));
    }


    @Test
    void must_load_user_by_username() {
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(jwtTokenService.decode(anyString())).thenReturn(decodedToken);
        when(decodedToken.getExpiresAtAsInstant()).thenReturn(tokenExpire);
        when(dateUtil.nowInstant()).thenReturn(nowInstant);
        when(decodedToken.getSubject()).thenReturn(subject);
        when(securityUserDetailsService.loadUserByUsername(anyString())).thenReturn(securityUser);
        when(securityUser.getId()).thenReturn(securityID);

        filter.doFilterInternal(request, response, filterChain);

        verify(securityUserDetailsService).loadUserByUsername(subject);
    }


    @Test
    void must_catch_and_rethrow_specific_exception_when_cannot_get_user_from_DB() {
        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(jwtTokenService.decode(anyString())).thenReturn(decodedToken);
        when(decodedToken.getExpiresAtAsInstant()).thenReturn(tokenExpire);
        when(dateUtil.nowInstant()).thenReturn(nowInstant);
        when(decodedToken.getSubject()).thenReturn(subject);
        when(securityUserDetailsService.loadUserByUsername(anyString())).thenThrow(RuntimeException.class);

        assertThrows(InvalidTokenException.class, () -> filter.doFilterInternal(request, response, filterChain));
    }


    @Test
    void must_create_username_password_authentication_token_store_it_in_security_context_and_continue_filter() throws ServletException, IOException {

        when(request.getHeader(anyString())).thenReturn(authorizationHeader);
        when(jwtTokenService.decode(anyString())).thenReturn(decodedToken);
        when(decodedToken.getExpiresAtAsInstant()).thenReturn(tokenExpire);
        when(dateUtil.nowInstant()).thenReturn(nowInstant);
        when(decodedToken.getSubject()).thenReturn(subject);
        when(securityUserDetailsService.loadUserByUsername(anyString())).thenReturn(securityUser);
        when(securityUser.getId()).thenReturn(securityID);
        doReturn(grantedAuthorities).when(securityUser).getAuthorities();

        filter.doFilterInternal(request, response, filterChain);

        verify(securityUser).getId();
        verify(securityUser).getAuthorities();
        verify(securityContext).setAuthentication(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
        assertThat(usernamePasswordAuthenticationTokenArgumentCaptor.getValue().getDetails(), is(new WebAuthenticationDetailsSource().buildDetails(request)));
        assertThat(usernamePasswordAuthenticationTokenArgumentCaptor.getValue().getAuthorities().size(), is(1));
        assertThat(usernamePasswordAuthenticationTokenArgumentCaptor.getValue().getAuthorities().contains(grantedAuthority), is(true));
        assertThat(usernamePasswordAuthenticationTokenArgumentCaptor.getValue().getPrincipal(), sameInstance(securityID));
        verify(filterChain).doFilter(request, response);

    }


    @Mock SecurityUserDetailsService securityUserDetailsService;
    @Mock JwtTokenService jwtTokenService;
    @Mock DateUtil dateUtil;
    @InjectMocks
    JwtRequestFilter filter;
    @Captor ArgumentCaptor<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenArgumentCaptor;


    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain filterChain;
    SecurityContext securityContext;
    DecodedJWT decodedToken;
    Authentication authentication;
    SecurityUser securityUser;
    GrantedAuthority grantedAuthority;
    List<? extends GrantedAuthority> grantedAuthorities;
    Instant tokenExpire;
    Instant nowInstant;
    String authorizationHeader;
    String token;
    String subject;
    UUID securityID;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        securityContext = mock(SecurityContext.class);
        decodedToken = mock(DecodedJWT.class);
        authentication = null;
        securityUser = mock(SecurityUser.class);
        grantedAuthority = mock(GrantedAuthority.class);
        tokenExpire = Instant.now().plus(1, ChronoUnit.DAYS);
        nowInstant = Instant.now();
        authorizationHeader = "Bearer test-token";
        token = "test-token";
        subject = "test-subject";
        securityID = UUID.randomUUID();
        grantedAuthorities = List.of(grantedAuthority);
        SecurityContextHolder.setContext(securityContext);
    }

}
