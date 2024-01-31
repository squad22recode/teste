package com.gestaoCash.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestaoCash.model.Company;
import com.gestaoCash.repositories.CompanyRepository;
import com.gestaoCash.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository comRepo;
	
	@Override
	public Company saveCompany(Company company) {
		return comRepo.save(company);
	}

	@Override
	public List<Company> findAllCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company findCompanyById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCompanyById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
