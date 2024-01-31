package com.gestaoCash.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gestaoCash.repositories.UserRepository;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Users users;

    public UserDetailsImpl(Users user) {
        this.users = user;
    }

    // public UserDetailsImpl(UserRepository userRepository) {
    // this.userRepository = userRepository;
    // }

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // String perfil = users.getTipoUsuario().equals("admin") ? "USER_ADMIN" :
    // "USER_COMUM";

    // return AuthorityUtils.createAuthorityList(perfil);

    // }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (users.getTipoUsuario().equals("admin"))
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_COMUM"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_COMUM"));
    }

    @Override
    public String getPassword() {
        return users.getSenha();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    public Users getDetailsLog() {
        return users;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
