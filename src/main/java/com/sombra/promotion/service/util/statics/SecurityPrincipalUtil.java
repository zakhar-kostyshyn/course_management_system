package com.sombra.promotion.service.util.statics;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityPrincipalUtil {

    public static UUID authenticatedUserId() {
        return (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
