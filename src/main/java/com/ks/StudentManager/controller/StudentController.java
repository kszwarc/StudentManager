package com.ks.StudentManager.controller;

import com.ks.StudentManager.model.Student;
import com.ks.StudentManager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class StudentController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @GetMapping("students")
    public List<Student> getStudents () {
        return studentRepository.findAll();
    }

    @PostMapping("students")
    public Student addBook(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}
