package com.sombra.promotion.dto.details;

import com.sombra.promotion.enums.RoleEnum;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Builder
public class UserDetails {

    @NonNull private UUID userId;
    @NonNull private String username;
    @NonNull private List<RoleEnum> roles;

}
