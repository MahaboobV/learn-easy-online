package com.example.leo.controller;

import com.example.leo.exception.ValidationException;
import com.example.leo.model.UserLoginDto;
import com.example.leo.model.UserRegistrationDto;
import com.example.leo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDto userLoginDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Extract error messages
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            throw new ValidationException(errorMessages);
        }

        UserRegistrationDto userRegistrationDto = userService.authenticateUser(userLoginDto.getEmail(), userLoginDto.getPassword());
        if (userRegistrationDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

       /* Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);*/

        return ResponseEntity.status(HttpStatus.CREATED).body("Login successful for user: " + userRegistrationDto.getFullName());
    }
}
