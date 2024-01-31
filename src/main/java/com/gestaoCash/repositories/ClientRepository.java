package com.gestaoCash.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;

public interface ClientRepository extends JpaRepository<Client, Long> {
List<Client> findAllClientByEmpresa(Company company,Pageable pageable);
List<Client> findAllClientByEmpresa(Company company);
}
