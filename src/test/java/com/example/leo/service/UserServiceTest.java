package com.example.leo.service;

import com.example.leo.entity.User;
import com.example.leo.exception.UserAlreadyExistException;
import com.example.leo.mapper.UserMapper;
import com.example.leo.model.ApiErrorResponse;
import com.example.leo.model.UserRegistrationDto;
import com.example.leo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        verify(userRepositoryMock, times(1)).findByEmail(any(String.class));
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(userMapperMock, times(1)).toEntity(any(UserRegistrationDto.class));
    }


    @Test
    public void testRegisterUser_InvalidEmail() {
        userRegistrationDto.setEmail("1234.com");

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> {
            userService.registerUser(userRegistrationDto);
        });

        // Assert
        assertEquals("Invalid email address" , exception.getMessage());

        verify(userRepositoryMock, never()).findByEmail(any(String.class));
        verify(userRepositoryMock, never()).save(any(User.class));
        verify(userMapperMock, never()).toEntity(any(UserRegistrationDto.class));
    }

    @Test
    public void testRegisterUser_InvalidPassword() {
        userRegistrationDto.setPassword("1234");

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> {
            userService.registerUser(userRegistrationDto);
        });

        // Assert
        assertEquals("Password must be at least 6 characters long" , exception.getMessage());

        verify(userRepositoryMock, never()).findByEmail(any(String.class));
        verify(userRepositoryMock, never()).save(any(User.class));
        verify(userMapperMock, never()).toEntity(any(UserRegistrationDto.class));
    }

    @Test
    public void testRegisterUser_UserAlreadyExist() {
        when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(user));

        //Act
        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, ()-> {
            userService.registerUser(userRegistrationDto);
        });

        // Assert
        assertEquals("User with Email: "+userRegistrationDto.getEmail()+" is already registered!" , exception.getMessage());

        verify(userRepositoryMock, times(1)).findByEmail(any(String.class));
        verify(userRepositoryMock, never()).save(any(User.class));
        verify(userMapperMock, never()).toEntity(any(UserRegistrationDto.class));
    }


}
