package com.ks.StudentManager;

import com.ks.StudentManager.bl.Student;
import com.ks.StudentManager.bl.StudentFactory;
import com.ks.StudentManager.bl.StudentRepository;
import com.ks.StudentManager.bl.StudentService;
import com.ks.StudentManager.bl.model.StudentDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=StudentManagerApplication.class)
public class StudentServiceTests {
    @MockBean
    private StudentRepository studentRepository;
    private StudentService studentService;
    private final Student STUDENT = new Student("Jan", "Nowak", "Jan@nowak.pl");
    private final StudentDTO STUDENT_DTO = StudentFactory.createTo(STUDENT);
    private final Long WRONG_ID = -1L;
    private final Long CORRECT_ID = 1L;

    @Before
    public void setUp() {
        when(studentRepository.findById(CORRECT_ID)).thenReturn(Optional.of(STUDENT));
        when(studentRepository.findAll()).thenReturn(Arrays.asList(new Student[] {STUDENT}));
        when(studentRepository.saveAndFlush(STUDENT)).thenReturn(STUDENT);
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void getByIdTestProperlyStudent() {
        ResponseEntity<StudentDTO> expected = new ResponseEntity(STUDENT_DTO, new HttpHeaders(), HttpStatus.OK);

        ResponseEntity<StudentDTO> result = studentService.getById(CORRECT_ID);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void getByIdTestWrongId() {
        ResponseEntity<StudentDTO> expected = ResponseEntity.notFound().build();

        ResponseEntity<StudentDTO> result = studentService.getById(WRONG_ID);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void getAllTest() {
        StudentDTO[] expected = new StudentDTO[] {STUDENT_DTO};

        List<StudentDTO> result = studentService.getAll();

        Assert.assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void addTest() {
        ResponseEntity<StudentDTO> expected = new ResponseEntity(STUDENT_DTO, new HttpHeaders(), HttpStatus.OK);

        ResponseEntity<StudentDTO> result = studentService.add(STUDENT_DTO);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void updateWrongIdTest() {
        ResponseEntity<StudentDTO> expected = ResponseEntity.notFound().build();

        ResponseEntity<StudentDTO> result = studentService.update(STUDENT_DTO,WRONG_ID);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void updateProperlyIdTest() {
        ResponseEntity<StudentDTO> expected = ResponseEntity.noContent().build();;

        ResponseEntity<StudentDTO> result = studentService.update(STUDENT_DTO, CORRECT_ID);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void deleteWrongIdTest() {
        ResponseEntity<StudentDTO> expected = ResponseEntity.notFound().build();

        ResponseEntity<StudentDTO> result = studentService.delete(WRONG_ID);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void deleteProperlyIdTest() {
        ResponseEntity<StudentDTO> expected = ResponseEntity.noContent().build();;

        ResponseEntity<StudentDTO> result = studentService.delete(CORRECT_ID);

        Assert.assertEquals(expected, result);
    }

}
