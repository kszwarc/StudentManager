package com.ks.StudentManager.bl;

import com.ks.StudentManager.bl.model.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<StudentDTO> getById(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.map(student -> new ResponseEntity(StudentFactory.createTo(student), new HttpHeaders(), HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public List<StudentDTO> getAll() {
        return studentRepository.findAll().stream().map(s->StudentFactory.createTo(s)).collect(Collectors.toList());
    }

    public ResponseEntity<StudentDTO> add(StudentDTO studentDTO) {
        Student student = studentRepository.saveAndFlush(StudentFactory.createFrom(studentDTO));
        return new ResponseEntity(StudentFactory.createTo(student), new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity update(StudentDTO studentDTO, Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();
        Student student = StudentFactory.createFrom(studentDTO);
        student.setId(id);
        studentRepository.save(student);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity delete(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
