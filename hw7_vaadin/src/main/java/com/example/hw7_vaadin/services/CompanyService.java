package com.example.hw7_vaadin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hw7_vaadin.entities.Company;
import com.example.hw7_vaadin.repositories.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {

    // service layer implementation 
    @Autowired
    private CompanyRepository repository;

    public Iterable<Company> getAllCompanies() {
        return repository.findAll();
    }

    public Company getCompanyById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Company getCompanyByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    public Company addCompany(Company company) {
        return repository.save(company);
    }

    public Company updateCompany(Company company) {
        if (repository.existsById(company.getId())) {
            return repository.save(company);
        } 
        else {
            return null;
        }
    }

    public boolean deleteCompany(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } 
        else {
            return false;
        }
    }

    public List<Company> getCompanies(String filter) {
        return repository.findByNameContainingIgnoreCase(filter);
    }

    public List<Company> getCompanies() {
        return (List<Company>) repository.findAll();
    }
}
