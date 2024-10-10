package com.example.leo.controller;

import com.example.leo.model.CourseDetailsDto;
import com.example.leo.service.CourseService;
import com.example.leo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<CourseDetailsDto>> getCourses() {

        List<CourseDetailsDto> courseDetailsDtoList = courseService.fetchCourses();

        return ResponseEntity.ok(courseDetailsDtoList);
    }
}
