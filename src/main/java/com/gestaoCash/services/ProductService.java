package com.gestaoCash.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.gestaoCash.model.Company;
import com.gestaoCash.model.Product;

public interface ProductService {
	void saveProduct(Product produto);
	List<Product> findAllProducts();
	Product findProductById(long id);
	List<Product> findAllByEmpresa(Company company, Pageable pageable);
	Long contTotalProduct();
}
