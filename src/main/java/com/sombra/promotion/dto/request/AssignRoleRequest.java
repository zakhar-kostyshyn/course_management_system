package com.sombra.promotion.dto.request;


import com.sombra.promotion.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleRequest {

    @NonNull private UUID userId;
    @NonNull private RoleEnum role;

}
