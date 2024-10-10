package com.example.leo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "instructor", nullable = false)
    private String instructor;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "course_type", nullable = false)
    private String courseType;

    @Column(name = "price", nullable = false)
    private Double price;
}
