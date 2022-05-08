package com.sombra.promotion.controller.admin.request;


import lombok.Data;
import lombok.NonNull;

@Data
public class AssignRoleByAdminRequest {

    @NonNull private final String role;
    @NonNull private final String username;

}
