package com.ks.StudentManager;

import com.ks.StudentManager.bl.Student;
import com.ks.StudentManager.bl.StudentFactory;
import com.ks.StudentManager.bl.model.StudentDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= StudentManagerApplication.class)
public class StudentFactoryTests {
    private final Student STUDENT = new Student("Jan", "Nowak", "nowak@kowalski.pl");
    private final StudentDTO STUDENT_DTO = new StudentDTO("Jan", "Nowak", "nowak@kowalski.pl");

    @Test
    public void getProperlyCreateFrom() {
        Student result = StudentFactory.createFrom(STUDENT_DTO);

        Assert.assertEquals(STUDENT, result);
    }

    @Test
    public void getProperlyCreateTo() {
        StudentDTO result = StudentFactory.createTo(STUDENT);

        Assert.assertEquals(STUDENT_DTO, result);
    }

}
