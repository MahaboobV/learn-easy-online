package com.example.leo.repository;

import com.example.leo.entity.Course;
import com.example.leo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Optional<Course>> findByCourseType(String courseType);
}
