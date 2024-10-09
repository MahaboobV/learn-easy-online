package com.example.leo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiErrorResponse {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private List<String> message;

    private String path;

}
