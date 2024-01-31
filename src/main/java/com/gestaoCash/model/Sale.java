package com.gestaoCash.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name="venda")
@Entity
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_venda")
	private long id;
	
	@JoinColumn(name="cliente_id", foreignKey = @ForeignKey(name = "FKCLI_ID"))
	@ManyToOne
	private Client cliente;
	
	@JoinColumn(name="empresa_id", foreignKey = @ForeignKey(name = "FKCompanySale"))
	  @ManyToOne
	  private Company empresa;
	
//	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinTable(name = "produto_venda", joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "venda_id"))
//	List<Product> produtos = new ArrayList<>();
	
//	@OneToMany(mappedBy = "venda", orphanRemoval = true, cascade = CascadeType.PERSIST)
//	 @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
//	private List<Product> produtos;
	
//	@Column(columnDefinition = "json")
//	private String produtos;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate data;
	
	private double valorTotal;
	private int desconto;
	

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
	private List<ItemSale> itemSale;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
//	public String getProdutos() {
//		return produtos;
//	}
//	public void setProdutos(String produtos) {
//		this.produtos = produtos;
//	}
	
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getDesconto() {
		return desconto;
	}
	public void setDesconto(int desconto) {
		this.desconto = desconto;
	}
	
	public Client getCliente() {
		return cliente;
	}
	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}
	
	public LocalDate getData() {
	return data;
}

public void setData(LocalDate data) {
	this.data = data;
}

public Company getEmpresa() {
	return empresa;
}
public void setEmpresa(Company empresa) {
	this.empresa = empresa;
}
public List<ItemSale> getItemSale() {
	return itemSale;
}
public void setItemSale(List<ItemSale> itemSale) {
	this.itemSale = itemSale;
}
//public List<Product> getProdutos() {
//	return produtos;
//}
//public void setProdutos(List<Product> produtos) {
//	this.produtos = produtos;
//}
@Override
public String toString() {
	return "Sale [id=" + id + ", cliente=" + cliente + ", empresa=" + empresa + ", data=" + data + ", valorTotal="
			+ valorTotal + ", desconto=" + desconto + ", itemSale=" + itemSale + "]";
}



	
//	
//	private int quantidade;
//	private double valor;
//	
//	@OneToMany(mappedBy = "venda", orphanRemoval = true, cascade = CascadeType.PERSIST)
//	private List<Product> produto = new ArrayList<Product>();
//	
//	@JoinColumn(name="cliente_id", foreignKey = @ForeignKey(name = "FKCLI_ID"))
//	@ManyToOne
//	private Client cliente;
//	

//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public int getQuantidade() {
//		return quantidade;
//	}
//
//	public void setQuantidade(int quantidade) {
//		this.quantidade = quantidade;
//	}
//
//	public double getValor() {
//		return valor;
//	}
//
//	public void setValor(double valor) {
//		this.valor = valor;
//	}
//
//
//	public Client getCliente() {
//		return cliente;
//	}
//
//	public void setCliente(Client cliente) {
//		this.cliente = cliente;
//	}
//

//	
	
}
