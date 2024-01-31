package com.gestaoCash.services;

import java.util.List;

import com.gestaoCash.model.Company;


public interface CompanyService {
	Company saveCompany(Company company);

	  List<Company> findAllCompany();

	  Company findCompanyById(Long id);
	  void deleteCompanyById(Long id);
	  
}
