package com.sombra.promotion.controller.admin.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class AssignInstructorResponse {

    @NonNull private final Integer id;
    @NonNull private final String role;
    @NonNull private final String username;


}
