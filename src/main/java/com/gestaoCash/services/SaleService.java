package com.gestaoCash.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;
import com.gestaoCash.model.ItemSale;
import com.gestaoCash.model.Product;
import com.gestaoCash.model.Sale;

import dto.FinanceDto;




public interface SaleService {
	Sale saveSale(Sale sale);
	List<Sale> findAllSaleByClienteByCompany(Client cliente, Company company);
	List<Sale> findAllSalesByCompany(Company company,String FilterDate);
	List<Sale> findAllSalesByCompanyAndFilter(Company company, Pageable pageable, String filterDate);
	Sale findSaleById(long id);
	String converterListToJson(List<Sale> sale, String dataFilter);
	int countSales(Company company,String filter);
	void deleteSale(long id);
//a fazer
	int calcPercentDate(List<Sale> sale, String dateType);
	List<FinanceDto> controlFinance(List<Sale> sale);
//	void addRelationShip(List<Product> product, long saleId);
//	List<Product> findAllProductById( Sale sale);
}
