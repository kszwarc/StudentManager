package com.ks.StudentManager.controller;

import com.ks.StudentManager.model.Student;
import com.ks.StudentManager.repository.StudentRepository;
import com.ks.StudentManager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Student student, @PathVariable Long id) {
        return studentService.update(student, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }
}
