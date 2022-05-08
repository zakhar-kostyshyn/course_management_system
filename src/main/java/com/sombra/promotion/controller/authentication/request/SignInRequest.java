package com.sombra.promotion.controller.authentication.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class SignInRequest {
    @NonNull private final String username;
    @NonNull private final String password;
}
