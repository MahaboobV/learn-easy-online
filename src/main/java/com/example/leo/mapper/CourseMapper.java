package com.example.leo.mapper;

import com.example.leo.entity.Course;
import com.example.leo.model.CourseDetailsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CourseDetailsDto courseDetailsDto);

    CourseDetailsDto toDTO(Course course);
}
