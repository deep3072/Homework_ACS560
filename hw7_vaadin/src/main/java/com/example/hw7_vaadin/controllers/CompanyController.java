package com.example.hw7_vaadin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hw7_vaadin.entities.Company;
import com.example.hw7_vaadin.services.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

    // my data consists of stock_prices table and companies table. It contains stock prices of 4 companies for the year 2023
    // stock_prices table has a foreign key relation with companies table 

    @Autowired
    private CompanyService service;

    // list all companies
    @GetMapping("/all") 
    public ResponseEntity<Iterable<Company>> getAllCompanies() {
        Iterable<Company> companies = service.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    // get company by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        Company company = service.getCompanyById(id);
        if (company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } 
        else {
            return new ResponseEntity<>("Company not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // get company by non-pk field
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCompanyByName(@PathVariable String name) {
        // System.out.println("Name " + name);
        Company company = service.getCompanyByName(name);
        // System.out.println(company);
        if (company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        } 
        else {
            return new ResponseEntity<>("Company not found with name: " + name, HttpStatus.NOT_FOUND);
        }
    }

    // add a new company
    @PostMapping("/add")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        Company savedCompany = service.addCompany(company);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    // update an existing company
    @PutMapping("/update")
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        Company updatedCompany = service.updateCompany(company);
        if (updatedCompany != null) {
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } 
        else {
            return new ResponseEntity<>("Company not found with id: " + company.getId(), HttpStatus.NOT_FOUND);
        }
    }

    // delete company with id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        boolean isDeleted = service.deleteCompany(id);
        if (isDeleted) {
            return new ResponseEntity<>("Company with id " + id + " deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Company not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}