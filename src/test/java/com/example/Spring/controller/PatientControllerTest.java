package com.example.Spring.controller;

import com.example.Spring.EmployeeStatus;
import com.example.Spring.model.Employee;
import com.example.Spring.model.Patient;
import com.example.Spring.repository.EmployeeRepository;
import com.example.Spring.repository.PatientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PatientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    Patient patient1;
    Patient patient2;
    Patient patient3;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Date date1 = new Date(90, Calendar.JANUARY,23);
        Date date2 = new Date(92, Calendar.MARCH,03);
        Date date3 = new Date(88, Calendar.JULY,26);
        Employee employee1 = new Employee("cardiology","Xavi", EmployeeStatus.ON);
        Employee employee2 = new Employee("odontology","Thais", EmployeeStatus.OFF);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        patient1 = new Patient("Ricard",date1 ,employee1);
        patient2 = new Patient("Victor",date2, employee2);
        patient3 = new Patient("Monica",date3, employee2);
        patientRepository.save(patient1);
        patientRepository.save(patient2);
        patientRepository.save(patient3);
    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
    }


    @Test
    void addPatient() throws Exception {
        patientRepository.delete(patient1);
        String body = objectMapper.writeValueAsString(patient1);
        MvcResult mvcResult = mockMvc.perform(post("/patient").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Ricard"));
    }

    @Test
    void updatePatient() throws Exception {
        int patientId = patient1.getPatientId();
        String body = objectMapper.writeValueAsString(patient2);
        MvcResult mvcResult = mockMvc.perform(put("/patient/"+patientId).content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Victor"));
    }


    @Test
    void getAllPatients() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Ricard"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Victor"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Monica"));
    }

    @Test
    void getPatientsByStatus() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patient?status=ON"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Ricard"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Victor"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Monica"));
    }

    @Test
    void getPatientsByDoctorsDepartment() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patient?department=odontology"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Ricard"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Victor"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Monica"));
    }

    @Test
    void getPatientWithId1() throws Exception{
        int patientId = patient1.getPatientId();
        MvcResult mvcResult = mockMvc.perform(get("/patient/"+patientId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Ricard"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Victor"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Monica"));
    }

    @Test
    void getNotCreatedPatientWithId() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patient/500"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("null"));
    }

    @Test
    void getPatientBetweenDates() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/patient/Date/1990-01-01/2000-01-01"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Ricard"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Victor"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Monica"));
    }
}