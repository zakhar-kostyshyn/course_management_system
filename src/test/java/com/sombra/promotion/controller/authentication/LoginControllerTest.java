package com.sombra.promotion.controller.authentication;

import com.sombra.promotion.controller.authentication.request.LoginRequest;
import com.sombra.promotion.repository.UserRepository;
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

import java.util.Base64;
import java.util.UUID;

import static com.sombra.promotion.Constants.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import({JacksonTestConfiguration.class, TestHelpersConfiguration.class})
class LoginControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JsonBuilderUtils jsonBuilderUtils;

    @Test
    void must_login_and_generate_token() throws Exception {

        LoginRequest request = new LoginRequest(TEST_USERNAME, TEST_PASSWORD);
        UUID salt = UUID.fromString("e4047f80-6e5e-4094-a493-42be90a1d85d");
        String hashedPassword = Base64.getEncoder().encodeToString((TEST_PASSWORD + salt).getBytes());
        User user = new User(TEST_UUID, TEST_USERNAME, hashedPassword, salt);
        when(userRepository.selectUserByUsername(any())).thenReturn(user);

        mvc.perform(post("/login")
                .contentType(APPLICATION_JSON)
                .content(jsonBuilderUtils.asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));

    }

}
