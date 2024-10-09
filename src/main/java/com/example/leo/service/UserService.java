package com.example.leo.service;

import com.example.leo.entity.User;
import com.example.leo.mapper.UserMapper;
import com.example.leo.model.UserRegistrationDto;
import com.example.leo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Long registerUser(UserRegistrationDto userRegistrationDTO) {
        User user = userMapper.toEntity(userRegistrationDTO);
        user = userRepository.save(user);
        return user.getId();
    }
}
