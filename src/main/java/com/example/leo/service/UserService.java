package com.example.leo.service;

import com.example.leo.entity.User;
import com.example.leo.exception.UserAlreadyExistException;
import com.example.leo.mapper.UserMapper;
import com.example.leo.model.UserRegistrationDto;
import com.example.leo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Constructor injection is preferred for immutability and easier testing
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * * Registers a new user in the system
     * *
     * @param userRegistrationDTO the user registration details
     * @return Id of the newly created user
     * @throws UserAlreadyExistException if the email address is already associated with existing user
     */
    public Long registerUser(UserRegistrationDto userRegistrationDTO) {
        validateUserRegistration(userRegistrationDTO);
        Optional<User> existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistException("User with Email: " + userRegistrationDTO.getEmail() +" is already registered!");
        }
        User user = userMapper.toEntity(userRegistrationDTO);
        try {
            user = userRepository.save(user);
        }catch (Exception e) {
            throw new RuntimeException("An unexpected error occured while saving the user ", e);
        }
        return user.getId();
    }

    /**
     * * validates the user registartion data or business rules if any
     * *
     * @param userRegistrationDTO the user registration details
     * @throws IllegalArgumentException if any validation rule is violated
     */
    private void validateUserRegistration(UserRegistrationDto userRegistrationDTO) {

        if(userRegistrationDTO.getEmail() == null || !userRegistrationDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if(userRegistrationDTO.getPassword() == null || userRegistrationDTO.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }

    }
}
