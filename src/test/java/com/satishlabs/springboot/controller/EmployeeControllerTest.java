package com.satishlabs.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satishlabs.springboot.model.Employee;
import com.satishlabs.springboot.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import org.hamcrest.CoreMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@spd.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((emp) -> emp.getArgument(0));

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

    //Junit test for Get All Employees REST API
    @Test
    public void givenListOfEmployee_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        //given - precondtion or setup
        List<Employee> listEmployees = new ArrayList<>();
        listEmployees.add(Employee.builder().firstname("Kumar").lastname("Prasad").email("km@spd.com").build());
        listEmployees.add(Employee.builder().firstname("Satish").lastname("Prasad").email("sp@spd.com").build());
        given(employeeService.getAllEmployees()).willReturn(listEmployees);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(listEmployees.size())));
    }
}
