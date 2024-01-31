package com.gestaoCash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaoCash.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
