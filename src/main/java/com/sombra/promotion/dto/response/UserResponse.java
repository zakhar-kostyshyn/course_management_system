package com.sombra.promotion.dto.response;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @NonNull private UUID userId;
    @NonNull private String username;

}
