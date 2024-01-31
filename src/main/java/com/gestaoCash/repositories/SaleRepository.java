package com.gestaoCash.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;
import com.gestaoCash.model.Sale;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
List<Sale> findAllSaleByCliente(Client cliente);
List<Sale> findAllSaleByEmpresa(Company company, Pageable pageable);
List<Sale> findAllSaleByEmpresa(Company company);

}
