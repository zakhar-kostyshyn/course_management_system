package com.sombra.promotion.controller.admin.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class AssignRoleResponse {

    @NonNull private final Integer id;
    @NonNull private final String role;
    @NonNull private final String username;

}
