package com.ks.StudentManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ks.StudentManager.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StudentControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final MediaType APPLICATION_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private String requestJSON;
    private final String URL_WITH_WRONG_ID = "/students/-1";

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Student student = new Student("Jan", "Nowak", "jannowak@kowalski.pl");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJSON = ow.writeValueAsString(student);
    }

    @Test
    public void getStatusNotFoundForWrongIndexInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(URL_WITH_WRONG_ID)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStatusNotFoundForWrongIndexInUpdateStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(URL_WITH_WRONG_ID).contentType(APPLICATION_JSON).content(requestJSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStatusNotFoundForWrongIndexInDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL_WITH_WRONG_ID)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
