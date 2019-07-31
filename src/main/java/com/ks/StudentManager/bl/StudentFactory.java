package com.ks.StudentManager.bl;

import com.ks.StudentManager.bl.model.StudentDTO;

public class StudentFactory {
    public static Student createFrom(StudentDTO studentDTO) {
        return new Student(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getEmail()
        );
    }

    public static StudentDTO createTo(Student student) {
        return new StudentDTO(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }
}
