package com.example.leo.controller;

import com.example.leo.model.UserRegistrationDto;
import com.example.leo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    private UserRegistrationDto user;

    @BeforeEach
    public void setUp() {
        // Arrange
        user = new UserRegistrationDto();
        user.setId(12345L);
        user.setFullName("John Willy");
        user.setEmail("john.willy@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("1234567890");
        user.setAge(25);
    }

    @Test
    public void userRegisterTest() throws Exception{

        when(userServiceMock.registerUser(any(UserRegistrationDto.class))).thenReturn(user.getId());

        // Act
        ResultActions response = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));


        // Assert
        response.andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully with ID :"+user.getId()));


        verify(userServiceMock, times(1)).registerUser(any(UserRegistrationDto.class));
    }

    @Test
    public void userRegisterTest_MissingUserFullName() throws Exception{
        user.setFullName("");

        // Act
        ResultActions response = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));


        // Assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message[0]").value("fullName: Full name is required"));

    }

    @Test
    public void userRegisterTest_MissingEmail() throws Exception{
        // Arrange
        user.setEmail("");

        // Act
        ResultActions response = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // Assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message[0]").value("email: Email is required"));

    }

    @Test
    public void userRegisterTest_MissingPhone() throws Exception{
        // Arrange
        user.setPhoneNumber("");

        // Act
        ResultActions response = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // Assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message[0]").value("phoneNumber: Phone number is required"));

    }

    @Test
    public void userRegisterTest_MissingPassword() throws Exception{
        // Arrange
        user.setPassword("");

        // Act
        ResultActions response = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // Assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message[0]").value("password: Password is required"));

    }

    @Test
    public void userRegisterTest_AgeLimitViolates() throws Exception{
        // Arrange
        user.setAge(14);

        // Act
        ResultActions response = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // Assert
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message[0]").value("age: Age must be at least 18"));

    }
}
