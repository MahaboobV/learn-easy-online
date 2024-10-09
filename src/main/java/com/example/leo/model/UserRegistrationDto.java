package com.example.leo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationDto {

    private Long Id;

    private String fullName;

    private String email;

    private String password;

    private String phoneNumber;

    private int age;
}
