package com.student.service;

import com.student.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Long id);
    Student createStudent(Student student);
    Optional<Student> updateStudent(Long id, Student studentDetails);
    void deleteStudent(Long id);
    void assignCourseToStudent(Long studentId, Long courseId);
    Student registerStudent(Student student);
    Student calculateGradeAndGPA(Long studentId);
}
