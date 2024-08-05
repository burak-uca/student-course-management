package com.student.controller;

import com.student.model.Student;
import com.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/get-all-students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/get-students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create-students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        studentService.registerStudent(student); // Yeni kayıt metodunu kullan
        return ResponseEntity.ok(student);
    }

    @PutMapping("/update-students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, studentDetails);
        return updatedStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> assignCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.assignCourseToStudent(studentId, courseId);
        return ResponseEntity.ok().build();
    }
}
