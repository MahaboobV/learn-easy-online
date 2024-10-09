package com.example.leo.controller;

import com.example.leo.model.UserRegistrationDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userServiceMock;

    @Autowired
    ObjectMapper objectMapper;

    private UserRegistrationDTO user;

    @BeforeEach
    public void setUp() {
        // Arrange
        user = new UserRegistrationDTO();
        user.setId(12345L);
        user.setFullName("John Willy");
        user.setEmail("john.willy@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("1234567890");
        user.setAge(25);
    }

    @Test
    public void userRegisterTest() throws Exception{

        when(userServiceMock.registerUser(any(UserRegistrationDTO.class))).thenReturn(user.getId());

        // Act
        ResultActions response = mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));


        // Assert
        response.andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully with ID :"+user.getId()));


        verify(userServiceMock, times(1)).registerUser(any(UserRegistrationDTO.class));
    }
}
