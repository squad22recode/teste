package com.gestaoCash.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "cliente")
@Entity
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, columnDefinition = "varchar(15)")
  private String cpf;

  @Column(columnDefinition = "varchar(50)")
  private String nome;

  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate dataNascimento;

  @Column(columnDefinition = "varchar(17)")
  private String telefone;

  @Column(columnDefinition = "VARCHAR(60)")
  private String email;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "endereco_id", referencedColumnName = "id")
  private Address endereco;
  
  @JoinColumn(name="empresa_id", foreignKey = @ForeignKey(name = "FKClient"))
  @ManyToOne
  private Company empresa;
  
  @OneToMany(mappedBy = "cliente", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<Sale> sale = new ArrayList<Sale>();
  
  @Column(name="created_at")
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate createdAt;
  
  

  public List<Sale> getSale() {
	return sale;
}

public void setSale(List<Sale> sale) {
	this.sale = sale;
}

public LocalDate getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(LocalDate createdAt) {
	this.createdAt = createdAt;
}

public Company getEmpresa() {
	return empresa;
}

public void setEmpresa(Company empresa) {
	this.empresa = empresa;
}

public Client() {

  }

  public Client(Long id, String cpf, String nome, LocalDate dataNascimento, String telefone, String email,
      String endereco,
      String complemento) {
    this.id = id;
    this.cpf = cpf;
    this.nome = nome;
    this.dataNascimento = dataNascimento;
    this.telefone = telefone;
    this.email = email;

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Address getEndereco() {
    return endereco;
  }

  public void setEndereco(Address endereco) {
    this.endereco = endereco;
  }

@Override
public String toString() {
	return "Client [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", telefone="
			+ telefone + ", email=" + email + ", endereco=" + endereco + ", empresa=" + empresa + ", sale=" + sale
			+ ", createdAt=" + createdAt + "]";
}

  
}