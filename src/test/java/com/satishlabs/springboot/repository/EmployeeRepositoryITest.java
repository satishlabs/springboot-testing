package com.satishlabs.springboot.repository;

import com.satishlabs.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryITest {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();
    }

    //Integration for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        //when - action or behavriour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        /*Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);*/
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }
/*
    //Integration test for
    @Test
    public void given_when_then(){
        //given - precondtion or setup

        //when - action or behaviour that we are going to test

        //then - verify the output
    }*/

    //Integration test for
    @DisplayName("JUnit for get all employee operation")
    @Test
    public void givenEmplpyeesList_whenFindAll_thenEmployeesList(){
        //given - precondtion or setup
        /*Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
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
    //Integration test for getEmployeeById operation
    @DisplayName("JUnit test for get EmployeeById Operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenRetunEmployeeObject(){
        //given - precondtion or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Integration test for get employee by email operation
    @DisplayName("JUnit for get employee be email")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        //given - precondtion or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }
    //Integration test for update employee operation
    @DisplayName("JUnit for update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenUpdatedEmployee(){
        //given - precondtion or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("prasadsatish.rnc@gmail");
        savedEmployee.setFirstname("testFirst");
        savedEmployee.setLastname("testLast");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("prasadsatish.rnc@gmail");
        assertThat(updatedEmployee.getFirstname()).isEqualTo("testFirst");

    }

    //Integration test for delete employee operation
    @DisplayName("JUnit for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        //given - precondtion or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        //then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    //Integration test for custom query using JPQL with index
    @DisplayName("JUnit for customer query JPQL using index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenEmployeeObject(){
        //given - precondtion or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        String firstName = "Satish";
        String lastName = "Prasad";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findJPQL(firstName,lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Integration test for using JQPL with Named params
    @DisplayName("JUnit for customer query JPQL using named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenEmployeeObject(){
        //given - precondtion or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        String firstName = "Satish";
        String lastName = "Prasad";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findJPQLNamedParams(firstName,lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Integration test for custome query using Native SQL with index
    @DisplayName("JUnit for custom query using Native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        //given - precondtion or setup
        /*Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        String firstName = "Satish";
        String lastName = "Prasad";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName,lastName);
        //then - verify the output

        assertThat(savedEmployee).isNotNull();
    }

    //Integration test for custome query using Native SQL with named params
    @DisplayName("JUnit for custom query using Native SQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamed_thenReturnEmployeeObject(){
        //given - precondtion or setup
       /* Employee employee  = Employee.builder()
                .firstname("Satish")
                .lastname("Prasad")
                .email("satish_prasad@gmail")
                .build();*/
        employeeRepository.save(employee);

        String firstName = "Satish";
        String lastName = "Prasad";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeNamed(firstName,lastName);
        //then - verify the output

        assertThat(savedEmployee).isNotNull();
    }
}
