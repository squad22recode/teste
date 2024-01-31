package com.gestaoCash.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name="empresa")
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_empresa")
	private long idEmpresa;
	
	@Column(name="nome_empresa", length = 50)
	private String nomeEmpresa;
	
	@Column(name="cnpj", length = 18)
	private String cnpj;
	
	@Column(name="razao_social", length = 50)
	private String razaoSocial;
	
	@OneToOne(mappedBy = "empresa")
	private Users usuario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private AddressCompany enderecoEmpresa;
	
	@OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<RevenueCompany> revenueCompany = new ArrayList<RevenueCompany>();
		
	@OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<Client> client = new ArrayList<Client>();
	
	@OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<Sale> sale;
	
	@OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<Product> product = new ArrayList<Product>();
	
	@Column(name="categoria", length = 8)
	private String category; 
	

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Client> getClient() {
		return client;
	}

	public void setClient(List<Client> client) {
		this.client = client;
	}

	public List<RevenueCompany> getRevenueCompany() {
		return revenueCompany;
	}

	public void setRevenueCompany(List<RevenueCompany> revenueCompany) {
		this.revenueCompany = revenueCompany;
	}

	public AddressCompany getEnderecoEmpresa() {
		return enderecoEmpresa;
	}

	public void setEnderecoEmpresa(AddressCompany enderecoEmpresa) {
		this.enderecoEmpresa = enderecoEmpresa;
	}

	public Users getUsuario() {
		return usuario;
	}

	public void setUsuario(Users usuario) {
		this.usuario = usuario;
	}

	public long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public List<Sale> getSale() {
		return sale;
	}

	public void setSale(List<Sale> sale) {
		this.sale = sale;
	}


		
}
