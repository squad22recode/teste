package com.gestaoCash.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity(name = "despesa_usuario")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(foreignKey = @ForeignKey(name = "usuario_id"))
  @ManyToOne
  private Users usuario;

  private String categoria;

  @Column(nullable = false)
@DateTimeFormat(iso = ISO.DATE)
  private LocalDate data;

  private String descricao;

  @Column(columnDefinition = "DECIMAL(10,2)")
  private Double valor;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public String getCategoria() {
	return categoria;
}

public void setCategoria(String categoria) {
	this.categoria = categoria;
}

  public Users getUsuario() {
    return usuario;
  }

  public void setUsuario(Users usuario) {
    this.usuario = usuario;
  }



  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

@Override
public String toString() {
	return "id:" + id + ",usuario:" + usuario.getId() + ",categoria:" + categoria + ",data:" + data
			+ ",descricao:" + descricao + ",valor:" + valor;
}

  
}
