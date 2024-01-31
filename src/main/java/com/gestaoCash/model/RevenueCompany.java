package com.gestaoCash.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
@Entity(name = "receita_empresa")
public class RevenueCompany {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@JoinColumn(name="empresa_id", foreignKey = @ForeignKey(name = "FKEM_ID"))
		@ManyToOne
		private Company empresa;
		
		@Column(columnDefinition = "varchar(100)")
		private String descricao;
		
		private String observacao;
		
		@Column(columnDefinition = "DECIMAL(10,2)", nullable = false)
		private Double valor;

			@DateTimeFormat(iso = ISO.DATE)
		@Column(nullable = false)
		private LocalDate data;

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public Company getEmpresa() {
				return empresa;
			}

			public void setEmpresa(Company empresa) {
				this.empresa = empresa;
			}

			public String getDescricao() {
				return descricao;
			}

			public void setDescricao(String descricao) {
				this.descricao = descricao;
			}

			public String getObservacao() {
				return observacao;
			}

			public void setObservacao(String observacao) {
				this.observacao = observacao;
			}

			public Double getValor() {
				return valor;
			}

			public void setValor(Double valor) {
				this.valor = valor;
			}

			public LocalDate getData() {
				return data;
			}

			public void setData(LocalDate data) {
				this.data = data;
			}
			
			

}
