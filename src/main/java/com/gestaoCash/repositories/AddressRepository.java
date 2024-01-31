package com.gestaoCash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaoCash.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
