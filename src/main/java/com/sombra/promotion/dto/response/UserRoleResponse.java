package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {

    @NonNull UserResponse user;
    @NonNull RoleResponse role;

}
