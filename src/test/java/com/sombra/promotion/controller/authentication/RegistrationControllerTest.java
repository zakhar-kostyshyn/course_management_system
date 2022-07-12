package com.sombra.promotion.controller.authentication;

import com.sombra.promotion.controller.authentication.request.RegistrationUserRequest;
import com.sombra.promotion.repository.UserRepository;
import com.sombra.promotion.repository.UserRoleRepository;
import com.sombra.promotion.tables.pojos.User;
import com.sombra.promotion.testConfigs.JacksonTestConfiguration;
import com.sombra.promotion.testConfigs.TestHelpersConfiguration;
import com.sombra.promotion.testHelpers.JsonBuilderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.sombra.promotion.Constants.*;
import static com.sombra.promotion.enums.RoleEnum.student;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({JacksonTestConfiguration.class, TestHelpersConfiguration.class})
class RegistrationControllerTest {

    @MockBean
    private UserRoleRepository userRoleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JsonBuilderUtils jsonBuilderUtils;

    @Test
    void must_return_200_and_user() throws Exception {
        var user = new User(TEST_UUID, TEST_USERNAME, TEST_PASSWORD, TEST_SALT);
        when(userRoleRepository.insertUser(anyString(), anyString(), any(), eq(student)))
                .thenReturn(TEST_UUID);
        when(userRepository.selectUserById(any())).thenReturn(user);
        var request = new RegistrationUserRequest(TEST_USERNAME, TEST_PASSWORD);

        mvc.perform(post("/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonBuilderUtils.asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.password", is(TEST_PASSWORD)))
                .andExpect(jsonPath("$.salt", notNullValue()));
    }

}