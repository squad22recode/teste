package com.gestaoCash.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.gestaoCash.model.UserDetailsImpl;
import com.gestaoCash.model.Users;
import com.gestaoCash.services.UserDetailsServiceImpl;
import com.gestaoCash.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	public UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						authorizeConfig -> authorizeConfig
								.requestMatchers("/").permitAll()
								.requestMatchers("usuario/cadastro").permitAll()
								.requestMatchers("fragments/*").permitAll()
								.requestMatchers("/sobre").permitAll()
								.requestMatchers("/contato").permitAll()
								.requestMatchers("/duvidas").permitAll()
								.requestMatchers("usuario/entrar").permitAll()
								.requestMatchers("/assets/images/*").permitAll()
								.requestMatchers("/assets/styles/*").permitAll()
								.requestMatchers("/assets/scripts/*").permitAll()
								.requestMatchers("/css/*").permitAll()
								.requestMatchers("/painel-controle/**").hasRole("ADMIN")
								.anyRequest().authenticated()

				)
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/usuario/area-cliente")
						.permitAll())
				.logout(logout -> logout
						.permitAll()
						.logoutSuccessUrl("/"))
				.build();

	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(userDetailsServiceImpl)
				.passwordEncoder(passwordEncoder());
	}
}
