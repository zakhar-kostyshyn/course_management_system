package com.sombra.promotion.dto.response;

import com.sombra.promotion.enums.RoleEnum;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponse {

    @NonNull UserResponse user;
    @NonNull RoleResponse role;

}
