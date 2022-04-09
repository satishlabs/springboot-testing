package com.satishlabs.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satishlabs.springboot.model.Employee;
import com.satishlabs.springboot.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@spd.com")
                .build();

        //when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the result or output using assert statements
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname",
                        CoreMatchers.is(employee.getFirstname())))
                .andExpect(jsonPath("$.lastname",
                        CoreMatchers.is(employee.getLastname())))
                .andExpect(jsonPath("$.email",
                        CoreMatchers.is(employee.getEmail())));
    }

    //Integration test for Get All Employees REST API
    @Test
    public void givenListOfEmployee_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        //given - precondtion or setup
        List<Employee> listEmployees = new ArrayList<>();
        listEmployees.add(Employee.builder().firstname("Kumar").lastname("Prasad").email("km@spd.com").build());
        listEmployees.add(Employee.builder().firstname("Satish").lastname("Prasad").email("sp@spd.com").build());
        employeeRepository.saveAll(listEmployees);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(listEmployees.size())));
    }

    //postive scenario - valid employee Id
    //Integration test for GET Employee By Id REST API
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given - precondtion or setup
        Employee employee = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@spd.com")
                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}",employee.getId()));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstname", CoreMatchers.is(employee.getFirstname())))
                .andExpect(jsonPath("$.lastname", CoreMatchers.is(employee.getLastname())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    //negative scenario - valid employee Id
    //Integration test for GET Employee By Id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        //given - precondtion or setup
        long employeeId=1L;
        Employee employee = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@spd.com")
                .build();
        employeeRepository.save(employee);
        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}",employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //Integration test for update Employee REST API - positive scenarios
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        //given - precondtion or setup
        Employee savedEmployee = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@spd.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstname("Manish")
                .lastname("Kumar")
                .email("manish_kumar@spd.com")
                .build();


        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}",savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstname",CoreMatchers.is(updatedEmployee.getFirstname())))
                .andExpect(jsonPath("$.lastname",CoreMatchers.is(updatedEmployee.getLastname())))
                .andExpect(jsonPath("$.email",CoreMatchers.is(updatedEmployee.getEmail())));
    }

    //Integration test for update Employee REST API - negative scenarios
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        //given - precondtion or setup
        long employeeId=1L;
        Employee savedEmployee = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@spd.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstname("Manish")
                .lastname("Kumar")
                .email("manish_kumar@spd.com")
                .build();

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

   /* //Integration test for delete Employee Id REST API
   @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given - precondtion or setup
       Employee savedEmployee = Employee.builder()
               .firstname("Satish")
               .lastname("Prasad")
               .email("satish_prasad@spd.com")
               .build();
       employeeRepository.save(savedEmployee);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employee/{id}",savedEmployee.getId()));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }*/
}
