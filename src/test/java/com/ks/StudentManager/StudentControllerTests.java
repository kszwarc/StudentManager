package com.ks.StudentManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ks.StudentManager.bl.Student;
import com.ks.StudentManager.bl.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=StudentManagerApplication.class)
@WebAppConfiguration
public class StudentControllerTests {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private StudentRepository studentRepository;
    private MockMvc mockMvc;
    private final MediaType APPLICATION_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private String requestJSON;
    private String CORRUPTED_REQUEST_JSON = "{s}";
    private final String URL = "/students";
    private final String URL_WITH_WRONG_ID = URL+"/-1";
    private final Student STUDENT = new Student("Jan", "Nowak", "jannowak@kowalski.pl");

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        STUDENT.setId(1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJSON = ow.writeValueAsString(STUDENT);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(STUDENT));
        when(studentRepository.findAll()).thenReturn(Arrays.asList(new Student[] {STUDENT}));
    }

    @Test
    public void getStatusOkAndStudentForProperlyIndexInGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(URL+"/1").contentType(APPLICATION_JSON).content(requestJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(STUDENT.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(STUDENT.getLastName())))
                .andExpect(jsonPath("$.email", is(STUDENT.getEmail())));
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
    public void getStatus4xxForWrongStudentInUpdateStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(URL+"/1").contentType(APPLICATION_JSON).content(CORRUPTED_REQUEST_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getStatus2xxForProperlyIndexInUpdateStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(URL+"/1").contentType(APPLICATION_JSON).content(requestJSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getStatusNotFoundForWrongIndexInDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL_WITH_WRONG_ID)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStatusOkForProperlyIndexInDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL+"/1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void getStatus4xxForWrongStudentInAddStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(URL).contentType(APPLICATION_JSON).content(CORRUPTED_REQUEST_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getStatusOkAndStudentsForGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(URL).contentType(APPLICATION_JSON).content(requestJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(STUDENT.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(STUDENT.getLastName())))
                .andExpect(jsonPath("$[0].email", is(STUDENT.getEmail())))
                .andExpect(jsonPath("$.length()", is(1)));
    }
}
