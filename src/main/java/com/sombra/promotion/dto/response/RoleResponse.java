package com.sombra.promotion.dto.response;

import com.sombra.promotion.enums.RoleEnum;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    @NonNull private UUID id;
    @NonNull private RoleEnum roleEnum;

}
