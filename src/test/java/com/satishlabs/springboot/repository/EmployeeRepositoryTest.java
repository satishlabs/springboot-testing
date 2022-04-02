package com.satishlabs.springboot.repository;

import com.satishlabs.springboot.model.Employee;
//import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit for save employee operation

    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition or setup
        Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();
        //when - action or behavriour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        /*Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);*/
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }
/*
    //Junit test for
    @Test
    public void given_when_then(){
        //given - precondtion or setup

        //when - action or behaviour that we are going to test

        //then - verify the output
    }*/

    //Junit test for
    @DisplayName("JUnit for get all employee operation")
    @Test
    public void givenEmplpyeesList_whenFindAll_thenEmployeesList(){
        //given - precondtion or setup
        Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();
        Employee employee1  = Employee.builder()
                .firstname("Manish")
                .lastname("Kumar")
                .email("manish_kumar@gmail")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }
}
