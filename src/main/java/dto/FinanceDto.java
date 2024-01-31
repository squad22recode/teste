package dto;

import java.util.List;

import com.gestaoCash.model.ItemSale;

public class FinanceDto {
	private long id;
	private double revenue;
	private double expense;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	public double getExpense() {
		return expense;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	
	
}
