package com.gestaoCash.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Table
@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String authority;

  @ManyToMany(mappedBy = "roles")
  private Set<Users> users = new HashSet<>();

  public Role() {

  }

  public Role(Long id, String authority, Set<Users> users) {
    this.id = id;
    this.authority = authority;
    this.users = users;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Set<Users> getUsers() {
	return users;
}

public void setUsers(Set<Users> users) {
	this.users = users;
}

}
