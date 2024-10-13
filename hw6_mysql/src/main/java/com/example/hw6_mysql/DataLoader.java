package com.example.hw6_mysql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // to load data into database when application is start
        ClassPathResource resource = new ClassPathResource("stock_prices_2023.sql");
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String sql = reader.lines().collect(Collectors.joining("\n"));
        
            String[] sqlStatements = sql.split(";");
            
            // Execute each statement individually because executing in bulk gave error
            Arrays.stream(sqlStatements)
                  .filter(statement -> !statement.trim().isEmpty()) 
                  .forEach(jdbcTemplate::execute);
        }
    }
}
