package com.example.hw6_mysql.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hw6_mysql.entities.Company;
import com.example.hw6_mysql.repositories.CompanyRepository;

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
}
