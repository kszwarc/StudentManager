package com.ks.StudentManager.api;

import com.ks.StudentManager.bl.StudentService;
import com.ks.StudentManager.bl.model.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping
    public List<StudentDTO> getAll() {
        return studentService.getAll();
    }

    @PostMapping
    public ResponseEntity add(@RequestBody StudentDTO student) {
        return studentService.add(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody StudentDTO student, @PathVariable Long id) {
        return studentService.update(student, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }
}
