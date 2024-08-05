package com.student.service.impl;

import com.student.model.Course;
import com.student.repository.CourseRepository;
import com.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> updateCourse(Long id, Course courseDetails) {
        return courseRepository.findById(id).map(course -> {
            course.setCourseName(courseDetails.getCourseName());
            return courseRepository.save(course);
        });
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
