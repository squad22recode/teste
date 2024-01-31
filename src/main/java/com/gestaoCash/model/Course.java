package com.gestaoCash.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "curso")
@Entity
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "VARCHAR(255)")
  private String nomeCurso;

  private String url;
  private String descricao;
  private int duracao;
  private int conclusao;

  private boolean concluido = false; // Campo para indicar se o curso foi concluído

  public Course() {

  }

  public Course(Long id, String nomeCurso, String url, String descricao) {
    this.id = id;
    this.nomeCurso = nomeCurso;
    this.url = url;
    this.descricao = descricao;
    this.concluido = false; // Por padrão, ao criar um novo curso, ele não está concluído
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getDuracao() {
    return duracao;
  }

  public void setDuracao(int duracao) {
    this.duracao = duracao;
  }

  public int getConclusao() {
    return conclusao;
  }

  public void setConclusao(int conclusao) {
    this.conclusao = conclusao;
  }

  public String getNomeCurso() {
    return nomeCurso;
  }

  public void setNomeCurso(String nomeCurso) {
    this.nomeCurso = nomeCurso;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public boolean isConcluido() {
    return concluido;
  }

  public void setConcluido(boolean concluido) {
    this.concluido = concluido;
  }
}
