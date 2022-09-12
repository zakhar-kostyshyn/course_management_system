package com.sombra.promotion.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sombra.promotion.exception.generic.NotFoundException;
import com.sombra.promotion.exception.token.InvalidTokenException;
import com.sombra.promotion.exception.token.TokenExpiredException;
import com.sombra.promotion.security.model.SecurityUser;
import com.sombra.promotion.security.service.JwtTokenService;
import com.sombra.promotion.security.service.SecurityUserDetailsService;
import com.sombra.promotion.service.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String BEARER_TOKEN_START = "Bearer ";
    private final static int NUMBER_OF_LETTERS_BEFORE_TOKEN_PAYLOAD = 7;


    private final SecurityUserDetailsService securityUserDetailsService;
    private final JwtTokenService jwtTokenService;
    private final DateUtil dateUtil;

    @Override
    @SneakyThrows
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!authorizationHeader.startsWith(BEARER_TOKEN_START)) {
            log.warn("Authorization header doesn't contain Bearer token: " + authorizationHeader);
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            log.warn("Security context already fulfilled");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(NUMBER_OF_LETTERS_BEFORE_TOKEN_PAYLOAD);
        DecodedJWT decodedToken = jwtTokenService.decode(token);
        if (decodedToken.getExpiresAtAsInstant().isBefore(dateUtil.nowInstant()))
            throw new TokenExpiredException();

        String username = decodedToken.getSubject();
        if (username == null || username.isBlank())
            throw new InvalidTokenException("Username from token is null or blank");

        SecurityUser securityUser;
        try {
            securityUser = securityUserDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            throw new InvalidTokenException("Cannot get user from DB by token subject", e);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                securityUser.getId(),
                null,
                securityUser.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

}
