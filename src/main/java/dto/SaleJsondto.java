package dto;

import java.time.LocalDate;

public class SaleJsondto {
private long id;
private LocalDate data;
private int countClient;
private int countSale;
private double totalValus;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public LocalDate getData() {
	return data;
}
public void setData(LocalDate data) {
	this.data = data;
}
public int getCountClient() {
	return countClient;
}
public void setCountClient(int countClient) {
	this.countClient = countClient;
}
public int getCountSale() {
	return countSale;
}
public void setCountSale(int countSale) {
	this.countSale = countSale;
}
public double getTotalValus() {
	return totalValus;
}
public void setTotalValus(double totalValus) {
	this.totalValus = totalValus;
}


}
