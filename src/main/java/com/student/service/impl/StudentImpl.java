package com.student.service.impl;

import com.student.model.Course;
import com.student.model.Student;
import com.student.repository.CourseRepository;
import com.student.repository.StudentRepository;
import com.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setAge(studentDetails.getAge());
            student.setGpa(studentDetails.getGpa());
            student.setSection(studentDetails.getSection());
            student.setMidterm(studentDetails.getMidterm());
            student.setFinalExam(studentDetails.getFinalExam());
            return studentRepository.save(student);
        });
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void assignCourseToStudent(Long studentId, Long courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            Student student = studentOpt.get();
            Course course = courseOpt.get();
            student.getCourses().add(course);
            studentRepository.save(student);
        }
    }

    @Override
    public Student registerStudent(Student student) {
        if (student.getGpa() >= 3.5) {
            student.setSection("A");
        } else if (student.getGpa() >= 2.0) {
            student.setSection("B");
        } else {
            student.setSection("C");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student calculateGradeAndGPA(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            double midterm = student.getMidterm();
            double finalExam = student.getFinalExam();
            double finalGrade = (midterm * 0.6) + (finalExam * 0.4);

            if (finalExam < 35 || finalGrade < 35) {
                student.setSection("F");
                studentRepository.save(student);
                return student;
            } else {
                double gpaMultiplier = 2.0;
                if (finalGrade >= 50 && finalGrade < 60) {
                    gpaMultiplier = 2.5;
                } else if (finalGrade >= 60 && finalGrade < 70) {
                    gpaMultiplier = 3.0;
                } else if (finalGrade >= 70 && finalGrade < 90) {
                    gpaMultiplier = 3.5;
                } else if (finalGrade >= 90 && finalGrade <= 100) {
                    gpaMultiplier = 4.0;
                }

                double newGPA = (student.getGpa() * 0.8) + (gpaMultiplier * 0.2);
                student.setGpa(newGPA);
            }

            return studentRepository.save(student);
        }
        return null;
    }
}
