package com.gestaoCash.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;
import com.gestaoCash.repositories.ClientRepository;
import com.gestaoCash.services.ClientService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public void saveClient(Client client) {
    this.clientRepository.save(client);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Client> findAllClient() {
    var clients = this.clientRepository.findAll();

    return clients;
  }

  @Override
  @Transactional(readOnly = true)
  public Client findClientById(Long id) {
    var optionalClient = this.clientRepository.findById(id);

    if (optionalClient.isPresent()) {
      var client = optionalClient.get();
      return client;
    } else {
      throw new EntityNotFoundException();
    }

  }

  @Override
  public void deleteClientById(Long id) {
    this.clientRepository.deleteById(id);
  }

  @Override
  public void deleteAllClientsById(List<Long> ids) {
    this.clientRepository.deleteAllById(ids);
  }

@Override
public List<Client> finAllClientByCompany(Company company, String filter) {
	return this.filter(filter, clientRepository.findAllClientByEmpresa(company));
}

@Override
public int countClient(Company company,String filter) {
	List<Client> clients = clientRepository.findAllClientByEmpresa(company);
		
	clients = this.filter(filter, clients);
	
	return clients.size();
}

public List<Client> filter(String filter, List<Client> clients){
	
	if(filter.equalsIgnoreCase("mes")) {
		clients = clients.stream().filter(client->client.getCreatedAt().getMonth() == LocalDate.now().getMonth() && client.getCreatedAt().getYear() == LocalDate.now().getYear()).collect(Collectors.toList());
	}else if(filter.equalsIgnoreCase("ano")) {
		clients = clients.stream().filter(client->client.getCreatedAt().getYear() == LocalDate.now().getYear()).collect(Collectors.toList());
	}else if(filter.equalsIgnoreCase("hoje")) {
		clients = clients.stream().filter(client->client.getCreatedAt().isEqual(LocalDate.now()) ).collect(Collectors.toList());
	}
	
	return clients;
}

@Override
public List<Client> finAllClientByCompany(Company company, Pageable pageable, String filter) {
	// TODO Auto-generated method stub
	return this.filter(filter, clientRepository.findAllClientByEmpresa(company,pageable));
}

}
