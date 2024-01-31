package com.gestaoCash.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="item_venda")
public class ItemSale {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itemId;
	
	 	private int quantidade;
	    private double subtotal;

	    @ManyToOne
	    @JoinColumn(name = "produto_id")
	    private Product produto;
	    
	    @ManyToOne
	    @JoinColumn(name = "venda_id")
	    private Sale venda;

		public long getItemId() {
			return itemId;
		}

		public void setItemId(long itemId) {
			this.itemId = itemId;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}

		public double getSubtotal() {
			return subtotal;
		}

		public void setSubtotal(double subtotal) {
			this.subtotal = subtotal;
		}

		public Product getProduto() {
			return produto;
		}

		public void setProduto(Product produto) {
			this.produto = produto;
		}

		public Sale getVenda() {
			return venda;
		}

		public void setVenda(Sale venda) {
			this.venda = venda;
		}

		@Override
		public String toString() {
			return "ItemSale [itemId=" + itemId + ", quantidade=" + quantidade + ", subtotal=" + subtotal + ", produto="
					+ produto + ", venda=" + venda + "]";
		}

	

	
	    
	    
}
