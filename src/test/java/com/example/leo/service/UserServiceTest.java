package com.example.leo.service;

import com.example.leo.entity.User;
import com.example.leo.mapper.UserMapper;
import com.example.leo.model.UserRegistrationDto;
import com.example.leo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User user;

    private UserRegistrationDto userRegistrationDto;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserMapper userMapperMock;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setId(12345L);
        user.setFullName("John Willy");
        user.setEmail("john.willy@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("1234567890");
        user.setAge(25);

        userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setId(12345L);
        userRegistrationDto.setFullName("John Willy");
        userRegistrationDto.setEmail("john.willy@example.com");
        userRegistrationDto.setPassword("password123");
        userRegistrationDto.setPhoneNumber("1234567890");
        userRegistrationDto.setAge(25);

    }

    @Test
    public void testRegisterUser() {

        when(userMapperMock.toEntity(any(UserRegistrationDto.class))).thenReturn(user);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        //Act
        Long response = userService.registerUser(userRegistrationDto);


        // Assert
        assertEquals(response , user.getId());

        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(userMapperMock, times(1)).toEntity(any(UserRegistrationDto.class));
    }
}
