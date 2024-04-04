package com.example.Spring.controller;

import com.example.Spring.EmployeeDTO;
import com.example.Spring.EmployeeStatus;
import com.example.Spring.model.Employee;
import com.example.Spring.model.Patient;
import com.example.Spring.repository.EmployeeRepository;
import com.example.Spring.repository.PatientRepository;
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
class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    EmployeeRepository employeeRepository;
    Employee employee1;
    Employee employee2;
    Employee employee3;
    int employeeId;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        employee1 = new Employee("cardiology","Xavi", EmployeeStatus.ON);
        employee2 = new Employee("odontology","Thais", EmployeeStatus.OFF);
        employee3 = new Employee("odontology","Haku", EmployeeStatus.OFF);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }
    @Test
    void getEmployees() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Xavi"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Thais"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Haku"));
    }

    @Test
    void getEmployeesByStatus() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees?status=ON"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Xavi"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Thais"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Haku"));
    }

    @Test
    void getEmployeesByDepartment() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/employees?department=odontology"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Xavi"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Thais"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Haku"));
    }

    @Test
    void getEmployeeByID() throws Exception{
        employeeId = employee1.getEmployeeId();
        MvcResult mvcResult = mockMvc.perform(get("/employees/"+employeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Xavi"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Thais"));
        assertFalse(mvcResult.getResponse().getContentAsString().contains("Haku"));
    }

    @Test
    void getNotCreatedEmployeetWithId() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/employees/500"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("null"));
    }

    @Test
    void addEmployee() throws Exception {
        employeeRepository.delete(employee1);
        String body = objectMapper.writeValueAsString(employee1);
        MvcResult mvcResult = mockMvc.perform(post("/employees").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Xavi"));
    }

    @Test
    void updateEmployeeStatus() throws Exception{
        employeeId = employee1.getEmployeeId();
        EmployeeDTO values = new EmployeeDTO();
        values.setStatus(EmployeeStatus.OFF);
        String body = objectMapper.writeValueAsString(values);
        MvcResult mvcResult = mockMvc.perform(patch("/employees/"+employeeId).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("OFF"));
    }

    @Test
    void updateEmployeeDepartment() throws Exception{
        employeeId = employee1.getEmployeeId();
        EmployeeDTO values = new EmployeeDTO();
        values.setDepartment("Engineering");
        String body = objectMapper.writeValueAsString(values);
        MvcResult mvcResult = mockMvc.perform(patch("/employees/"+employeeId).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Engineering"));
    }
}