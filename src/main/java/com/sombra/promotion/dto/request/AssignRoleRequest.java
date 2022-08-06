package com.sombra.promotion.dto.request;


import com.sombra.promotion.enums.RoleEnum;
import lombok.Data;
import lombok.NonNull;

@Data
public class AssignRoleRequest {

    @NonNull private final RoleEnum role;
    @NonNull private final String username;

}
