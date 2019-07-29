package com.ks.StudentManager.controller;

import com.ks.StudentManager.model.Student;
import com.ks.StudentManager.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getSAll () {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Student student, @PathVariable long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();
        student.setId(id);
        studentRepository.save(student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable("id") Long id) {
        studentRepository.deleteById(id);
    }
}
