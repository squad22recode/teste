package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gestaoCash.model.Sale;

public class teste {
	  public List<Sale> processSales(List<Sale> sales) {
	        Map<LocalDate, Sale> saleMap = new HashMap<>();

	        for (Sale sale : sales) {
	            LocalDate dateKey = sale.getData();
	            Sale existingSale = saleMap.get(dateKey);

	            if (existingSale == null) {
	                saleMap.put(dateKey, sale);
	            } else {
	                existingSale.setValorTotal(existingSale.getValorTotal() + sale.getValorTotal());
	                
	                
	            }
	        }

	        return new ArrayList<>(saleMap.values());
	    }

//	    private List<ItemSale> concatenateItems(List<ItemSale> items1, List<ItemSale> items2) {
//	        // Implemente a lógica para concatenar as listas de itens
//	    }
//
//	    private double calculateDiscount(double discount1, double discount2) {
//	        // Implemente a lógica para calcular o desconto final
//	    }
}
