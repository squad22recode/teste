package com.gestaoCash.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;

public interface ClientService {
  void saveClient(Client client);
  List<Client> findAllClient();
  List<Client> finAllClientByCompany(Company company, String filter);
  List<Client> finAllClientByCompany(Company company,Pageable pegeable, String filter);
  int countClient(Company company,String filter);
  Client findClientById(Long id);
  void deleteClientById(Long id);
  void deleteAllClientsById(List<Long> ids);
}
