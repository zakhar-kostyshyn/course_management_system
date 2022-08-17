package com.sombra.promotion.dto.response;

import com.sombra.promotion.enums.RoleEnum;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @NonNull private UUID userId;
    @NonNull private String username;

}
