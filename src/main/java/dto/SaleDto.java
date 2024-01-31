package dto;

import java.time.LocalDate;
import java.util.List;

import com.gestaoCash.model.Client;
import com.gestaoCash.model.Product;

public class SaleDto {

		private long id;
		
		private int quantidade;
		private double valor;
		private int desconto;
		
		private List<Product> produtos;
		

		private Client cliente;
		
		private LocalDate data;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
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

		public int getDesconto() {
			return desconto;
		}

		public void setDesconto(int desconto) {
			this.desconto = desconto;
		}

		
		public List<Product> getProdutos() {
			return produtos;
		}

		public void setProdutos(List<Product> produtos) {
			this.produtos = produtos;
		}

		@Override
		public String toString() {
			return "Sale [id=" + id + ", quantidade=" + quantidade + ", valor=" + valor + ", desconto=" + desconto
					+ ", produtos=" + produtos + ", cliente=" + cliente + ", data=" + data + "]";
		}
		

}
