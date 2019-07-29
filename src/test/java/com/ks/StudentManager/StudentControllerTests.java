package com.ks.StudentManager;

import com.ks.StudentManager.model.Student;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

public class StudentControllerTests extends StudentAbstractTests {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getStudentsList() throws Exception {
        String uri = "/students";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Student[] studentList = super.mapFromJson(content, Student[].class);
        assertTrue(studentList.length > 0);
    }
}
