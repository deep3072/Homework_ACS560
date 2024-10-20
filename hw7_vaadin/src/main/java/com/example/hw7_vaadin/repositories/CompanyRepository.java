package com.example.hw7_vaadin.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.hw7_vaadin.entities.Company;

import java.util.List;

// crud repo for comapny entity
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Optional<Company> findByName(String name);
    List<Company> findByNameContainingIgnoreCase(String name);
}