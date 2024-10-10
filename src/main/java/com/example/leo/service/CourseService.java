package com.example.leo.service;

import com.example.leo.entity.Course;
import com.example.leo.exception.CourseException;
import com.example.leo.mapper.CourseMapper;
import com.example.leo.model.CourseDetailsDto;
import com.example.leo.repository.CourseRepository;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    // Constructor injection is preferred for immutability and easier testing
    @Autowired
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    /** fetch the course details
     * *
     * @return courseDetailsDtoList
     */
    public List<CourseDetailsDto> fetchCourses() {
        List<Course> courses = courseRepository.findAll();
        if(!courses.isEmpty()) {
            return setCourses(courses);
        }else {
            throw new CourseException("No courses available");
        }
    }

    public CourseDetailsDto fetchCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(courseMapper::toDTO).orElse(null);
    }

    private List<CourseDetailsDto> setCourses(List<Course> courses) {
        List<CourseDetailsDto> courseDetailsDtoList = new ArrayList<>();
        for(Course course : courses){
            CourseDetailsDto courseDetailsDto = courseMapper.toDTO(course);
            courseDetailsDtoList.add(courseDetailsDto);
        }
        return courseDetailsDtoList;
    }
}
