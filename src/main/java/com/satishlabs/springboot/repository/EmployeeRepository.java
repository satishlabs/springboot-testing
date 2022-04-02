package com.satishlabs.springboot.repository;

import com.satishlabs.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findByEmail(String email);

    //define custome query using JPQL(Java persisitance query lanaguage) with index parameters
    @Query("select e from Employee e where e.firstname =?1 and e.lastname = ?2")
    Employee findJPQL(String firstName,String lastName);


    //define custome query using JPQL(Java persisitance query lanaguage) with named parameters
    @Query("select e from Employee e where e.firstname =:firstName and e.lastname =:lastName")
    Employee findJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName")String lastName);

    //define custome query using Native SQL Query with index params
    @Query(value = "select * from employees e where e.first_name=?1 and e.last_Name=?2 ",nativeQuery = true)
    Employee findByNativeSQL(String fistName,String lastName);


    //define custome query using Native SQL Query with named params
    @Query(value = "select * from employees e where e.first_name=:firstName and e.last_Name=:lastName ",nativeQuery = true)
    Employee findByNativeNamed(@Param("firstName") String fistName,@Param("lastName") String lastName);

}
