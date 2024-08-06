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

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, studentDetails);
        return updatedStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> assignCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.assignCourseToStudent(studentId, courseId);
        return ResponseEntity.ok().build();
    }
//öğrencinin notunu girmek ve güncellemek için kullanırız
    @PostMapping("/{studentId}/exams")
    public ResponseEntity<Student> updateExams(
            @PathVariable Long studentId,
            @RequestParam Double midterm,
            @RequestParam Double finalExam) {
        Optional<Student> studentOpt = studentService.getStudentById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setMidterm(midterm);
            student.setFinalExam(finalExam);
            studentService.updateStudent(studentId, student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//girilen notların gpa ya etki etmesi için kullanılır
@PostMapping("/{studentId}/calculate")
public ResponseEntity<Student> calculateGradeAndGPA(@PathVariable Long studentId) {
    Student student = studentService.calculateGradeAndGPA(studentId);
    if (student != null) {
        return ResponseEntity.ok(student);
    } else {
        return ResponseEntity.notFound().build();
    }
}
}