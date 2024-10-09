package com.example.leo.controller;

import com.example.leo.exception.ValidationException;
import com.example.leo.model.UserRegistrationDto;
import com.example.leo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDTO,
    BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            // Extract error messages
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ValidationException(errorMessages);
        }
       Long Id = userService.registerUser(userRegistrationDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with ID :"+ Id);
    }

}
