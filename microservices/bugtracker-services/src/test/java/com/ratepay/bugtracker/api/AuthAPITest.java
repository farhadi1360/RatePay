package com.ratepay.bugtracker.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratepay.bugtracker.security.AuthServiceImpl;
import com.ratepay.bugtracker.utils.UserPrepareInfo;
import com.ratepay.client.bugtracker.models.LoginRequest;
import com.ratepay.client.bugtracker.models.RegisterRequest;
import com.ratepay.core.dto.ResponseDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthAPITest {
    private static final ResponseDto DONE = new ResponseDto(true);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    AuthServiceImpl authServiceImpl;

    @DisplayName("Test API Mock Register User ")
    @Test
    @Order(1)
    @Commit
    @SneakyThrows
    void registerTest(){
        RegisterRequest request =new RegisterRequest();
        request.setUsername("m.farhadi");
        request.setPassword("123");
        request.setEmail("farhadi.kam@gmail.com");
        Set<String> roles = new HashSet<>();
        roles.add("role_user");
        request.setRoles(roles);
        when(authServiceImpl.register(request)).thenReturn(DONE);
        String json = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
        ;
    }
    @DisplayName("Test API Mock Login User ")
    @Test
    @Order(2)
    @Commit
    @SneakyThrows
    void loginTest(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXRlcGF5MSIsImlhdCI6MTY2MDcyNzE1NywiZXhwIjoxNjYxMzMxOTU3fQ.0cUVJ8Rh7ZKw7ZauI443a9fzUECU1v7edS-GZ-F7i_oEgMRNuehrzOpEWxrutOz_GBJbziQ4o-TdQdjcH5pV5w";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("m.farhadi");
        loginRequest.setPassword("123");
        String json = objectMapper.writeValueAsString(loginRequest);
        when(authServiceImpl.authenticate(loginRequest.getUsername(),loginRequest.getPassword())).thenReturn(token);
        when(authServiceImpl.loadUserByUsername(loginRequest.getUsername())).thenReturn(UserPrepareInfo.getUser());
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
        ;
    }
}
