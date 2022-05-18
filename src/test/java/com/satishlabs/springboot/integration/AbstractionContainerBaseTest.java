package com.satishlabs.springboot.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class AbstractionContainerBaseTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
                .withUsername("satish")
                .withPassword("satishtest")
                .withDatabaseName("emsdb");

        MY_SQL_CONTAINER.start();
    }



    @DynamicPropertySource
    public static void dynamicPropertySoruce(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username",MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password",MY_SQL_CONTAINER::getPassword);
    }
}
