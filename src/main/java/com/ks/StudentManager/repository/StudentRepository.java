package com.ks.StudentManager.repository;

import com.ks.StudentManager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
