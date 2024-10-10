package com.example.leo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDetailsDto {
    private String title;

    private String description;

    private String instructor;

    private String duration;

    private String courseType;

    private Double price;
}
