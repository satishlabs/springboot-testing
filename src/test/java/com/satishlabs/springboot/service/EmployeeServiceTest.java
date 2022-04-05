package com.satishlabs.springboot.service;

import com.satishlabs.springboot.exception.ResourceNotFoundException;
import com.satishlabs.springboot.model.Employee;
import com.satishlabs.springboot.repository.EmployeeRepository;
import com.satishlabs.springboot.service.Impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    //private EmployeeService employeeService;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
         employee = Employee.builder()
                .id(1L)
                .firstname("Kumar")
                .lastname("Mahto")
                .email("km@gmail")
                .build();
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    //Junit test for savedEmployee method
    @DisplayName("JUnit for savedEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        //given - precondtion or setup

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for savedEmployee method
    @DisplayName("JUnit for savedEmployee method which throws Exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowsException(){
        //given - precondtion or setup

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

     //   given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or behaviour that we are going to test
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            employeeService.saveEmployee(employee);
        });

        //then - verify the output
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    //Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList(){
        //given - precondtion or setup
            Employee  employee1 = Employee.builder()
                    .id(2L)
                    .firstname("Satish")
                    .lastname("Prasad")
                    .email("sp@gmail")
                    .build();
            given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));
        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //Junit test for getAllEmployees method with negative scenario
    @DisplayName("Junit test for getAllEmployees method(negative Scenario)")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        //given - precondtion or setup
        Employee  employee1 = Employee.builder()
                .id(2L)
                .firstname("Satish")
                .lastname("Prasad")
                .email("sp@gmail")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    //Junit test for getEmployeeById method
    @DisplayName("Junit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        //given - precondtion or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for updateEmployee method
    @DisplayName("Junit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given - precondtion or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("satish@gmail.com");
        employee.setFirstname("Satish");
        employee.setLastname("Prasad");

        //when - action or behaviour that we are going to test
       Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("satish@gmail.com");
        assertThat(updatedEmployee.getFirstname()).isEqualTo("Satish");
        assertThat(updatedEmployee.getLastname()).isEqualTo("Prasad");
    }


    //Junit test for delete employee method
    @DisplayName("Junit test for delete employee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        long employeeId = 1L;
        //given - precondtion or setup
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when - action or behaviour that we are going to test
        employeeService.deleteEmployee(employeeId);

        //then - verify the output
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }
}
