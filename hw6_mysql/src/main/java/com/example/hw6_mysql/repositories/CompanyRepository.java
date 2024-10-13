package com.example.hw6_mysql.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.hw6_mysql.entities.Company;

// crud repo for comapny entity
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Optional<Company> findByName(String name);
}