package com.gestaoCash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestaoCash.model.Role;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByAuthority(String authority);
}
