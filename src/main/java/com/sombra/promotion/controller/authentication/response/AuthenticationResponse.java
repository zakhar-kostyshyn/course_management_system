package com.sombra.promotion.controller.authentication.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class AuthenticationResponse {

    @NonNull private final String token;

}
