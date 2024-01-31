package com.gestaoCash.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestaoCash.model.Company;
import com.gestaoCash.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByEmpresa(Company company,Pageable pageable);
	

}
