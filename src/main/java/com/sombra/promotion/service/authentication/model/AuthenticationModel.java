package com.sombra.promotion.service.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class AuthenticationModel {
    @NonNull private final String username;
    @NonNull private final String password;
}
