package com.gestaoCash.servicesImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestaoCash.model.Role;
import com.gestaoCash.model.Users;
import com.gestaoCash.repositories.AddressRepository;
import com.gestaoCash.repositories.UserRepository;
import com.gestaoCash.services.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AddressRepository addressRepo;

  @Override
  public void saveUser(Users user) {

    this.userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Users> findAllUsers() {
    return this.userRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Users findUserById(Long id) {
    var optionalUser = this.userRepository.findById(id);

    if (optionalUser.isPresent()) {
      var user = optionalUser.get();
      return user;
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  public void updateUserById(Long id, Users updatedUser) {
    this.userRepository.save(updatedUser);
  }

  @Override
  public void deleteUserById(Long id) {
    this.userRepository.deleteById(id);
  }

  @Override
  public Optional<Users> findByEmail(String email) {
   
    return Optional.empty();
  }

@Override
public boolean existsEmail(String email) {
	return this.userRepository.existsByEmail(email);
	
}

  // @Override
  // public UserDetails loadUserByUsername(String username) throws
  // UsernameNotFoundException {

  // var user = this.userRepository.findByEmail(username);

  // if (user != null && user.isPresent()) {
  // var userd = new Users();
  // userd.setEmail(user.get().getEmail());
  // userd.setSenha((user.get().getSenha()));
  // userd.setRoles((java.util.Set<Role>)
  // mapRolesTAuthorities(user.get().getRoles()));

  // return (UserDetails) userd;
  // } else {
  // throw new UsernameNotFoundException("username ou senha inválidos");
  // }
  // }

  // private Collection<? extends GrantedAuthority>
  // mapRolesTAuthorities(Collection<Role> roles) {
  // Collection<? extends GrantedAuthority> mapRoles = roles.stream()
  // .map(role -> new
  // SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());

  // return mapRoles;
  // }

  // @Override
  // public Users findByUserEmail(String email) {
  // // TODO Auto-generated method stub
  // return userRepository.findByUserEmail(email);
  // }

}
