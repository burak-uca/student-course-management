package com.student.service;
import com.student.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Long id);
    Course createCourse(Course course);
    Optional<Course> updateCourse(Long id, Course courseDetails);
    void deleteCourse(Long id);
}
